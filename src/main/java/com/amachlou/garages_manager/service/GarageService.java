package com.amachlou.garages_manager.service;

import com.amachlou.garages_manager.model.Garage;
import com.amachlou.garages_manager.model.OpeningTime;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

public interface GarageService {

    Garage save(Garage garage);
    Map<DayOfWeek,List<OpeningTime>> getGarageOpeningHours(Long garageId);

}
