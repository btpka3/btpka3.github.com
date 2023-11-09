package me.test.first.spring.boot.test;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.util.AntPathMatcher;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 */
public class FindPlaceHolderInUseTest {

    @Test
    public void x() {

        Set<String> excludeFileNames = new HashSet<>(Arrays.asList(

        ));


        Set<String> keys = new HashSet<>(Arrays.asList(

        ));

        new AntPathMatcher();


    }

    @Test
    public void x1() {
        System.out.println(getKeys());
    }

    @SneakyThrows
    protected Set<String> getKeys() {
        List<String> lines = IOUtils.readLines(
                FindPlaceHolderInUseTest.class.getResourceAsStream("FindPlaceHolderInUseTest.properties"),
                StandardCharsets.UTF_8
        );
        return lines.stream()
                .filter(StringUtils::isNotBlank)
                .map(StringUtils::trim)
                .collect(Collectors.toSet());
    }
}
