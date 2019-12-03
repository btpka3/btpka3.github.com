package com.github.btpka3.first.spring.data.jpa.domain;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 *
 */
public class FilmRatingEnumConverter implements AttributeConverter<FilmRatingEnum, String> {

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