package com.amachlou.garages_manager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ACCESSOIRES")
public class Accessoire implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String type;

//  Map accessoire to vehicule
    @ManyToOne
    @JoinColumn(name="vehicule_id", nullable=false)
    private Vehicule vehicule;

}
