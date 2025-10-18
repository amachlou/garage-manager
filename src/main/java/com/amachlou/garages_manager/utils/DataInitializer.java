package com.amachlou.garages_manager.utils;

import com.amachlou.garages_manager.model.*;
import com.amachlou.garages_manager.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.Year;
import java.util.Arrays;
import java.util.List;

@Configuration
@AllArgsConstructor
public class DataInitializer {

	private GarageRepository garageRepository;
	private VehiculeRepository vehiculeRepository;
	private AccessoireRepository accessoireRepository;
	private OpeningHourRepository openingHourRepository;
	private OpeningTimeRepository openingTimeRepository;

	@Bean
	public CommandLineRunner commandLineRunner(){
		return args -> {

			Garage g1 = garageRepository.save(new Garage(null,"DAR BOUAZZA", "Ru2 01", "12334455", "test@email.com","Casablanca", null, null));
			Garage g2 = garageRepository.save(new Garage(null,"Inzegan", "Bds Muqawama", "7009955", "dev@email.com","Agadir",null,null));
			Garage g3 = garageRepository.save(new Garage(null,"Rabat", "Hay Riyad", "+092334455", "prod@email.com","Tanger",null,null));

			Arrays.asList(g1,g2,g3).forEach(garage -> {
				List<Vehicule> vehicules = Arrays.asList(
								vehiculeRepository.save(new Vehicule(null, "MD01", "SEDAN", "Renault", Year.of(2020), "Diesel", garage, null)),
								vehiculeRepository.save(new Vehicule(null, "MD02", "COPE", "BENZ", Year.of(2022), "Gaz", garage, null)),
								vehiculeRepository.save(new Vehicule(null, "MD03", "CABRIOLET", "TESLA", Year.of(2023), "Gasoil", garage, null)));

				vehicules.forEach(vehicule -> {
							accessoireRepository.save(new Accessoire(null, "Pignon", "10cm P", 76.8, "Originl", vehicule));
							accessoireRepository.save(new Accessoire(null, "Cable", "15cm P", 6.8, "CAT 2", vehicule));
							accessoireRepository.save(new Accessoire(null, "Cle", "25cm P", 00.8, "Super", vehicule));
						});
			});

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

		};
	}

}
