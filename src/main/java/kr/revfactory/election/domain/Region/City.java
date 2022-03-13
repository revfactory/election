package kr.revfactory.election.domain.Region;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@ToString(of = {"name"})
@JsonIgnoreProperties({ "towns" })
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code"}, name = "ux_city_code")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class City implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String code;

    @NotNull
    @Column(nullable = false)
    private String name;

    @JsonManagedReference
    @OneToMany(mappedBy = "city", cascade = CascadeType.PERSIST)
    private Set<Town> towns;

    @Builder
    private City(String code, String name) {
        this.code = code;
        this.name = name;
        this.towns = new HashSet<>();
    }

    public static City of(String code, String name) {
        return City.builder()
                .code(code)
                .name(name)
                .build();
    }

    public void addAll(List<Town> towns) {
        this.towns.addAll(towns);
    }
}
