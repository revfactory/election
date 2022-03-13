package kr.revfactory.election.domain.Region.ids;

import kr.revfactory.election.domain.Region.Village;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionID implements Serializable {
    private Village village;
    private String name;
}
