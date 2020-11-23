package me.test.first.spring.boot.test;

import org.apache.tika.Tika;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.junit.jupiter.api.Test;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * @author dangqian.zll
 * @date 2020/11/5
 */
public class TikaTest {

    @Test
    public void detectMimetype01() throws TikaException, IOException {
        TikaConfig tika = new TikaConfig();

        File dir = new File("/Users/zll/Downloads");

        System.out.printf("%50s | %30s%n", "file", "mimetype");
        Stream.of(dir.listFiles())
                .filter(File::isFile)
                .sorted(Comparator.comparing(File::getName))
                .forEach(f -> {
                    Metadata metadata = new Metadata();
                    metadata.set(Metadata.RESOURCE_NAME_KEY, f.toString());
                    MediaType mimetype = null;
                    try (TikaInputStream inputStream = TikaInputStream.get(f)) {
                        mimetype = tika.getDetector()
                                .detect(inputStream, metadata);
                        System.out.printf("%-50s | %30s%n", f.getName(), mimetype);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });
    }


    @Test
    public void detectMimetype02() {

        File dir = new File("/Users/zll/Downloads");

        System.out.printf("%50s | %30s%n", "file", "mimetype");
        Stream.of(dir.listFiles())
                .filter(File::isFile)
                .sorted(Comparator.comparing(File::getName))
                .forEach(f -> {
                    try {
                        String mimetype = Files.probeContentType(f.toPath());
                        System.out.printf("%-50s | %30s%n", f.getName(), mimetype);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });
    }

    @Test
    public void parseToStringExample01() {
        Tika tika = new Tika();
        try (InputStream stream = TikaTest.class.getResourceAsStream("tika-01.docx")) {
            String str = tika.parseToString(stream);
            System.out.println("str = " + str);
        } catch (IOException | TikaException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void parseToString4Html() {
        Tika tika = new Tika();
        try (InputStream stream = TikaTest.class.getResourceAsStream("tika-01.html")) {
            String str = tika.parseToString(stream);
            System.out.println("str = " + str);
        } catch (IOException | TikaException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void parseImageExample01() {

        ContentHandler handler = new ContentHandler() {
            @Override
            public void setDocumentLocator(Locator locator) {

            }

            @Override
            public void startDocument() throws SAXException {

            }

            @Override
            public void endDocument() throws SAXException {

            }

            @Override
            public void startPrefixMapping(String prefix, String uri) throws SAXException {

            }

            @Override
            public void endPrefixMapping(String prefix) throws SAXException {

            }

            @Override
            public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
                System.out.println("startElement : " + uri + ", " + localName + ", " + qName + ", " + atts);
            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {

            }

            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {

            }

            @Override
            public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {

            }

            @Override
            public void processingInstruction(String target, String data) throws SAXException {

            }

            @Override
            public void skippedEntity(String name) throws SAXException {

            }
        };

        Tika tika = new Tika();
        try (InputStream stream = TikaTest.class.getResourceAsStream("tika-01.docx")) {
            Metadata metadata = new Metadata();
            AutoDetectParser parser = new AutoDetectParser();
            parser.parse(stream, handler, metadata);
            System.out.println("str = " + handler.toString());
        } catch (IOException | TikaException | SAXException e) {
            e.printStackTrace();
        }
    }


}
