/*
 * This file is generated by jOOQ.
 */
package com.github.btpka3.first.spring.jooq.domain.tables.pojos;


import com.github.btpka3.first.spring.jooq.domain.enums.NicerButSlowerFilmListRating;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * VIEW
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class NicerButSlowerFilmList implements Serializable {

    private static final long serialVersionUID = 1L;

    private Short                        fid;
    private String                       title;
    private String                       description;
    private String                       category;
    private BigDecimal                   price;
    private Short                        length;
    private NicerButSlowerFilmListRating rating;
    private String                       actors;

    public NicerButSlowerFilmList() {}

    public NicerButSlowerFilmList(NicerButSlowerFilmList value) {
        this.fid = value.fid;
        this.title = value.title;
        this.description = value.description;
        this.category = value.category;
        this.price = value.price;
        this.length = value.length;
        this.rating = value.rating;
        this.actors = value.actors;
    }

    public NicerButSlowerFilmList(
        Short                        fid,
        String                       title,
        String                       description,
        String                       category,
        BigDecimal                   price,
        Short                        length,
        NicerButSlowerFilmListRating rating,
        String                       actors
    ) {
        this.fid = fid;
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.length = length;
        this.rating = rating;
        this.actors = actors;
    }

    /**
     * Getter for <code>sakila.nicer_but_slower_film_list.FID</code>.
     */
    public Short getFid() {
        return this.fid;
    }

    /**
     * Setter for <code>sakila.nicer_but_slower_film_list.FID</code>.
     */
    public NicerButSlowerFilmList setFid(Short fid) {
        this.fid = fid;
        return this;
    }

    /**
     * Getter for <code>sakila.nicer_but_slower_film_list.title</code>.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Setter for <code>sakila.nicer_but_slower_film_list.title</code>.
     */
    public NicerButSlowerFilmList setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Getter for <code>sakila.nicer_but_slower_film_list.description</code>.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Setter for <code>sakila.nicer_but_slower_film_list.description</code>.
     */
    public NicerButSlowerFilmList setDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Getter for <code>sakila.nicer_but_slower_film_list.category</code>.
     */
    public String getCategory() {
        return this.category;
    }

    /**
     * Setter for <code>sakila.nicer_but_slower_film_list.category</code>.
     */
    public NicerButSlowerFilmList setCategory(String category) {
        this.category = category;
        return this;
    }

    /**
     * Getter for <code>sakila.nicer_but_slower_film_list.price</code>.
     */
    public BigDecimal getPrice() {
        return this.price;
    }

    /**
     * Setter for <code>sakila.nicer_but_slower_film_list.price</code>.
     */
    public NicerButSlowerFilmList setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    /**
     * Getter for <code>sakila.nicer_but_slower_film_list.length</code>.
     */
    public Short getLength() {
        return this.length;
    }

    /**
     * Setter for <code>sakila.nicer_but_slower_film_list.length</code>.
     */
    public NicerButSlowerFilmList setLength(Short length) {
        this.length = length;
        return this;
    }

    /**
     * Getter for <code>sakila.nicer_but_slower_film_list.rating</code>.
     */
    public NicerButSlowerFilmListRating getRating() {
        return this.rating;
    }

    /**
     * Setter for <code>sakila.nicer_but_slower_film_list.rating</code>.
     */
    public NicerButSlowerFilmList setRating(NicerButSlowerFilmListRating rating) {
        this.rating = rating;
        return this;
    }

    /**
     * Getter for <code>sakila.nicer_but_slower_film_list.actors</code>.
     */
    public String getActors() {
        return this.actors;
    }

    /**
     * Setter for <code>sakila.nicer_but_slower_film_list.actors</code>.
     */
    public NicerButSlowerFilmList setActors(String actors) {
        this.actors = actors;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("NicerButSlowerFilmList (");

        sb.append(fid);
        sb.append(", ").append(title);
        sb.append(", ").append(description);
        sb.append(", ").append(category);
        sb.append(", ").append(price);
        sb.append(", ").append(length);
        sb.append(", ").append(rating);
        sb.append(", ").append(actors);

        sb.append(")");
        return sb.toString();
    }
}