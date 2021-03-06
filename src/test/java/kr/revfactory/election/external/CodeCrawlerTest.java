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
                        tuple("1100", "???????????????"),
                        tuple("2600", "???????????????"),
                        tuple("2700", "???????????????"),
                        tuple("2800", "???????????????"),
                        tuple("2900", "???????????????"),
                        tuple("3000", "???????????????"),
                        tuple("3100", "???????????????"),
                        tuple("5100", "?????????????????????"),
                        tuple("4100", "?????????"),
                        tuple("4200", "?????????"),
                        tuple("4300", "????????????"),
                        tuple("4400", "????????????"),
                        tuple("4500", "????????????"),
                        tuple("4600", "????????????"),
                        tuple("4700", "????????????"),
                        tuple("4800", "????????????"),
                        tuple("4900", "?????????????????????")
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

        City city = City.of("1100", "???????????????");
        List<TownCode> towns = cityCrawler.getTowns(city);

        assertThat(towns).hasSize(25)
                .extracting("code", "name")
                .contains(
                        tuple("1101", "?????????"),
                        tuple("1102", "??????"),
                        tuple("1103", "?????????"),
                        tuple("1104", "?????????"),
                        tuple("1105", "?????????"),
                        tuple("1106", "????????????"),
                        tuple("1107", "?????????"),
                        tuple("1108", "?????????"),
                        tuple("1109", "?????????"),
                        tuple("1110", "?????????"),
                        tuple("1111", "?????????"),
                        tuple("1112", "?????????"),
                        tuple("1113", "????????????"),
                        tuple("1114", "?????????"),
                        tuple("1115", "?????????"),
                        tuple("1116", "?????????"),
                        tuple("1117", "?????????"),
                        tuple("1118", "?????????"),
                        tuple("1119", "????????????"),
                        tuple("1120", "?????????"),
                        tuple("1121", "?????????"),
                        tuple("1122", "?????????"),
                        tuple("1123", "?????????"),
                        tuple("1124", "?????????"),
                        tuple("1125", "?????????")
                );
    }
}