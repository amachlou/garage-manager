package com.amachlou.garages_manager.controller;

import com.amachlou.garages_manager.model.Garage;
import com.amachlou.garages_manager.repository.GarageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.JsonPath;
import org.springframework.test.web.servlet.MockMvc;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GarageControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private GarageRepository garageRepository;

    @Test
    void createGarage() {
//        TODO: To be completed
        Garage garage = new Garage(null,"DAR BOUAZZA", "Ru2 01", "12334455", "test@email.com","Casablanca", null);
//        mvc.perform(get("/api/vehicles?page=0&size=2&sort=year,desc")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(JsonPath("$.content", hasSize(1)))
//                .andExpect(jsonPath("$.content[0].brand", is("BMW")));
    }

    @Test
    void getAllGarages() {

    }

    @Test
    void getGarageById() {
    }

    @Test
    void updateGarage() {
    }

    @Test
    void deleteGarage() {
    }
}