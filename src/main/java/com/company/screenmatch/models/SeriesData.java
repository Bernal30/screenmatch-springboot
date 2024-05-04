package com.company.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//solo mapea los atributos declarados en esta clase
@JsonIgnoreProperties(ignoreUnknown = true)
public record SeriesData(
        //con JsonAlias se identifica el atributo estrictamente escritpo como está en el json
        @JsonAlias("Title") String title,
        @JsonAlias("totalSeasons") Integer totalSeasons,
        @JsonAlias("imdbRating") Double rating
) {

    @Override
    public String toString() {
        return "Datos de la serie{" +
                "titulo = " + title +
                ", temporadas totales = " + totalSeasons +
                ", calificaión = " + rating +
                '}';
    }
}
