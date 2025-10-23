package com.amachlou.garages_manager.controller;

import com.amachlou.garages_manager.model.Garage;
import com.amachlou.garages_manager.repository.GarageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Full-stack integration tests for {@link GarageController}.
 * This spins up the web server, uses the real repository,
 * and verifies persistence and REST integration.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class GarageControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private GarageRepository garageRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/garages";
        garageRepository.deleteAll();
    }

    @Test
    @DisplayName("POST /garages → should create a new garage and persist it")
    void createGarage_ShouldPersistAndReturnGarage() {
        Garage garage = new Garage();
        garage.setName("MegaGarage");
        garage.setAddress("Rue Hassan II");
        garage.setVille("Marrakech");
        garage.setEmail("mega@garage.ma");
        garage.setTelephone("+212612345678");

        ResponseEntity<Garage> response = restTemplate.postForEntity(baseUrl, garage, Garage.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(garageRepository.findById(response.getBody().getId())).isPresent();
    }

    @Test
    @DisplayName("GET /garages → should return a paginated list")
    void getAllGarages_ShouldReturnPage() {
        garageRepository.save(new Garage(null,"AutoFix","Avenue Mohammed V","+212600111222","fix@auto.ma","Casablanca",null,null));
        garageRepository.save(new Garage(null,"SpeedGarage","Rue Oujda","+212600333444","speed@garage.ma","Rabat",null,null));

        String url = baseUrl + "?page=0&size=5&sort=name,asc";

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(((Map<?, ?>) response.getBody().get("pageable"))).isNotEmpty();
        assertThat(response.getBody()).containsKey("content");
    }

    @Test
    @DisplayName("GET /garages/{id} → should return existing garage")
    void getGarageById_ShouldReturnGarage() {
        Garage saved = garageRepository.save(new Garage(null, "AutoKing", "Centre Ville", "Tanger", "king@auto.ma", "+212655112233", null, null));

        ResponseEntity<Garage> response = restTemplate.getForEntity(baseUrl + "/" + saved.getId(), Garage.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("AutoKing");
    }

    @Test
    @DisplayName("PUT /garages/{id} → should update and return garage")
    void updateGarage_ShouldModifyGarage() {
        Garage saved = garageRepository.save(new Garage(null,"OldName","Rue 1","Agadir","old@garage.ma","+212655998877",null,null));

        saved.setName("Car Center");
        HttpEntity<Garage> entity = new HttpEntity<>(saved);

        ResponseEntity<Garage> response = restTemplate.exchange(
                baseUrl + "/" + saved.getId(),
                HttpMethod.PUT,
                entity,
                Garage.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("Car Center");

        Garage updated = garageRepository.findById(saved.getId()).orElseThrow();
        assertThat(updated.getName()).isEqualTo("Car Center");
    }

    @Test
    @DisplayName("DELETE /garages/{id} → should remove garage")
    void deleteGarage_ShouldDeleteFromDb() {
        Garage saved = garageRepository.save(new Garage(null, "My Old Garage", "Rue X", "+212644332211", "del@garage.ma", "Oujda", null, null));

        restTemplate.delete(baseUrl + "/" + saved.getId());

        assertThat(garageRepository.existsById(saved.getId())).isFalse();
    }

    @Test
    @DisplayName("GET /garages/{id}/vehicules → should return empty list initially")
    void getVehicules_ShouldReturnEmptyList() {
        Garage saved = garageRepository.save(new Garage(null, "VehiculeTest", "Zone A", "+212611000999", "vh@garage.ma", "Casa", null, null));

        ResponseEntity<Object[]> response = restTemplate.getForEntity(baseUrl + "/" + saved.getId() + "/vehicules", Object[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();
    }
}


