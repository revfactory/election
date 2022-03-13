package kr.revfactory.election.region;

import kr.revfactory.election.domain.Region.City;
import kr.revfactory.election.domain.Region.service.RegionService;
import kr.revfactory.election.region.dto.CityDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/cities")
@RestController
@RequiredArgsConstructor
public class CityController {

    private final RegionService regionService;

    @GetMapping
    public List<City> getCities() {
        return regionService.getCities();
    }

    @GetMapping("/{id}")
    public CityDetailResponse getCity(@PathVariable("id") String id) {
        return CityDetailResponse.build(regionService.getCity(id));
    }
}
