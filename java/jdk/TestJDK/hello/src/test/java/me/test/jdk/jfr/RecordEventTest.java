package me.test.jdk.jfr;


import jdk.jfr.FlightRecorder;
import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingFile;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * 生成JFR事件
 */
public class RecordEventTest {

    @Test
    @SneakyThrows
    public void testEvent() {
        Recording r = new Recording();
        // 启用事件, 并获取栈信息
        r.enable(MyEvent.class).withStackTrace();
        r.start();

        recordEvent();
        recordPeriodicEvent(r);

        r.stop();

        Path path = Files.createTempFile("recording", ".jfr");
        r.dump(path);


    }

    /**
     * 一次性事件
     */
    public void recordEvent() {
        MyEvent event1 = new MyEvent();
        event1.info = "event1 info";
        event1.commit();
    }

    /**
     * 周期性事件
     */
    public void recordPeriodicEvent(Recording r) {
        // enable MyEvent , 周期为1s
        r.enable(MyEvent.class).with("period", "1s");
        FlightRecorder.addPeriodicEvent(MyEvent.class, () -> {
            MyEvent event = new MyEvent();
            event.info = "info";
            event.commit();
        });
    }

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
