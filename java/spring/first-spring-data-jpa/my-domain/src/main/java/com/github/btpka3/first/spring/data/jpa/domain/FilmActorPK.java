package com.github.btpka3.first.spring.data.jpa.domain;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 电影、演员的关系表
 */
@Data
public class FilmActorPK implements Serializable {

    private static final long serialVersionUID = 1L;

    private long actorId;

    private long filmId;

}
