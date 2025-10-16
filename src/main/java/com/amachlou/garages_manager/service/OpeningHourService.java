package com.amachlou.garages_manager.service;

import com.amachlou.garages_manager.model.OpeningTime;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

public interface OpeningHourService {

    public Map<DayOfWeek, List<OpeningTime>> getOpeningHours(Long garageId);

}
