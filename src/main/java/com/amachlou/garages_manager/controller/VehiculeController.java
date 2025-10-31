package com.amachlou.garages_manager.controller;

import com.amachlou.garages_manager.model.Accessoire;
import com.amachlou.garages_manager.model.Vehicule;
import com.amachlou.garages_manager.repository.AccessoireRepository;
import com.amachlou.garages_manager.repository.VehiculeRepository;
import com.amachlou.garages_manager.service.VehiculeService;
import com.amachlou.garages_manager.service.kafka.VehiculeProducer;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicules")
@AllArgsConstructor
@Tag(name = "Vehicle Management", description = "CRUD operations for vehicles")
public class VehiculeController {

//    * Ajout, modification et suppression de véhicules associés à un garage.
//    o Lister les véhicules d’un garage spécifique.
//    o Possibilité de lister tous les véhicules d’un modèle donné dans plusieurs vehicules.

    private final VehiculeRepository vehiculeRepository;
    private final AccessoireRepository accessoireRepository;
    private final VehiculeService vehiculeService;
    private final VehiculeProducer vehiculeProducer;

    @PostMapping
    public ResponseEntity<Vehicule> createVehicule(@RequestBody Vehicule vehicule) {
        Vehicule saved = vehiculeRepository.save(vehicule);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
//    @Operation(summary = "List vehicles with pagination & sorting")
    public Page<Vehicule> getAllVehicules(
            @RequestParam(required = false) String model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {

        // Parse sort params
        String sortField = sort[0];
        String sortDirection = sort.length > 1 ? sort[1] : "desc";

        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

        if(!Strings.isBlank(model)){
            return vehiculeService.findByModel(model, pageable);
        }
        return vehiculeRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicule> getVehiculeById(@PathVariable Long id) {
        return vehiculeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/accessoires")
    public List<Accessoire> getVehiculeAccessoires(@PathVariable Long id) {
        return accessoireRepository.findByVehiculeId(id);
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

    // Vehicule publisher
    @PostMapping("/publish")
    public ResponseEntity<String> publish(@RequestBody Vehicule vehicule) {
        vehiculeProducer.publish(vehicule);
        return ResponseEntity.ok("Vehicule sent to Kafka");
    }

}
