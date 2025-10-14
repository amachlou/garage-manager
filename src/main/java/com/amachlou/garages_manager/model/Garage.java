package com.amachlou.garages_manager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "GARAGES")
public class Garage extends BaseEntity implements Serializable {

    // TODO: Implement openning hours
    private String name;
    private String address;
    private String telephone;
    private String email;
    private String ville;


//  Map garage to vehicule
    @OneToMany(mappedBy="garage")
    private Set<Vehicule> vehicules;

}
