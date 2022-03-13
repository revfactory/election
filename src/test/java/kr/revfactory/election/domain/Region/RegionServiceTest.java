package kr.revfactory.election.domain.Region;

import kr.revfactory.election.domain.Region.service.RegionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class RegionServiceTest {

    @Autowired
    private RegionService regionService;

    @Test
    public void loadAll() {
        List<City> cities = regionService.loadAll();

        cities.forEach(city -> {
            System.out.println(city.getCode() + " : " + city.getName());
            city.getTowns().forEach(town -> System.out.println(" - " + town.getCode() + " : " + town.getName()));
        });
    }
}