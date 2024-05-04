package com.company.screenmatch.Main;

import com.company.screenmatch.models.EpisodeData;
import com.company.screenmatch.models.SeasonData;
import com.company.screenmatch.models.SeriesData;
import com.company.screenmatch.service.ApiConsumption;
import com.company.screenmatch.service.DataConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private Scanner keyboard = new Scanner(System.in);
    //instancia de la clase ApiConsumption
    private ApiConsumption apiConsumption = new ApiConsumption();
    //constants for the static URL
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=72bc7832";
    //instance of the converter to json into java class
    private DataConverter dataConverter = new DataConverter();

    public void showMenu(){
        System.out.print("Ingrese el nombre de la serie que quiera buscar: ");
        var userSerieName = keyboard.nextLine();

        //petición a la api y devuelve un formato json
        var jsonResponse = apiConsumption.getData(URL_BASE + userSerieName.replace(" ", "+") + API_KEY);

        //parsing of the user serie
        var pasringSerieData = dataConverter.dataJsonToClass(jsonResponse, SeriesData.class);
        System.out.println("Parsing: " + pasringSerieData);

        //season's array for searching data of each seasons
        List<SeasonData> seasonsList = new ArrayList<>();

        //loop over each season to take the json format of each season and make the parsing corresponding to each season
        for (int i = 1; i <= pasringSerieData.totalSeasons(); i++) {
            jsonResponse = apiConsumption.getData(URL_BASE + userSerieName.replace(" ", "+") + "&Season=" +i+ API_KEY);
            //System.out.println("Formato json de la temporada número " + i + ": " + jsonResponse);

            //parsing of each season into the record class
            var seasonsData = dataConverter.dataJsonToClass(jsonResponse, SeasonData.class);
            seasonsList.add(seasonsData);
        }
        //print every element from the array
        //seasonsList.forEach(System.out::println);

        //shows only the name of the episode of each season
        for (int i = 0; i < pasringSerieData.totalSeasons(); i++) {
            //episodes array of each season
            List<EpisodeData> episodesOnSeason = seasonsList.get(i).episodesList();
            for (int j = 0; j < episodesOnSeason.size(); j++) {
                System.out.println(episodesOnSeason.get(j).title());
            }
        }

    }
}
