package com.github.btpka3.first.spring.data.jpa.domain;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 */
public class SpecialFeaturesConverter implements AttributeConverter<EnumSet<FilmSpecialFeatureEnum>, String> {

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