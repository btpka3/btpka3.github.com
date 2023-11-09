package me.test.first.spring.boot.test;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jsonSchema.annotation.JsonHyperSchema;
import com.fasterxml.jackson.module.jsonSchema.annotation.Link;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * https://github.com/FasterXML/jackson-annotations/wiki/Jackson-Annotations
 *
 * @author dangqian.zll
 * @date 2019-05-31
 * @see SerializationFeature#WRITE_DATES_WITH_ZONE_ID
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonHyperSchema(
        pathStart = "http://localhost:8080/persons/",
        links = {
                @Link(href = "{name}", rel = "self"),
                @Link(href = "{name}/pet", rel = "pet", targetSchema = Pet.class)
        })
public class MyPojo implements Serializable {

    private static final long serialVersionUID = 1L;
    public Integer myInt;

    @JsonFormat(shape = JsonFormat.Shape.STRING
//            , pattern = "yyyy-MM-dd====='T'HH:mm:ss.SSSZ"
//            , timezone = "America/Phoenix"
    )
    @JsonProperty(required = true)
    public Date myDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public ZonedDateTime myZonedDateTime;

    @JsonProperty
    @NotNull
    @Size(max = 128)
    private String name;

    @Max(200)
    @Positive
    private Integer age;


    private Pet pet0;
    private Pet pet1;

}
