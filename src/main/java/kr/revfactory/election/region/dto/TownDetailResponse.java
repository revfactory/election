package kr.revfactory.election.region.dto;

import kr.revfactory.election.domain.Region.Town;
import kr.revfactory.election.domain.Region.Village;
import lombok.Builder;
import lombok.Getter;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class TownDetailResponse {
    private final Long id;
    private final String code;
    private final String name;
    private final Set<Village> villages;

    @Builder
    public TownDetailResponse(Long id, String code, String name, Set<Village> villages) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.villages = villages;
    }

    public static TownDetailResponse build(final Town town) {
        return TownDetailResponse.builder()
                .id(town.getId())
                .code(town.getCode())
                .name(town.getName())
                .villages(town.getVillages())
                .build();
    }

    public static List<TownDetailResponse> build(final Collection<Town> towns) {
        return towns.stream()
                .map(TownDetailResponse::build)
                .collect(Collectors.toList());
    }
}
