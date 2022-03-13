package kr.revfactory.election.region.dto;

import kr.revfactory.election.domain.Region.City;
import kr.revfactory.election.domain.Region.Town;
import lombok.Builder;
import lombok.Getter;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class CityDetailResponse {
    private final Long id;
    private final String code;
    private final String name;
    private final Set<Town> towns;

    @Builder
    public CityDetailResponse(Long id, String code, String name, Set<Town> towns) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.towns = towns;
    }

    public static CityDetailResponse build(final City city) {
        return CityDetailResponse.builder()
                .id(city.getId())
                .code(city.getCode())
                .name(city.getName())
                .towns(city.getTowns())
                .build();
    }

    public static List<CityDetailResponse> build(final Collection<City> cities) {
        return cities.stream()
                .map(CityDetailResponse::build)
                .collect(Collectors.toList());
    }

}
