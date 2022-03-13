package kr.revfactory.election.external.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.revfactory.election.domain.Region.City;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CityCode {
    @JsonProperty("CODE")
    private String code;

    @JsonProperty("NAME")
    private String name;

    public City toEntity() {
        return City.of(code, name);
    }
}
