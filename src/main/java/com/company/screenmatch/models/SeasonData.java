package com.company.screenmatch.models;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeasonData(
        @JsonAlias("Season") Integer seasonNumber,
        @JsonAlias("Episodes") List<EpisodeData> episodesList

) {
    @Override
    public String toString() {
        return "Datos de la temporada: {" +
                "número de la temporada = " + seasonNumber +
                ", Lista de episodios = " + episodesList +
                '}';
    }
}
