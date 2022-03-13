package kr.revfactory.election.domain.Region.repository;

import kr.revfactory.election.domain.Region.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByCode(String code);
}
