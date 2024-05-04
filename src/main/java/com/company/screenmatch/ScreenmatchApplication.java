package com.company.screenmatch;

import com.company.screenmatch.models.EpisodeData;
import com.company.screenmatch.models.SeasonData;
import com.company.screenmatch.models.SeriesData;
import com.company.screenmatch.service.ApiConsumption;
import com.company.screenmatch.service.DataConverter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	//metodo que se ejecuta al iniciar la aplicacin
	@Override
	public void run(String... args) throws Exception {

		var apiConsumption = new ApiConsumption();
		var jsonResponse = apiConsumption.getData("https://www.omdbapi.com/?t=game+of+thrones&apikey=72bc7832");
		System.out.println("Formato de la seire en json: " + jsonResponse);

		//instancia de la clase DataConverter
		DataConverter converter = new DataConverter();

		//parsing from data json into a java class
		var seriesData = converter.dataJsonToClass(jsonResponse, SeriesData.class);
		System.out.println("Parsing: " + seriesData);

		//now the parsing of an episode
		jsonResponse = apiConsumption.getData("https://www.omdbapi.com/?t=game+of+thrones&season=1&episode=1&apikey=72bc7832");
		System.out.println("\nFormato de el episodio en json: " + jsonResponse);
		var episodeData = converter.dataJsonToClass(jsonResponse, EpisodeData.class);
		System.out.println("Parsing: " + episodeData);

		//season's array
		List<SeasonData> seasonsList = new ArrayList<>();

		//loop over each season to take the json format of each season and make the parsing corresponding to each season
		for (int i = 1; i <= seriesData.totalSeasons(); i++) {
			jsonResponse = apiConsumption.getData("https://www.omdbapi.com/?t=game+of+thrones&Season="+i+"&apikey=72bc7832");
			//System.out.println("Formato json de la temporada número " + i + ": " + jsonResponse);

			//parsing of each season into the record class
			var seasonsData = converter.dataJsonToClass(jsonResponse, SeasonData.class);
			seasonsList.add(seasonsData);
		}
		//print every element from the array
		seasonsList.forEach(System.out::println);

	}
}
