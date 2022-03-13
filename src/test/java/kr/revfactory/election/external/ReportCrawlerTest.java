package kr.revfactory.election.external;

import kr.revfactory.election.domain.Region.City;
import kr.revfactory.election.domain.Region.Town;
import kr.revfactory.election.domain.report.Report;
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
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(value = ReportCrawler.class)
@AutoConfigureWebClient(registerRestTemplate=true)
class ReportCrawlerTest {

    @Autowired
    private ReportCrawler reportCrawler;

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
    public void test() throws IOException {
        String REPORT_URL = "http://info.nec.go.kr/electioninfo/electionInfo_report.xhtml";
        Resource resource = new ClassPathResource("/external/electionInfo_report.xhtml", getClass());
        String html = StreamUtils.copyToString(resource.getInputStream(), Charset.defaultCharset());

        mockServer.expect(
                        once(),
                        requestTo(REPORT_URL)
                )
                .andRespond(withSuccess(html, MediaType.APPLICATION_XHTML_XML));

        City city = City.of("1100", "서울특별시");
        Town town = Town.of(city, "1101", "종로구");

        List<Report> reports = reportCrawler.getReports(town);

        assertThat(reports).hasSize(83);

        for (Report report : reports) {
            System.out.println(report);
        }
    }

}