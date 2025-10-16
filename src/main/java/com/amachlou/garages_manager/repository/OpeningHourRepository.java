package com.amachlou.garages_manager.repository;

import com.amachlou.garages_manager.model.OpeningHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpeningHourRepository extends JpaRepository<OpeningHour, Long> {

    List<OpeningHour> findByGarageId(Long id);

}
