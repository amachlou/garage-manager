package com.amachlou.garages_manager.controller;

import com.amachlou.garages_manager.model.Garage;
import com.amachlou.garages_manager.repository.GarageRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/garages")
@AllArgsConstructor
@Tag(name = "Garage", description = "Operations on Garages")
public class GarageController {
//    Création, modification et suppression de garages.
//    o Récupération d’un garage spécifique (par ID).
//    o Liste paginée de tous les garages, avec possibilité de tri (par nom,ville, etc.).
    private final GarageRepository garageRepository;

    @PostMapping
    public ResponseEntity<Garage> createGarage(@RequestBody Garage garage) {
        Garage saved = garageRepository.save(garage);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    @ResponseBody
    public Page<Garage> getAllGarages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {

        // Parse sort params
        String sortField = sort[0];
        String sortDirection = sort.length > 1 ? sort[1] : "asc";

        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

        return garageRepository.findAll(pageable);
    }

    @GetMapping("/all")
    public List<Garage> getAll() {
        List<Garage> garages = garageRepository.findAll();
        return garages;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Garage> getGarageById(@PathVariable Long id) {
        return garageRepository.findById(id)
                                .map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Garage> updateGarage(@PathVariable Long id, @RequestBody Garage updatedGarage) {
        return garageRepository.findById(id).map(garage -> {
            garage.setName(updatedGarage.getName());
            garage.setAddress(updatedGarage.getAddress());
            garage.setEmail(updatedGarage.getEmail());
            garage.setVille(updatedGarage.getVille());
            garage.setTelephone(updatedGarage.getTelephone());
            garage.setVehicules(updatedGarage.getVehicules());

            Garage saved = garageRepository.save(garage);

            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Garage> deleteGarage(@PathVariable Long id) {
        if (!garageRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        garageRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
