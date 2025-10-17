package com.amachlou.garages_manager.controller;

import com.amachlou.garages_manager.model.Garage;
import com.amachlou.garages_manager.model.OpeningTime;
import com.amachlou.garages_manager.model.Vehicule;
import com.amachlou.garages_manager.repository.GarageRepository;
import com.amachlou.garages_manager.service.GarageService;
import com.amachlou.garages_manager.service.VehiculeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GarageController.class)
class GarageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GarageRepository garageRepository;

    @MockitoBean
    private GarageService garageService;

    @MockitoBean
    private VehiculeService vehiculeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Garage garage;

    @Test
    void testCreateGarage() throws Exception {
        Garage garage = new Garage();
        garage.setId(1L);
        garage.setName("Casa Car Service");

        Mockito.when(garageService.save(any(Garage.class))).thenReturn(garage);

        mockMvc.perform(post("/garages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(garage)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Casa Car Service"));
    }

    @Test
    void testGetAllGarages() throws Exception {
        Garage garage = new Garage();
        garage.setId(1L);
        garage.setName("Casa Car Service");
        Page<Garage> page = new PageImpl<>(List.of(garage));

        Mockito.when(garageRepository.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/garages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Casa Car Service"));
    }

    @Test
    void testGetGarageById_Found() throws Exception {
        Garage garage = new Garage();
        garage.setId(1L);
        garage.setName("Casa Car Service");

        Mockito.when(garageRepository.findById(1L)).thenReturn(Optional.of(garage));

        mockMvc.perform(get("/garages/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Casa Car Service"));
    }

    @Test
    void testGetGarageById_NotFound() throws Exception {
        Mockito.when(garageRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/garages/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetVehiculesPerGarage() throws Exception {
        Vehicule vehicule = new Vehicule();
        Mockito.when(vehiculeService.findByGarageId(1L)).thenReturn(List.of(vehicule));

        mockMvc.perform(get("/garages/1/vehicules"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetGarageOpeningHours() throws Exception {
        Map<DayOfWeek, List<OpeningTime>> map = Map.of(DayOfWeek.MONDAY, List.of(new OpeningTime()));
        Mockito.when(garageService.getGarageOpeningHours(1L)).thenReturn(map);

        mockMvc.perform(get("/garages/1/hours"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateGarage_Found() throws Exception {
        Garage existing = new Garage();
        existing.setId(1L);
        existing.setName("Casa Car Service");

        Garage updated = new Garage();
        updated.setName("Car Center");

        Mockito.when(garageRepository.findById(1L)).thenReturn(Optional.of(existing));
        Mockito.when(garageRepository.save(any(Garage.class))).thenReturn(existing);

        mockMvc.perform(put("/garages/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Car Center"));
    }

    @Test
    void testDeleteGarage_Found() throws Exception {
        Mockito.when(garageRepository.existsById(1L)).thenReturn(true);

        mockMvc.perform(delete("/garages/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteGarage_NotFound() throws Exception {
        Mockito.when(garageRepository.existsById(1L)).thenReturn(false);

        mockMvc.perform(delete("/garages/1"))
                .andExpect(status().isNotFound());
    }
}