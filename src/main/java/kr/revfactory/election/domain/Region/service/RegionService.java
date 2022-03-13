package kr.revfactory.election.domain.Region.service;

import kr.revfactory.election.domain.Region.City;
import kr.revfactory.election.domain.Region.Town;
import kr.revfactory.election.domain.Region.repository.CityRepository;
import kr.revfactory.election.domain.Region.repository.TownRepository;
import kr.revfactory.election.exception.EntityNotFoundException;
import kr.revfactory.election.external.CodeCrawler;
import kr.revfactory.election.external.dto.CityCode;
import kr.revfactory.election.external.dto.TownCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RegionService {

    private final CityRepository cityRepository;
    private final TownRepository townRepository;
    private final CodeCrawler codeCrawler;

    public List<City> getCities() {
        return cityRepository.findAll();
    }

    public City getCity(String id) {
        return cityRepository.findByCode(id)
                .orElseThrow(() -> new EntityNotFoundException(City.class, id));
    }

    public List<Town> getTowns() {
        return townRepository.findAll();
    }

    public Town getTown(String id) {
        return townRepository.findByCode(id)
                .orElseThrow(() -> new EntityNotFoundException(City.class, id));
    }

    public List<City> loadAll() {
        List<City> cities = loadCities();
        for (City city : cities) {
            loadTown(city);
        }
        return cities;
    }

    public List<City> loadCities() {
        List<City> cities = codeCrawler.getCities().stream()
                .map(this::findOrCreateCity)
                .collect(Collectors.toList());
        return cityRepository.saveAll(cities);
    }

    public List<Town> loadTown(City city) {
        List<Town> towns = codeCrawler.getTowns(city).stream()
                .map(townCode -> this.findOrCreateTown(city, townCode))
                .collect(Collectors.toList());
        city.addAll(towns);
        cityRepository.save(city);
        return townRepository.saveAll(towns);
    }

    public City findOrCreateCity(CityCode cityCode) {
        return cityRepository.findByCode(cityCode.getCode())
                .orElseGet(() -> cityRepository.save(cityCode.toEntity()));
    }

    public Town findOrCreateTown(City city, TownCode townCode) {
        return townRepository.findByCode(townCode.getCode())
                .orElseGet(() -> townRepository.save(townCode.toEntity(city)));
    }
}
