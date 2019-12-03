package me.test.first.spring.boot.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pet implements Serializable {

    private static final long serialVersionUID = 1L;


    private String name;
    private String type;


}
