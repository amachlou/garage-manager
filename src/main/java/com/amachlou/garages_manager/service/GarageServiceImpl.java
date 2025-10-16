package com.amachlou.garages_manager.service;

import com.amachlou.garages_manager.exception.GarageOutOfPlacesException;
import com.amachlou.garages_manager.model.Garage;
import com.amachlou.garages_manager.model.OpeningTime;
import com.amachlou.garages_manager.repository.GarageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@Service
public class GarageServiceImpl implements GarageService {

    private GarageRepository garageRepository;
    private OpeningHourService openingHourService;

    @Value("${max.vehicule.per.garage}")
    private int MAX_VEHICULE_PER_GARAGE;

    public GarageServiceImpl(GarageRepository garageRepository, OpeningHourService openingHourService) {
        this.garageRepository = garageRepository;
        this.openingHourService = openingHourService;
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

    @Override
    public Map<DayOfWeek, List<OpeningTime>> getGarageOpeningHours(Long garageId) {
        return openingHourService.getOpeningHours(garageId);
    }

}
