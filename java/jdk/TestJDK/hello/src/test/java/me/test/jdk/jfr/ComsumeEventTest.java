package me.test.jdk.jfr;


import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingFile;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * 消费事件
 */
public class ComsumeEventTest {


    /**
     * 解析JFR文件并处理其中的事件。
     */
    @SneakyThrows
    @Test
    public void loadEvent() {
        Path path = Files.createTempFile("recording", ".jfr");
        List<RecordedEvent> events = RecordingFile.readAllEvents(path);
        events.forEach(event -> {
            // get field value
            String info = event.getValue("info");
            System.out.println(info);
            // stack
            System.out.println(event.getStackTrace());
        });
    }

}
