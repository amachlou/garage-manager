package com.amachlou.garages_manager.repository;

import com.amachlou.garages_manager.model.Accessoire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessoireRepository extends JpaRepository<Accessoire, Long> {

    List<Accessoire> findByVehiculeId(Long vehiculeId);

}
