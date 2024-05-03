package com.company.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EpisodeData(
        @JsonAlias("Title") String title,
        @JsonAlias("Episode") Integer episodeNumber,
        @JsonAlias("imdbRating") String rating,
        @JsonAlias("Released") String dateRelease
) {
    @Override
    public String toString() {
        return "Datos del episodio{" +
                "titulo = " + title +
                ", número del episodio = " + episodeNumber +
                ", calificaión = " + rating +
                ", fecha de lanzamiento = " + dateRelease +
                '}';
    }
}
