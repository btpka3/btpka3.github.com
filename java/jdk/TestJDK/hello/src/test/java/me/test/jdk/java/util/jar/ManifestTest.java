package me.test.jdk.java.util.jar;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * @author dangqian.zll
 * @date 2024/1/26
 */
public class ManifestTest {

    @Test
    public void test() throws IOException {
        String manifestPath = "/META-INF/MANIFEST.MF";
        URL url = StringUtils.class.getResource(manifestPath);
        System.out.println("URL(\"" + manifestPath + "\") = " + url);

        Manifest manifest = new Manifest(StringUtils.class.getResourceAsStream(manifestPath));
        Attributes attributes = manifest.getMainAttributes();
        String exportPackages = attributes.getValue("Export-Package");
        System.out.println("Export-Package = " + exportPackages);
    }
}
