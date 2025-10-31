package com.amachlou.garages_manager.search;

import com.amachlou.garages_manager.model.Vehicule;
import com.amachlou.garages_manager.model.VehiculeDocument;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehiculeSearchRepository extends ElasticsearchRepository<VehiculeDocument, Long> {

    // Full-text search
    @Query("SELECT v FROM Vehicule v WHERE v.model LIKE %:term% OR v.type LIKE %:term% OR v.brand LIKE %:term%")
    List<VehiculeDocument> searchByTerm(@Param("term") String term);

    List<VehiculeDocument> findByModelContaining(String term);

}
