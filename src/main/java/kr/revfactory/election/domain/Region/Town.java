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
@JsonIgnoreProperties({ "villages" })
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code"}, name = "ux_town_code")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Town implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String code;

    @NotNull
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JsonBackReference
    private City city;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "town", cascade = CascadeType.PERSIST)
    private Set<Village> villages;

    @Builder
    private Town(City city, String code, String name) {
        this.city = city;
        this.code = code;
        this.name = name;
        villages = new HashSet<>();
    }

    public void add(Village village) {
        this.villages.add(village);
    }

    public static Town of(City city, String code, String name) {
        return Town.builder()
                .city(city)
                .code(code)
                .name(name)
                .build();
    }
}
