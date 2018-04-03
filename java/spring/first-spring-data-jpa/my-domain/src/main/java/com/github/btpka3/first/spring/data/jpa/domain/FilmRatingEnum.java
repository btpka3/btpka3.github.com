package com.github.btpka3.first.spring.data.jpa.domain;

public enum FilmRatingEnum {

    E_G("G"),

    E_PG("PG"),

    E_PG13("PG-13"),

    E_R("R"),

    E_NC17("NC-17");

    FilmRatingEnum(String code) {
        this.code = code;
    }

    private String code;

    public String getCode() {
        return code;
    }
}
