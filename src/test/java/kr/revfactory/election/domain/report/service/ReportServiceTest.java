package kr.revfactory.election.domain.report.service;

import kr.revfactory.election.domain.Region.City;
import kr.revfactory.election.domain.Region.Town;
import kr.revfactory.election.domain.report.Report;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReportServiceTest {

    @Autowired
    private ReportService reportService;

    @Test
    public void load() {
        City city = City.of("1100", "서울특별시");
        Town town = Town.of(city, "1102", "중구");
        List<Report> reports = reportService.load(town);

        reports.forEach(report -> {
            System.out.println(report + " >>> " + report.getStatistics() + " | " + report.getVote());
        });
    }
}