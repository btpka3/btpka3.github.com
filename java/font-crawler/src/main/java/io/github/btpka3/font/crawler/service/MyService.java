package io.github.btpka3.font.crawler.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class MyService {

    String DOMAIN = "www.1001freefonts.com";
    String NEW_DOWNLOAD_URL_TPL = "https://www.1001freefonts.com/new-fonts-{n}.php";
    String TOP_DOWNLOAD_URL_TPL = "https://www.1001freefonts.com/top-fonts-{n}.php";
    String NEW_FILE_NAME = "font-crawler.1001freefonts.top-fonts.json";
    String TOP_FILE_NAME = "font-crawler.1001freefonts.top-fonts.json";

    List<String> fontSuffix = Arrays.asList(
            "eot",
            "otf",
            "fon",
            "font",
            "ttf",
            "ttc",
            "woff",
            "woff2"
    );

    UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(TOP_DOWNLOAD_URL_TPL);

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;


    public int add(int a, int b) {
        int c = a + b;
        System.out.println("a+b = " + a + " + " + b + " = " + c);
        //json();
        //System.out.println(IntStream.range(1, 10).mapToObj(i-> i+1).collect(Collectors.toList()));
        return c;
    }

    private String loadHtml(int page) {
        URI uri = uriComponentsBuilder.buildAndExpand(page)
                .toUri();

        String html = restTemplate.getForObject(uri, String.class);
        System.out.println(page);

        return html;
    }


    private Stream<FontInfo> parseHtml(String html) {
        Document doc = Jsoup.parse(html);

        return doc.select("ul.fontListingList > li").stream()
                .map((Element li) -> {
                    FontInfo info = new FontInfo();

                    Elements previewHeader = li.select("div.fontPreviewHeaderInner.responsiveHide");

                    Elements a = previewHeader.select("div.fontPreviewTitle > a");
                    info.setName(a.get(0).text());
                    info.setBy(a.get(1).text());

                    info.setCategories(previewHeader.select("div.fontTopCategories > a").eachText());
                    info.setDownloadUrl(li.select("div.downloadButtonElement > a").attr("href"));

                    return info;
                });

    }

    public void json() {

        int startPage = 1;
        // 2037
        int endPage = 500;
        List<FontInfo> fonts = IntStream.range(startPage, endPage + 1)
                .parallel()
                .mapToObj(this::loadHtml)
                .flatMap(this::parseHtml)
                .collect(Collectors.toList());

        Map<String, Object> m = new LinkedHashMap<>();
        m.put("fonts", fonts);
        Map<String, Map<String, Object>> result = new LinkedHashMap<>();
        result.put(DOMAIN, m);

        try {
            objectMapper.writeValue(new File(TOP_FILE_NAME), result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        System.out.println(fonts);

    }


    private void download(String url, String fileName) {

        byte[] bytes = restTemplate.getForObject(url, byte[].class);

        try {
            IOUtils.write(bytes, new FileOutputStream(new File(".tmp", fileName)));
            System.out.println(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void unzip(String fileName) {
        Path zipFile = Paths.get(fileName);
        Map<String, ?> env = new HashMap<>();
        try (FileSystem zipfs = FileSystems.newFileSystem(zipFile.toUri(), env)) {
            Files.walk(zipfs.getPath("/"))
                    .filter(path -> fontSuffix.stream().anyMatch(s -> path.endsWith(s)))
                    .forEach(path -> {
                        try {
                            Files.copy(path, Paths.get(".tmp/font"));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("::" + path);
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void download() {
        try {
            Map m = objectMapper.readValue(new File(TOP_FILE_NAME), Map.class);

            ((List<Map<String, String>>) ((Map) m.get(DOMAIN)).get("fonts"))
                    .stream()
                    .parallel()
                    .forEach(fontInfo -> {

                        String url = fontInfo.get("downloadUrl");
                        String fileName = url.substring(url.lastIndexOf('/') + 1);

                        this.download(
                                fontInfo.get("downloadUrl"),
                                fileName
                        );
                    });
            System.out.println(m);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
