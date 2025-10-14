package com.amachlou.garages_manager;

import com.amachlou.garages_manager.model.Accessoire;
import com.amachlou.garages_manager.model.Garage;
import com.amachlou.garages_manager.model.Vehicule;
import com.amachlou.garages_manager.repository.AccessoireRepository;
import com.amachlou.garages_manager.repository.GarageRepository;
import com.amachlou.garages_manager.repository.VehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class GaragesManagerApplication {
	@Autowired
	private VehiculeRepository vehiculeRepository;
	@Autowired
	private GarageRepository garageRepository;
	@Autowired
	private AccessoireRepository accessoireRepository;

	String[] cities = {"Lisbon", "Casablanca", "Tokyo", "Berlin", "New York", "Paris", "Cairo", "Toronto", "Sydney", "Dubai"};
	String[] weatherTypes = {"Sunny", "Cloudy", "Rainy", "Windy", "Clear"};

	Random random = new Random();

	public static void main(String[] args) {
		SpringApplication.run(GaragesManagerApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(){
		return args -> {

			Garage g1 = garageRepository.save(new Garage("DAR BOUAZZA", "Ru2 01", "12334455", "test@email.com","Casablanca",null));
			Garage g2 = garageRepository.save(new Garage("Inzegan", "Bds Muqawama", "7009955", "dev@email.com","Agadir",null));
			Garage g3 = garageRepository.save(new Garage("Rabat", "Hay Riyad", "+092334455", "prod@email.com","Tanger",null));

			Vehicule v1 = vehiculeRepository.save(new Vehicule("MD01", "SEDAN", "Renault", Year.of(2020), "Diesel", g1, null));
			Vehicule v2 = vehiculeRepository.save(new Vehicule("MD02", "COPE", "BENZ", Year.of(2022), "Gaz", g2, null));
			Vehicule v3 = vehiculeRepository.save(new Vehicule("MD03", "CABRIOLET", "TESLA", Year.of(2023), "Gasoil", g3, null));

			Accessoire a1 = accessoireRepository.save(new Accessoire("Pignon", "15cm P", 76.8, "Originl", v1));
			Accessoire a2 = accessoireRepository.save(new Accessoire("Cable", "15cm P", 6.8, "CAT 2", v2));
			Accessoire a3 = accessoireRepository.save(new Accessoire("Cle", "15cm P", 00.8, "Super", v3));
		};
	}

}
