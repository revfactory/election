package kr.revfactory.election.domain.Region.repository;

import kr.revfactory.election.domain.Region.Section;
import kr.revfactory.election.domain.Region.Village;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

    Optional<Section> findByVillageAndName(Village village, String Name);

}
