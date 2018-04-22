package com.github.btpka3.first.spring.data.jpa.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 电影、演员的关系表
 */
@Data
@Entity
@Table(name = "film_actor")
@IdClass(FilmActorPK.class)
public class FilmActor {
    public static final String C_ACTOR_ID = "actor_id";
    public static final String C_FILM_ID = "film_id";
    public static final String C_LAST_UPDATE = "last_update";

    @Id
    @Column(name = C_ACTOR_ID)
    private Integer actorId;

    @Id
    @Column(name = C_FILM_ID)
    private Integer filmId;

    @Column(name = C_LAST_UPDATE)
    private Date lastUpdate;

    @ManyToOne
    //@JoinColumn(name = "actor_id", updatable = false, insertable = false)
    @PrimaryKeyJoinColumn(name = C_ACTOR_ID)
    private Actor actor;

    @ManyToOne()
    @PrimaryKeyJoinColumn(name = C_FILM_ID)
    private Film film;

}
