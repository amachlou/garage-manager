package com.amachlou.garages_manager.utils;

import com.amachlou.garages_manager.model.*;
import com.amachlou.garages_manager.repository.AccessoireRepository;
import com.amachlou.garages_manager.repository.GarageRepository;
import com.amachlou.garages_manager.repository.OpeningHourRepository;
import com.amachlou.garages_manager.repository.OpeningTimeRepository;
import com.amachlou.garages_manager.repository.VehiculeRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.Year;
import java.util.*;

@Configuration
@AllArgsConstructor
public class DataInitializer {

	@Bean
	public CommandLineRunner commandLineRunner(GarageRepository garageRepository,
											   VehiculeRepository vehiculeRepository,
											   AccessoireRepository accessoireRepository,
											   OpeningHourRepository openingHourRepository,
											   OpeningTimeRepository openingTimeRepository){
		return args -> {

			Garage g1 = garageRepository.save(new Garage(null,"DAR BOUAZZA", "Ru2 01", "12334455", "test@email.com","Casablanca", null, null));
			Garage g2 = garageRepository.save(new Garage(null,"Inzegan", "Bds Muqawama", "7009955", "dev@email.com","Agadir",null,null));
			Garage g3 = garageRepository.save(new Garage(null,"Rabat", "Hay Riyad", "+092334455", "prod@email.com","Tanger",null,null));

			Vehicule v1 = vehiculeRepository.save(new Vehicule(null,"MD01", "SEDAN", "Renault", Year.of(2020), "Diesel", g1, null));
			Vehicule v2 = vehiculeRepository.save(new Vehicule(null,"MD02", "COPE", "BENZ", Year.of(2022), "Gaz", g2, null));
			Vehicule v3 = vehiculeRepository.save(new Vehicule(null,"MD03", "CABRIOLET", "TESLA", Year.of(2023), "Gasoil", g3, null));

			Accessoire a1 = accessoireRepository.save(new Accessoire(null,"Pignon", "15cm P", 76.8, "Originl", v1));
			Accessoire a2 = accessoireRepository.save(new Accessoire(null,"Cable", "15cm P", 6.8, "CAT 2", v2));
			Accessoire a3 = accessoireRepository.save(new Accessoire(null,"Cle", "15cm P", 00.8, "Super", v3));

			OpeningHour oh1 = openingHourRepository.save(new OpeningHour(null, g1, DayOfWeek.MONDAY,null));
			OpeningHour oh2 = openingHourRepository.save(new OpeningHour(null, g1, DayOfWeek.TUESDAY,null));
			OpeningHour oh3 = openingHourRepository.save(new OpeningHour(null, g1, DayOfWeek.WEDNESDAY,null));
			OpeningHour oh4 = openingHourRepository.save(new OpeningHour(null, g1, DayOfWeek.THURSDAY,null));
			OpeningHour oh5 = openingHourRepository.save(new OpeningHour(null, g1, DayOfWeek.FRIDAY,null));
			OpeningHour oh6 = openingHourRepository.save(new OpeningHour(null, g1, DayOfWeek.SATURDAY,null));

			Arrays.asList(oh1,oh2,oh3,oh4,oh5).forEach( oh ->
					openingTimeRepository.save(new OpeningTime(null,LocalTime.of(9, 0),LocalTime.of(18, 0),oh))
			);
			openingTimeRepository.save(new OpeningTime(null,LocalTime.of(9, 0),LocalTime.of(18, 0),oh6));

			OpeningTime semaine = new OpeningTime(null,LocalTime.of(9, 0),LocalTime.of(18, 0),oh1);
			OpeningTime samedi = new OpeningTime(null, LocalTime.of(9, 0), LocalTime.of(18, 0), oh1);

		};
	}

}
