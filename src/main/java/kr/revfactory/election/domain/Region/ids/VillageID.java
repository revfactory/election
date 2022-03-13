package kr.revfactory.election.domain.Region.ids;

import kr.revfactory.election.domain.Region.Town;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VillageID implements Serializable {
    private Town town;
    private String name;
}
