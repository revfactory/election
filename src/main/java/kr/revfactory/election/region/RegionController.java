package kr.revfactory.election.region;

import kr.revfactory.election.domain.Region.City;
import kr.revfactory.election.domain.Region.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/regions")
@RestController
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @GetMapping("/load")
    public List<City> load() {
        return regionService.loadAll();
    }
}
