package me.test.first.spring.boot.test;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author dangqian.zll
 * @date 2025/12/25
 * @see <a href="https://www.baeldung.com/java-jackson-polymorphic-deserialization">@JsonSubTypes vs. Reflections for Polymorphic Deserialization in Jackson</a>
 */
public class JacksonSubClassTest {

    @SneakyThrows
    @Test
    public void test01() {
        // language=JSON
        String jsonStr = """
                {
                   "name": "TangBoHuDianQiuXiang",
                   "animalList": [
                       {
                          "type": "cockroach",
                          "name": "XiaoQiang",
                          "weight": 10
                       },
                       {
                          "type": "dog",
                          "name": "WangCai",
                          "height": 20
                       },
                       {
                          "type": "cat",
                          "name": "MiMi",
                          "color": "black"
                       }
                   ]
                }
                """;

        Zoo zoo = new ObjectMapper()
                //.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true)
                .configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
                //.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false)
                .readerFor(Zoo.class)
                .readValue(jsonStr);

        assertEquals("TangBoHuDianQiuXiang", zoo.getName());
        List<Animal> animalList = zoo.getAnimalList();
        assertEquals(3, animalList.size());

        Animal animal0 = animalList.get(0);
        assertInstanceOf(Cockroach.class, animal0);
        Cockroach cockroach = (Cockroach) animal0;
        assertEquals("cockroach", cockroach.getType());
        assertEquals("XiaoQiang", cockroach.getName());
        assertEquals(10, cockroach.getWeight());

        Animal animal1 = animalList.get(1);
        assertInstanceOf(Dog.class, animal1);
        Dog dog = (Dog) animal1;
        assertEquals("dog", dog.getType());
        assertEquals("WangCai", dog.getName());
        assertEquals(20, dog.getHeight());

        // 未配置的，仍将使用父类
        Animal animal2 = animalList.get(2);
        assertFalse(animal2 instanceof Cat);
        assertEquals("cat", animal2.getType());
        assertEquals("MiMi", animal2.getName());
    }

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.EXISTING_PROPERTY,
            property = "type",
            visible = true
    )
    @JsonSubTypes({
            @JsonSubTypes.Type(value = Cockroach.class, name = "cockroach"),
            @JsonSubTypes.Type(value = Dog.class, name = "dog"),
            @JsonSubTypes.Type(value = Animal.class, name = "cat"),
    })
    @Data
    public static class Animal {
        private String type;
        private String name;
    }

    @Data
    public static class Cockroach extends Animal {
        private int weight;
    }

    @Data
    public static class Dog extends Animal {
        private int height;
    }

    @Data
    public static class Cat extends Animal {
        private String color;
    }

    @Data
    public static class Zoo {
        private String name;
        private List<Animal> animalList;
    }
}
