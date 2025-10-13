package com.amachlou.garages_manager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "VEHICULES")
public class Vehicule extends BaseEntity {

    private String model;
    private String type;
    private String brand;
    @Column(name = "production_year")
    private Year productionYear;
    @Column(name = "fuel_type")
    private String fuelType;

    @ManyToOne
    @JoinColumn(name="garage_id", nullable=false)
    private Garage garage;

    @OneToMany(mappedBy="vehicule")
    private List<Vehicule> vehicules;

}
