package me.test;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws IOException {
        mapToJson1();
        jsonToMap1();
    }


    public static void mapToJson1() throws IOException {

        System.out.println("=====================================================111");

        ObjectMapper mapper = new ObjectMapper();
        Map map = new LinkedHashMap();
        map.put("string", "aaa");
        map.put("date", new Date());
        map.put("html", "aaa<>\"'&中文111");
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map));

        System.out.println("=====================================================222");

        ObjectMapper mapper1 = new ObjectMapper();
        JsonFactory f = new JsonFactory();
        f.enable(JsonGenerator.Feature.ESCAPE_NON_ASCII);
        StringWriter s = new StringWriter();
        JsonGenerator g = f.createGenerator(s);
        mapper1.writerWithDefaultPrettyPrinter().writeValue(g, map);
        System.out.println(s.toString());
    }

    public static void jsonToMap1() throws IOException {
        System.out.println("===================================================== jsonToMap1 111");
        String json = "{\"string\" : \"aaa\",\"date\" : 1450245761324,\"html\" : \"aaa<>\\\"'&\\u4E2D\\u6587111\"}";
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map;
        map = mapper.readValue(json, new TypeReference<Map<String, Object>>() {
        });
        System.out.println(map);
        System.out.println(map.get("date").getClass());
    }
}
