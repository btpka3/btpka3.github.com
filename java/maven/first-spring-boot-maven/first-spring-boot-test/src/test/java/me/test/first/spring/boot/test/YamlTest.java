package me.test.first.spring.boot.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author dangqian.zll
 * @date 2023/6/21
 */
public class YamlTest {

    @SneakyThrows
    @Test
    public void test01() {
        LoaderOptions options = new LoaderOptions();
        Yaml yaml = new Yaml(options);
        String document = IOUtils.toString(
                YamlTest.class.getResourceAsStream("yaml-001.yaml"),
                StandardCharsets.UTF_8
        );

        Map map = yaml.load(document);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map));
    }
}
