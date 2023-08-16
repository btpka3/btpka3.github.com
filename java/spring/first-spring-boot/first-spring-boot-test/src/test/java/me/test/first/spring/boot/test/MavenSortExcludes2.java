package me.test.first.spring.boot.test;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author dangqian.zll
 * @date 2023/8/16
 */
public class MavenSortExcludes2 {

    @Test
    @SneakyThrows
    public void sortByJSoup() {
        String xmlStr = IOUtils.toString(MavenSortExcludes2.class.getResourceAsStream("MavenSortExcludes2.xml"), StandardCharsets.UTF_8);
        Document doc = Jsoup.parse(xmlStr);
        Element rootEle = doc.getElementsByTag("exclusions").get(0);
        Elements oldRoot = rootEle.children();

        Comparator<Element> comparator = Comparator
                .comparing((Element element) -> element.getElementsByTag("groupId").text())
                .thenComparing((Element element) -> element.getElementsByTag("artifactId").text());

        Collection<Element> list = io.vavr.collection.Stream.ofAll(oldRoot)
                // 修正 groupId, artifactId 的顺序
                .peek(this::sortGroupIdAndArtifactId)
                // 去重
                .distinctBy(comparator)
                // 排序
                .sorted(comparator)
                .collect(Collectors.toList());

        Element newRoot = doc.createElement("exclusions");
        newRoot.appendChildren(list);


        System.out.println("============================= START");
        System.out.println(newRoot.toString());
        System.out.println("============================= END");
    }

    protected void sortGroupIdAndArtifactId(Element element) {
        Element node0 = element.child(0);
        if (Objects.equals("artifactId", node0.nodeName())) {
            node0.remove();
            node0.appendTo(element);
        }
    }


}
