package me.test.jdk.jfr;


import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jdk.jfr.AnnotationElement;
import jdk.jfr.Event;
import jdk.jfr.EventFactory;
import jdk.jfr.FlightRecorder;
import jdk.jfr.Recording;
import jdk.jfr.ValueDescriptor;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingFile;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

/**
 * 生成JFR事件
 */
public class RecordEventTest {

    @Test
    @SneakyThrows
    public void testEvent() {
        Recording r = new Recording();
        // 启用事件：方式1
        Map<String, String> settings = new HashMap<>(4);
        settings.put("Foo#enabled", "true");
        settings.put("Foo#period", "5 s");
        r.setSettings(settings);

        // 启用事件：方式2
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
        MyEvent event = new MyEvent();

        if (event.isEnabled()) {
            event.begin();
            event.info = "event1 info";
            event.commit();
            if (event.shouldCommit()) {
                event.commit();
            }
        }
    }


    public void producer01() {
        //Producer myProducer = new Producer("Demo Producer", "A demo event producer.", http://www.example.com/demo/);
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
     * 动态事件字段。
     *
     * @param r
     */
    public void recordPeriodicEvent2(Recording r) {
        List<AnnotationElement> annotationElements = null;
        List<ValueDescriptor> fields = null;
        final EventFactory factory = EventFactory.create(annotationElements, fields);
        Event event = factory.newEvent();
        FlightRecorder.addPeriodicEvent(event.getClass(), () -> {
            Event foo = factory.newEvent();
            foo.set(0, "111");
            foo.commit();
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
