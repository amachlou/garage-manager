package com.amachlou.garages_manager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "garages")
public class Garage extends BaseEntity {

    // TODO: Implement openning hours
    private String name;
    private String address;
    private String telephone;
    private String email;
    private String ville;


    @OneToMany(mappedBy="garage")
    private Set<Vehicule> vehicules;
}
