package com.amachlou.garages_manager.service;

import com.amachlou.garages_manager.model.Vehicule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VehiculeService {

    Page<Vehicule> findByModel(String model, Pageable pageable);
    List<Vehicule> findByGarageId(Long garageId);

}
