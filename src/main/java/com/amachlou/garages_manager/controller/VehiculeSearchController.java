package com.amachlou.garages_manager.controller;

import com.amachlou.garages_manager.model.Vehicule;
import com.amachlou.garages_manager.model.VehiculeDocument;
import com.amachlou.garages_manager.repository.VehiculeRepository;
import com.amachlou.garages_manager.search.VehiculeSearchCustomRepository;
import com.amachlou.garages_manager.search.VehiculeSearchRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vehicules/search")
@AllArgsConstructor
@Tag(name = "Vehicle Search", description = "Search operations")
public class VehiculeSearchController {

    private final VehiculeRepository vehiculeRepository;
    private final VehiculeSearchRepository vehiculeSearchRepository;
    private final VehiculeSearchCustomRepository vehiculeSearchCustomRepository;

    // SEARCH
    @GetMapping("/all")
    public List<VehiculeDocument> searchAll() {
        List<VehiculeDocument> vehicules = (List<VehiculeDocument>) vehiculeSearchRepository.findAll();
        return vehicules;
    }

    @GetMapping
    public ResponseEntity<List<VehiculeDocument>> searchProducts(@RequestParam String term) {
        List<VehiculeDocument> results = vehiculeSearchRepository.findByModelContaining(term);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/v1")
    public ResponseEntity<List<VehiculeDocument>> searchProducts1(@RequestParam String term) {
        List<VehiculeDocument> results = vehiculeSearchCustomRepository.searchByTerm(term);
        syncDatabaseToElastic();
        return ResponseEntity.ok(results);
    }

    @GetMapping("/v2")
    public ResponseEntity<List<VehiculeDocument>> searchProducts2(@RequestParam String term) {
        List<VehiculeDocument> results = vehiculeSearchCustomRepository.fullTextSearch(term);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/v3")
    public ResponseEntity<List<VehiculeDocument>> searchProducts3(@RequestParam String term) {
        List<VehiculeDocument> results = vehiculeSearchCustomRepository.findAllWithFields();
        return ResponseEntity.ok(results);
    }

    @Async
    @PostConstruct
    public void syncDatabaseToElastic() {
        List<Vehicule> vehicules = vehiculeRepository.findAll();
        List<VehiculeDocument> documents = vehicules.stream()
                .map(VehiculeDocument::fromEntity)
                .toList();
        vehiculeSearchRepository.saveAll(documents);
    }


}
