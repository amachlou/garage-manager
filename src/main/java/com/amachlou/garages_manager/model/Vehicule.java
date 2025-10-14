package com.amachlou.garages_manager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Year;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "VEHICULES")
public class Vehicule implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String model;
    private String type;
    private String brand;
    @Column(name = "production_year")
    private Year productionYear;
    @Column(name = "fuel_type")
    private String fuelType;

//  Map vehicule to garage
    @ManyToOne
    @JoinColumn(name="garage_id", nullable=false)
    private Garage garage;

//  Map vehicule accessoire
    @OneToMany(mappedBy="vehicule", fetch=FetchType.LAZY)
    private Set<Accessoire> accessoires;


}
