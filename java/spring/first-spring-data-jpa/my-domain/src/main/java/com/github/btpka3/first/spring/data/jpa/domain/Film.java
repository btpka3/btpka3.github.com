package com.github.btpka3.first.spring.data.jpa.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

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


    public static class FilmRatingEnumConverter implements AttributeConverter<FilmRatingEnum, String> {

        @Override
        public String convertToDatabaseColumn(FilmRatingEnum attribute) {

            return attribute.getCode();
        }

        @Override
        public FilmRatingEnum convertToEntityAttribute(String str) {
            Optional<FilmRatingEnum> opt = Arrays.stream(FilmRatingEnum.values())
                    .filter(e -> Objects.equals(e.getCode(), str))
                    .findFirst();
            if (opt.isPresent()) {
                return opt.get();
            }

            throw new RuntimeException("invalid code '" + str + "' for FilmRatingEnum");

        }
    }


    public static class SpecialFeaturesConverter implements AttributeConverter<EnumSet<FilmSpecialFeatureEnum>, String> {

        @Override
        public String convertToDatabaseColumn(EnumSet<FilmSpecialFeatureEnum> attribute) {

            return attribute.stream()
                    .map(e -> e.getCode())
                    .collect(Collectors.joining(","));
        }

        @Override
        public EnumSet<FilmSpecialFeatureEnum> convertToEntityAttribute(String dbData) {
            return Arrays.stream(dbData.split(","))
                    .map(s -> Arrays.stream(FilmSpecialFeatureEnum.values())
                            .filter(e -> Objects.equals(e.getCode(), s))
                            .findFirst()
                            .orElse(null)
                    )
                    .filter(Objects::nonNull)
                    .collect(Collectors.toCollection(() -> EnumSet.noneOf(FilmSpecialFeatureEnum.class)));
        }
    }
}
