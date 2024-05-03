package com.company.screenmatch;

import com.company.screenmatch.models.EpisodeData;
import com.company.screenmatch.models.SeriesData;
import com.company.screenmatch.service.ApiConsumption;
import com.company.screenmatch.service.DataConverter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	//metodo que se ejecuta al iniciar la aplicacin
	@Override
	public void run(String... args) throws Exception {

		var apiConsumption = new ApiConsumption();
		var jsonResponse = apiConsumption.getData("https://www.omdbapi.com/?apikey=72bc7832&t=game+of+thrones");
		System.out.println("Formato de la seire en json: " + jsonResponse);

		//instancia de la clase DataConverter
		DataConverter converter = new DataConverter();

		//parsing from data json into a java class
		var seriesData = converter.dataJsonToClass(jsonResponse, SeriesData.class);
		System.out.println(seriesData);

		//now the parsing of an episode
		jsonResponse = apiConsumption.getData("https://www.omdbapi.com/?t=game+of+thrones&season=1&episode=1&apikey=72bc7832");
		System.out.println("\nFormato de el episodio en json: " + jsonResponse);
		var episodeData = converter.dataJsonToClass(jsonResponse, EpisodeData.class);
		System.out.println(episodeData);

	}
}
