package me.test.org.apache.commons.text;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.commons.text.StringSubstitutor;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/4/30
 */
public class StringSubstitutorTest {

    @Test
    public void replaceSystemProperties01() {
        String result = StringSubstitutor.replaceSystemProperties("java.version=${java.version}, os.name=${os.name}.");
        System.out.println("============");
        System.out.println(result);
        System.out.println("============");
    }

    @Test
    public void customMap01() {
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("a", "aaa");
        valuesMap.put("b", "bbb");
        String tpl = "a=${a}, b=${b}, c=${c:-ccc}.";

        StringSubstitutor sub = new StringSubstitutor(valuesMap);

        String result = sub.replace(tpl);
        System.out.println("============");
        System.out.println(result);
        System.out.println("============");
    }

    @SneakyThrows
    @Test
    public void interpolator01() {
        System.out.println("locale = " + Locale.getDefault());
        Locale.setDefault(Locale.CHINA);
        System.out.println("locale = " + Locale.getDefault());

        try (Writer writer = new FileWriter("/tmp/document.properties")) {
            IOUtils.write("a=aaa\nb=bbb\nmykey=myValue\n", writer);
        }
        try (Writer writer = new FileWriter("/tmp/document.xml")) {
            IOUtils.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                            + "<root>\n"
                            + "  <path>\n"
                            + "    <to>\n"
                            + "    <node>node001</node>\n"
                            + "    </to>\n"
                            + "  </path>\n"
                            + "</root>\n"
                    , writer);
        }

        final StringSubstitutor interpolator = StringSubstitutor.createInterpolator();


        final String result = interpolator.replace(
                "Base64 Decoder:        ${base64Decoder:SGVsbG9Xb3JsZCE=}\n"
                        + "Base64 Encoder:        ${base64Encoder:HelloWorld!}\n"
                        + "Java Constant:         ${const:java.awt.event.KeyEvent.VK_ESCAPE}\n"
                        + "Date:                  ${date:yyyy-MM-dd}\n"
                        + "Environment Variable:  ${env:USER}\n"
                        + "File Content:          ${file:UTF-8:/tmp/document.properties}\n"
                        + "Java:                  ${java:version}\n"
                        + "Localhost:             ${localhost:canonical-name}\n"
                        + "Properties File:       ${properties:/tmp/document.properties::mykey}\n"
                        + "Resource Bundle:       ${resourceBundle:me.demoResourceBundle:mykey}\n"
                        + "System Property:       ${sys:user.dir}\n"
                        + "URL Decoder:           ${urlDecoder:Hello%20World%21}\n"
                        + "URL Encoder:           ${urlEncoder:Hello World!}\n"
                        + "XML XPath:             ${xml:/tmp/document.xml:/root/path/to/node}\n");
        System.out.println("============");
        System.out.println(result);
        System.out.println("============");
    }

}

