package com.amachlou.garages_manager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ACCESSOIRES")
public class Accessoire extends BaseEntity {

    private String name;
    private String description;
    private String price;
    private String type;

//  Map accessoire to vehicule
    @ManyToOne
    @JoinColumn(name="vehicule_id", nullable=false)
    private Vehicule vehicule;

}
