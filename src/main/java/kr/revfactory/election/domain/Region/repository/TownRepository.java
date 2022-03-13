package kr.revfactory.election.domain.Region.repository;

import kr.revfactory.election.domain.Region.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TownRepository extends JpaRepository<Town, Long> {
    Optional<Town> findByCode(String code);
}
