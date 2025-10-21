package com.amachlou.garages_manager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Year;
import java.util.List;

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
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="garage_id", nullable=false)
    private Garage garage;

//  Map vehicule accessoire
    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy="vehicule", fetch=FetchType.LAZY)
    private List<Accessoire> accessoires;


}
