package kr.revfactory.election.domain.Region;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@ToString(of = {"name"})
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"village_id", "name"}, name = "ux_village_section_name")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    private Village village;

    @NotNull
    @Column(nullable = false)
    private String name;

    @Builder
    private Section(Village village, String name) {
        this.village = village;
        this.name = name;
    }

    public static Section of(Village village, String name) {
        return Section.builder()
                .village(village)
                .name(name)
                .build();
    }
}
