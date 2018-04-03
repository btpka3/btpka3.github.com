package com.github.btpka3.first.spring.data.jpa.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Data
@ToString
//@Entity(name = "film")
public class Film {

    @Id
    @Column(name = "film_id")
    private Integer filmId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "release_year")
    private Date releaseYear;

    @Column(name = "original_language_id")
    private Integer originalLanguageId;

    @Column(name = "rental_duration")
    private Integer rentalDuration;

    @Column(name = "rental_rate")
    private Double rentalRate;

    @Column(name = "length")
    private Integer length;

    @Column(name = "replacement_cost")
    private Double replacementCost;

    @Column(name = "rating")
    private FilmRatingEnum rating;

    // FIXME
    @Column(name = "special_features")
    private List<FilmSpecialFeatureEnum> specialFeatures;

    @Column(name = "last_update")
    private Date lastUpdate;

}
