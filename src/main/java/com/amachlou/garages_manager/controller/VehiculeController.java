package com.amachlou.garages_manager.controller;

import com.amachlou.garages_manager.model.Vehicule;
import com.amachlou.garages_manager.repository.VehiculeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicules")
@AllArgsConstructor
public class VehiculeController {

//    * Ajout, modification et suppression de véhicules associés à un garage.
//    o Lister les véhicules d’un garage spécifique.
//    o Possibilité de lister tous les véhicules d’un modèle donné dans plusieurs vehicules.

    private final VehiculeRepository vehiculeRepository;

    @PostMapping
    public ResponseEntity<Vehicule> createVehicule(@RequestBody Vehicule vehicule) {
        Vehicule saved = vehiculeRepository.save(vehicule);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public Page<Vehicule> getAllVehicules(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {

        // Parse sort params
        String sortField = sort[0];
        String sortDirection = sort.length > 1 ? sort[1] : "desc";

        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

        return vehiculeRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicule> getVehiculeById(@PathVariable Long id) {
        return vehiculeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/modele/{model}")
    public ResponseEntity<Vehicule> getVehiculeById(@PathVariable String model) {
        return vehiculeRepository.findByModel(model)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicule> updateVehicule(@PathVariable Long id, @RequestBody Vehicule updatedVehicule) {
        return vehiculeRepository.findById(id).map(vehicule -> {
            vehicule.setBrand(updatedVehicule.getBrand());
            vehicule.setModel(updatedVehicule.getType());
            vehicule.setType(updatedVehicule.getType());
            vehicule.setFuelType(updatedVehicule.getFuelType());
            vehicule.setProductionYear(updatedVehicule.getProductionYear());
            vehicule.setAccessoires(updatedVehicule.getAccessoires());

            Vehicule saved = vehiculeRepository.save(vehicule);

            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Vehicule> deleteVehicule(@PathVariable Long id) {
        if (!vehiculeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        vehiculeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
