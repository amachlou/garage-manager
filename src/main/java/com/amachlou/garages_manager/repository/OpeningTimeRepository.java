package com.amachlou.garages_manager.repository;

import com.amachlou.garages_manager.model.OpeningHour;
import com.amachlou.garages_manager.model.OpeningTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpeningTimeRepository extends JpaRepository<OpeningTime, Long> {
}
