package kr.revfactory.election.domain.report.repository;

import kr.revfactory.election.domain.Region.Town;
import kr.revfactory.election.domain.report.Report;
import kr.revfactory.election.domain.report.enums.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findByTown(Town town);
    List<Report> findByTypeAndTown(ReportType type, Town town);
}
