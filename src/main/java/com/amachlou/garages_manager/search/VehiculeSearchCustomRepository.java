package com.amachlou.garages_manager.search;

import com.amachlou.garages_manager.model.Vehicule;
import com.amachlou.garages_manager.model.VehiculeDocument;

import java.util.List;

public interface VehiculeSearchCustomRepository {

    List<VehiculeDocument> fullTextSearch(String term);
    List<VehiculeDocument> findAllWithFields();
    List<VehiculeDocument> searchByTerm(String term);

}
