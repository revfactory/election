package kr.revfactory.election.domain.Region;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@ToString(of = {"name"})
@JsonIgnoreProperties({ "sections" })
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"town_id", "name"}, name = "ux_town_village_name")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Village implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    private Town town;

    @NotNull
    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "village", cascade = CascadeType.PERSIST)
    private Set<Section> sections = new HashSet<>();

    @Builder
    private Village(Town town, String name) {
        this.town = town;
        this.name = name;
        this.sections = new HashSet<>();
    }

    public void add(Section section) {
        this.sections.add(section);
    }

    public static Village of(Town town, String name) {
        return Village.builder()
                .town(town)
                .name(name)
                .build();
    }
}
