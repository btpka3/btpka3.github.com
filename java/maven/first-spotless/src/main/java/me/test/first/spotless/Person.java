package me.test.first.spotless;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

    private String name;
    private Integer age;
    private String gender;
    private String job;
}
