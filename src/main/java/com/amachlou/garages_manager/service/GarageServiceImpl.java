package com.amachlou.garages_manager.service;

import com.amachlou.garages_manager.exception.GarageOutOfPlacesException;
import com.amachlou.garages_manager.model.Garage;
import com.amachlou.garages_manager.repository.GarageRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GarageServiceImpl implements GarageService {

    private GarageRepository garageRepository;

    @Value("${max.vehicule.per.garage}")
    private int MAX_VEHICULE_PER_GARAGE;

    public GarageServiceImpl(GarageRepository garageRepository) {
        this.garageRepository = garageRepository;
    }

    @Override
    public Garage save(Garage garage){
        long count = garageRepository.count();
        if(count >= MAX_VEHICULE_PER_GARAGE ){
            throw new GarageOutOfPlacesException("Toutes les places du garage sont occupées.");
        }else{
            return garageRepository.save(garage);
        }
    }

}
