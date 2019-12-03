package me.test.first.spring.boot.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author dangqian.zll
 * @date 2019-05-31
 */
public class JsonSchemaTest {

    @Test
    public void test01() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        //mapper.enable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID);


        JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);


        JsonSchema schema = schemaGen.generateSchema(MyPojo.class);

        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(schema));

        //
        com.kjetland.jackson.jsonSchema.JsonSchemaGenerator g = new com.kjetland.jackson.jsonSchema.JsonSchemaGenerator(mapper);


        System.out.println("========================");
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(g.generateJsonSchema(MyPojo.class)));

//        MyPojo pojo = MyPojo.builder()
//                .age(10)
//                .myDate(new Date())
//                .myInt(11)
//                .myZonedDateTime(ZonedDateTime.now())
//                .name("zhang3")
//                .pet0(Pet.builder()
//                        .name("bb")
//                        .type("cat")
//                        .build())
//                .pet1(Pet.builder()
//                        .name("kk")
//                        .type("dog")
//                        .build())
//                .build();
//
//        System.out.println("========================");
//        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pojo));

    }
}
