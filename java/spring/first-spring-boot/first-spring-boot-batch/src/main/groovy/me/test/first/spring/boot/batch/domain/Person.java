package me.test.first.spring.boot.batch.domain;


import lombok.*;


// @Data = @ToString + @EqualsAndHashCode + @Getter + @Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private String lastName;
    private String firstName;
}
