package kr.revfactory.election.domain.report.service;

import kr.revfactory.election.domain.Region.Section;
import kr.revfactory.election.domain.Region.Town;
import kr.revfactory.election.domain.Region.Village;
import kr.revfactory.election.domain.Region.repository.CityRepository;
import kr.revfactory.election.domain.Region.repository.SectionRepository;
import kr.revfactory.election.domain.Region.repository.TownRepository;
import kr.revfactory.election.domain.Region.repository.VillageRepository;
import kr.revfactory.election.domain.report.Report;
import kr.revfactory.election.domain.report.enums.ReportType;
import kr.revfactory.election.domain.report.repository.ReportRepository;
import kr.revfactory.election.external.ReportCrawler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    private final CityRepository cityRepository;
    private final TownRepository townRepository;
    private final VillageRepository villageRepository;
    private final SectionRepository sectionRepository;

    private final ReportCrawler reportCrawler;

    public List<Report> getReports() {
        return reportRepository.findAll();
    }

    public List<Report> getReportsByTown(Town town) {
        return reportRepository.findByTypeAndTown(ReportType.VOTE, town);
    }

    public void loadAll() {
        townRepository.findAll()
                .forEach(this::load);
    }

    public List<Report> load(Town town) {
        deleteByTown(town);
        List<Report> reports = reportCrawler.getReports(town);
        reports.forEach(report -> {
            report.setVillage(this::findOrCreateVillage);
            report.setSection(this::findOrCreateSection);
        });
        return reportRepository.saveAll(reports);
    }

    public void deleteByTown(Town town) {
        List<Report> reports = reportRepository.findByTown(town);
        reportRepository.deleteAll(reports);
    }

    public Village findOrCreateVillage(Town town, String villageName) {
        if (StringUtils.hasText(villageName)) {
            return villageRepository.findByTownAndName(town, villageName)
                    .orElseGet(() -> villageRepository.save(Village.of(town, villageName)));
        } else {
            return null;
        }
    }

    public Section findOrCreateSection(Village village, String sectionName) {
        if (StringUtils.hasText(sectionName)) {
            return sectionRepository.findByVillageAndName(village, sectionName)
                    .orElseGet(() -> {
                        Section section = Section.of(village, sectionName);
                        return sectionRepository.save(section);
                    });
        } else {
            return null;
        }
    }

}
