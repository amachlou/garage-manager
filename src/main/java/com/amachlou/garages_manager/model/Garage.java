package com.amachlou.garages_manager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "GARAGES")
public class Garage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String telephone;
    private String email;
    private String ville;

//  Map garage to vehicule
    @JsonManagedReference @JsonIgnore
    @OneToMany(mappedBy="garage", fetch=FetchType.LAZY)
    private Set<Vehicule> vehicules;

    @JsonIgnore
    @OneToMany(mappedBy = "garage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OpeningHour> openingHours;

}
