package kr.revfactory.election.domain.report;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.revfactory.election.domain.Region.City;
import kr.revfactory.election.domain.Region.Section;
import kr.revfactory.election.domain.Region.Town;
import kr.revfactory.election.domain.Region.Village;
import kr.revfactory.election.domain.report.enums.ReportType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Optional;
import java.util.function.BiFunction;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"type", "city", "town", "village", "section"})
public class Report implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 40)
    @Enumerated(EnumType.STRING)
    private ReportType type;          // 데이터 타입

    @ManyToOne
    private City city;                // 시도

    @ManyToOne
    private Town town;                // 구시군

    @ManyToOne
    private Village village;          // 읍면동

    @ManyToOne
    private Section section;          // 투표구명

    @Embedded
    private Statistics statistics;    // 통계

    @Embedded
    private Vote vote;                // 투표

    @JsonIgnore
    @Transient
    private String villageName;

    @JsonIgnore
    @Transient
    private String sectionName;

    @Builder
    private Report(ReportType type, Town town, String villageName, String sectionName, Statistics statistics, Vote vote) {
        this.type = type;
        this.city = Optional.ofNullable(town).map(Town::getCity).orElse(null);
        this.town = town;
        this.villageName = type.needVillage() ? villageName : null;
        this.sectionName = sectionName;
        this.statistics = statistics;
        this.vote = vote;
    }

    public static Report of(ReportType type, Town town, String villageName, String sectionName, Statistics statistics, Vote vote) {
        return Report.builder()
                .type(type)
                .town(town)
                .villageName(villageName)
                .sectionName(sectionName)
                .statistics(statistics)
                .vote(vote)
                .build();
    }

    public void setVillage(BiFunction<Town, String, Village> villageFunc) {
        this.village = villageFunc.apply(town, villageName);
    }

    public void setSection(BiFunction<Village, String, Section> sectionFunc) {
        this.section = sectionFunc.apply(village, sectionName);
    }
}
