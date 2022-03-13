package kr.revfactory.election.domain.Region.repository;

import kr.revfactory.election.domain.Region.Town;
import kr.revfactory.election.domain.Region.Village;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VillageRepository extends JpaRepository<Village, Long> {
    Optional<Village> findByTownAndName(Town town, String name);
}
