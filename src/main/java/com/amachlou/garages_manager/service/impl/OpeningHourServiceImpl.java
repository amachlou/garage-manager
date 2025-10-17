package com.amachlou.garages_manager.service.impl;

import com.amachlou.garages_manager.model.OpeningHour;
import com.amachlou.garages_manager.model.OpeningTime;
import com.amachlou.garages_manager.repository.OpeningHourRepository;
import com.amachlou.garages_manager.service.OpeningHourService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OpeningHourServiceImpl implements OpeningHourService {

    private OpeningHourRepository openingHourRepository;

    @Override
    public Map<DayOfWeek, List<OpeningTime>> getOpeningHours(Long garageId){

        List<OpeningHour> hours = openingHourRepository.findByGarageId(garageId);

        return hours.stream()
                .collect(Collectors.toMap(
                        OpeningHour::getDayOfWeek,
                        h -> h.getOpeningTimes().stream()
                                .map(openingTime -> new OpeningTime(openingTime.getStartTime(), openingTime.getEndTime()))
                                .toList()
                ));

    }
}
