package com.amachlou.garages_manager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "GARAGES")
public class Garage implements Serializable {

    // TODO: Implement openning hours
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String telephone;
    private String email;
    private String ville;


//  Map garage to vehicule
    @OneToMany(mappedBy="garage", fetch=FetchType.LAZY)
    private Set<Vehicule> vehicules;

//    @ElementCollection
//    @CollectionTable(name = "garage_opening_hours", joinColumns = @JoinColumn(name = "garage_id"))
//    @MapKeyColumn(name = "day_of_week")
//    @MapKeyEnumerated(EnumType.STRING)
//    @Column(name = "opening_time")
//    private Map<DayOfWeek, List<OpeningTime>> horairesOuverture = new EnumMap<>(DayOfWeek.class);

    // Map Garare with embedded Openning times
//    public Map<DayOfWeek, List<OpeningTime>> getHorairesOuverture() { return horairesOuverture; }
//    public void setHorairesOuverture(Map<DayOfWeek, List<OpeningTime>> horairesOuverture) { this.horairesOuverture = horairesOuverture; }

}
