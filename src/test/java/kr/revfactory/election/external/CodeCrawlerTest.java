package kr.revfactory.election.external;

import kr.revfactory.election.domain.Region.City;
import kr.revfactory.election.domain.Region.Town;
import kr.revfactory.election.external.dto.CityCode;
import kr.revfactory.election.external.dto.TownCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestGatewaySupport;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(value = CodeCrawler.class)
@AutoConfigureWebClient(registerRestTemplate=true)
class CodeCrawlerTest {

    @Autowired
    private CodeCrawler cityCrawler;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void setUp() {
        RestGatewaySupport gateway = new RestGatewaySupport();
        gateway.setRestTemplate(restTemplate);
        mockServer = MockRestServiceServer.createServer(gateway);
    }

    @Test
    void getCities() throws IOException  {

        String CITY_CODE_URL = "http://info.nec.go.kr/bizcommon/selectbox/selectbox_cityCodeBySgJson.json";

        Resource resource = new ClassPathResource("/external/selectbox_cityCodeBySgJson.json", getClass());
        String html = StreamUtils.copyToString(resource.getInputStream(), Charset.defaultCharset());

        mockServer.expect(
                        once(),
                        requestTo(CITY_CODE_URL)
                )
                .andRespond(withSuccess(html, MediaType.APPLICATION_JSON));

        List<CityCode> cities = cityCrawler.getCities();

        assertThat(cities).hasSize(17)
                .extracting("code", "name")
                .contains(
                        tuple("1100", "서울특별시"),
                        tuple("2600", "부산광역시"),
                        tuple("2700", "대구광역시"),
                        tuple("2800", "인천광역시"),
                        tuple("2900", "광주광역시"),
                        tuple("3000", "대전광역시"),
                        tuple("3100", "울산광역시"),
                        tuple("5100", "세종특별자치시"),
                        tuple("4100", "경기도"),
                        tuple("4200", "강원도"),
                        tuple("4300", "충청북도"),
                        tuple("4400", "충청남도"),
                        tuple("4500", "전라북도"),
                        tuple("4600", "전라남도"),
                        tuple("4700", "경상북도"),
                        tuple("4800", "경상남도"),
                        tuple("4900", "제주특별자치도")
                );
    }

    @Test
    void getTowns() throws IOException  {

        String TOWN_CODE_URL = "http://info.nec.go.kr/bizcommon/selectbox/selectbox_townCodeJson.json";

        Resource resource = new ClassPathResource("/external/selectbox_townCodeJson.json", getClass());
        String html = StreamUtils.copyToString(resource.getInputStream(), Charset.defaultCharset());

        mockServer.expect(
                        once(),
                        requestTo(TOWN_CODE_URL)
                )
                .andRespond(withSuccess(html, MediaType.APPLICATION_JSON));

        City city = City.of("1100", "서울특별시");
        List<TownCode> towns = cityCrawler.getTowns(city);

        assertThat(towns).hasSize(25)
                .extracting("code", "name")
                .contains(
                        tuple("1101", "종로구"),
                        tuple("1102", "중구"),
                        tuple("1103", "용산구"),
                        tuple("1104", "성동구"),
                        tuple("1105", "광진구"),
                        tuple("1106", "동대문구"),
                        tuple("1107", "중랑구"),
                        tuple("1108", "성북구"),
                        tuple("1109", "강북구"),
                        tuple("1110", "도봉구"),
                        tuple("1111", "노원구"),
                        tuple("1112", "은평구"),
                        tuple("1113", "서대문구"),
                        tuple("1114", "마포구"),
                        tuple("1115", "양천구"),
                        tuple("1116", "강서구"),
                        tuple("1117", "구로구"),
                        tuple("1118", "금천구"),
                        tuple("1119", "영등포구"),
                        tuple("1120", "동작구"),
                        tuple("1121", "관악구"),
                        tuple("1122", "서초구"),
                        tuple("1123", "강남구"),
                        tuple("1124", "송파구"),
                        tuple("1125", "강동구")
                );
    }
}