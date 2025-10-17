package com.amachlou.garages_manager.service.impl;

import com.amachlou.garages_manager.model.Vehicule;
import com.amachlou.garages_manager.repository.VehiculeRepository;
import com.amachlou.garages_manager.service.VehiculeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VehiculeServiceImpl implements VehiculeService {

    private VehiculeRepository vehiculeRepository;

    public Page<Vehicule> findByModel(String model, Pageable pageable){
        return vehiculeRepository.findByModel(model, pageable);
    }

    public List<Vehicule> findByGarageId(Long garageId){
        return vehiculeRepository.findByGarageId(garageId);
    }

}
