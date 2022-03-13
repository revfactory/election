package kr.revfactory.election.region;

import kr.revfactory.election.domain.Region.Town;
import kr.revfactory.election.domain.Region.service.RegionService;
import kr.revfactory.election.region.dto.TownDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/towns")
@RestController
@RequiredArgsConstructor
public class TownController {

    private final RegionService regionService;

    @GetMapping
    public List<Town> getTowns() {
        return regionService.getTowns();
    }

    @GetMapping("/{id}")
    public TownDetailResponse getCity(@PathVariable("id") String id) {
        return TownDetailResponse.build(regionService.getTown(id));
    }
}
