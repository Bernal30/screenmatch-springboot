package com.company.screenmatch.Main;

import com.company.screenmatch.models.Episode;
import com.company.screenmatch.models.EpisodeData;
import com.company.screenmatch.models.SeasonData;
import com.company.screenmatch.models.SeriesData;
import com.company.screenmatch.service.ApiConsumption;
import com.company.screenmatch.service.DataConverter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
//        for (int i = 0; i < pasringSerieData.totalSeasons(); i++) {
//            //episodes array of each season
//            List<EpisodeData> episodesOnSeason = seasonsList.get(i).episodesList();
//            for (int j = 0; j < episodesOnSeason.size(); j++) {
//                System.out.println(episodesOnSeason.get(j).title());
//            }
//        }

        //lambda function to replace the bucle for inside another bucle for
        //se imprimen todos los episodios de la serie
        //seasonsList.forEach(t -> t.episodesList().forEach(e -> System.out.println(e.title())));

        //Convertir todas las informaciones enuna lista del tipo EpisodeData
        List<EpisodeData> episodeData = seasonsList.stream()
                .flatMap(t -> t.episodesList().stream())
                .collect(Collectors.toList());

        //Top 5 episodes by rating
        System.out.println("--- Top 5 episodios por calificaión ---");
        episodeData.stream()
                .filter(e -> !e.rating().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(EpisodeData::rating).reversed())
                .limit(5)
                .forEach(System.out::println);

        //Convirtiendo los datos a una lista del tipo Episode
        List<Episode> episodes = seasonsList.stream()
                .flatMap(t -> t.episodesList().stream()
                        .map(d -> new Episode(t.seasonNumber(), d)))
                .collect(Collectors.toList());

        //imprimir cada elemnto del array episodes
        episodes.forEach(System.out::println);

    }
}
