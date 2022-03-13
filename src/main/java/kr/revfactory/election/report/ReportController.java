package kr.revfactory.election.report;

import kr.revfactory.election.domain.Region.Town;
import kr.revfactory.election.domain.Region.service.RegionService;
import kr.revfactory.election.domain.report.Report;
import kr.revfactory.election.domain.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/reports")
@RestController
@RequiredArgsConstructor
public class ReportController {
    private final RegionService regionService;
    private final ReportService reportService;

    @GetMapping
    public List<Report> retReports() {
        return reportService.getReports();
    }

    @GetMapping("/{id}")
    public  List<Report> getReport(@PathVariable("id") String id) {
        Town town = regionService.getTown(id);
        return reportService.getReportsByTown(town);
    }

    @GetMapping("/load")
    public void loadAll() {
        reportService.loadAll();
    }

    @GetMapping("/load/{id}")
    public List<Report> load(@PathVariable("id") String id) {
        Town town = regionService.getTown(id);
        return reportService.load(town);
    }
}
