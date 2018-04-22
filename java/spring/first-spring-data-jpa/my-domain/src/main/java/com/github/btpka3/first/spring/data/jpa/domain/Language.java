package com.github.btpka3.first.spring.data.jpa.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Data
@Entity
@Table(name = "language")
public class Language {

    @Id
    @Column(name = "language_id")
    private Integer languageId;

    @Column(name = "name")
    private String name;

    @Column(name = "last_update")
    private Date lastUpdate;
}
