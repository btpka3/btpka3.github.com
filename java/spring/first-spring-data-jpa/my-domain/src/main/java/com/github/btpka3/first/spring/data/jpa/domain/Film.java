package com.github.btpka3.first.spring.data.jpa.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.EnumSet;

/**
 * 电影
 */
@Data
@Entity
@Table(name = "film")
public class Film {

    public static final String C_FILM_ID = "film_id";

    public static final String A_FILM_ID = "title";

    @Id
    @Column(name = "film_id")
    private Integer filmId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "release_year")
    private Integer releaseYear;

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
    @Convert(converter = FilmRatingEnumConverter.class)
    private FilmRatingEnum rating;

    // FIXME
    //    @ElementCollection
    //@Enumerated(EnumType.STRING)
    @Column(name = "special_features")
    @Convert(converter = SpecialFeaturesConverter.class)
    private EnumSet<FilmSpecialFeatureEnum> specialFeatures;

    @Column(name = "last_update")
    private Date lastUpdate;
}

