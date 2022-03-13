package kr.revfactory.election.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.revfactory.election.domain.Region.City;
import kr.revfactory.election.domain.Region.Town;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TownCode {
    @JsonProperty("CODE")
    private String code;

    @JsonProperty("NAME")
    private String name;

    public Town toEntity(City city) {
        Town town = Town.of(city, code, name);
        return town;
    }
}
