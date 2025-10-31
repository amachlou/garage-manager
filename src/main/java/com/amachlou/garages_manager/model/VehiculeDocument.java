package com.amachlou.garages_manager.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.time.Year;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "vehicules")
public class VehiculeDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String model;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String type;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String brand;

    @Field(type = FieldType.Keyword, name = "production_year")
    private Year productionYear;

    @Field(name = "fuel_type", type = FieldType.Text, analyzer = "standard")
    private String fuelType;

    public static VehiculeDocument fromEntity(Vehicule vehicule) {
        VehiculeDocument vehiculeDocument = new VehiculeDocument();
        vehiculeDocument.setId(vehiculeDocument.getId());
        vehiculeDocument.setModel(vehicule.getModel());
        vehiculeDocument.setType(vehiculeDocument.getType());
        vehiculeDocument.setBrand(vehiculeDocument.getBrand());
        vehiculeDocument.setProductionYear(vehicule.getProductionYear());
        vehiculeDocument.setFuelType(vehicule.getFuelType());
        return vehiculeDocument;
    }

}
