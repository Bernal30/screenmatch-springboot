package com.company.screenmatch.models;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episode {
    private Integer seasonNumber;
    private String title;
    private Integer episodeNumber;
    private Double rating;
    private LocalDate dateRelease;

    //constructor personalizado
    public Episode(Integer number, EpisodeData d) {
        this.seasonNumber = number;
        this.title = d.title();
        this.episodeNumber = d.episodeNumber();
        //tratar excepción en caso de que el rating sea "N/A"
        try{
            //convierte de un String a un Double
            this.rating = Double.valueOf(d.rating());
        } catch (NumberFormatException e) {
            this.rating = 0.0;
        }
        //tratar excepción en caso de que la fecha de lanzamiento sea "N/A"
        try{
            //convertir de String a LocalDate
            this.dateRelease = LocalDate.parse(d.dateRelease());
        } catch (DateTimeParseException e){
            this.dateRelease = null;
        }
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDate getDateReales() {
        return dateRelease;
    }

    public void setDateReales(LocalDate dateReales) {
        this.dateRelease = dateReales;
    }

    @Override
    public String toString() {
        return "Número de temporada = " + seasonNumber +
                ", titulo = " + title +
                ", número de episodio = " + episodeNumber +
                ", calificación = " + rating +
                ", fecha de lanzamiento = " + dateRelease;
    }
}
