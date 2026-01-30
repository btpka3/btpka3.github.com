package me.test.jdk.java.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author dangqian.zll
 * @date 2026/1/26
 */
public class PropertiesUtils {

    @SneakyThrows
    public static String removeCommentAndSorted(String propsStr) {
        StringReader reader = new StringReader(propsStr);
        Properties props = new Properties();
        props.load(reader);

        StringWriter stringWriter = new StringWriter();
        props.store(stringWriter, null);
        String resultPropsStr = stringWriter.toString();

        String[] lines = StringUtils.split(resultPropsStr, System.lineSeparator());
        return Arrays.stream(lines)
                .filter(str -> !str.startsWith("#") && !str.startsWith("!"))
                .sorted()
                .collect(Collectors.joining(System.lineSeparator()));
    }

}
