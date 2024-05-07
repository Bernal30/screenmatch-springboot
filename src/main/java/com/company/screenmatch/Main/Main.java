package com.company.screenmatch.Main;

import com.company.screenmatch.models.Episode;
import com.company.screenmatch.models.EpisodeData;
import com.company.screenmatch.models.SeasonData;
import com.company.screenmatch.models.SeriesData;
import com.company.screenmatch.service.ApiConsumption;
import com.company.screenmatch.service.DataConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
                .peek(e -> System.out.println("Primer filtro (N/A) " + e))
                .sorted(Comparator.comparing(EpisodeData::rating).reversed())
                //.peek(e -> System.out.println("Segundo filtro (Ordenar de mayor a menor) " + e))
                .limit(5)
                //.map(e -> e.title().toUpperCase())
                //.peek(e -> System.out.println("Tercer filtro titulos en mayusculas " + e))
                .forEach(System.out::println);

        //Convirtiendo los datos a una lista del tipo Episode
        List<Episode> episodes = seasonsList.stream()
                .flatMap(t -> t.episodesList().stream()
                        .map(d -> new Episode(t.seasonNumber(), d)))
                .collect(Collectors.toList());

        //imprimir cada elemento del array episodes
        //episodes.forEach(System.out::println);

        //busqueda de episodio a partir de x año de estreno
        System.out.print("""
                --- Busqueda de capitulos por año de lanzamiento ---
                Ingrese el año de la fecha de lanzamiento:
                """);
        var userYear = keyboard.nextInt();

        LocalDate dateSearch = LocalDate.of(userYear, 1, 1);

        //modificamos el formato de como se muestra la fecha de lanzamiento
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        //modificar la lista de episodios para la busqueda
        episodes.stream()
                .filter(e -> e.getDateReales() != null && e.getDateReales().isAfter(dateSearch))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getSeasonNumber() +
                                ", Episodio: " + e.getTitle() +
                                ", Fecha de lanzamiento: " + e.getDateReales().format(dtf)
                ));

        //buscar un episodio por el titulo
        System.out.println("""
                --- Busqueda de un episodio por el titulo ---
                Ingrese el tituo del episodio que desea buscar: 
                """);
        var userEpisodeTitle = keyboard.next();
        Optional<Episode> searchedEpisode = episodes.stream()
                .filter(e -> e.getTitle().contains(userEpisodeTitle))
                .findFirst();

        if (searchedEpisode.isPresent()) {
            System.out.println("Episodio encontrado");
            System.out.println("Los datos son : " + searchedEpisode.get());
        } else {
            System.out.println("Episodio no encontrado");
        }

        keyboard.close();
    }
}


