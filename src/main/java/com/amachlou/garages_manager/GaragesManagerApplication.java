package com.amachlou.garages_manager;

import com.amachlou.garages_manager.model.Accessoire;
import com.amachlou.garages_manager.model.Garage;
import com.amachlou.garages_manager.model.OpeningTime;
import com.amachlou.garages_manager.model.Vehicule;
import com.amachlou.garages_manager.repository.AccessoireRepository;
import com.amachlou.garages_manager.repository.GarageRepository;
import com.amachlou.garages_manager.repository.VehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.Year;
import java.util.*;

@SpringBootApplication
public class GaragesManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GaragesManagerApplication.class, args);
	}

}
