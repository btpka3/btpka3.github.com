package me.test.jdk.jfr;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordedStackTrace;
import jdk.jfr.consumer.RecordedThread;
import jdk.jfr.consumer.RecordingFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

/**
 * 消费事件
 *
 * @see <a href="https://github.com/openjdk/jdk/blob/58911ccc2c48b4b5dd2ebc9d2a44dcf3737eca50/src/jdk.jfr/share/classes/jdk/jfr/internal/tool/Scrub.java#L43">jdk.jfr.internal.tool.Scrub</a>
 */
public class ConsumeEventTest {


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

    @SneakyThrows
    @Test
    public void tryToFindCpu() {
        Path path = Path.of(System.getProperty("user.home"), "Documents", "412.jfr");
        List<RecordedEvent> events = RecordingFile.readAllEvents(path);
        Instant startTime = ZonedDateTime.of(2024, 04, 12, 14, 59, 49, 0, ZoneId.systemDefault())
                .toInstant();
        Instant endTime = ZonedDateTime.of(2024, 04, 12, 14, 59, 52, 0, ZoneId.systemDefault())
                .toInstant();
        events.stream()
                .limit(1)
                .forEach(event -> {
                    System.out.println("event=" + JSON.toJSONString(event) + ",  " + event.getStartTime() + "," + event.getStartTime().isAfter(startTime));
                });


        try (FileWriter w = new FileWriter(new File("/tmp/tryToFindCpu.json"), StandardCharsets.UTF_8)) {

            Map<String, AtomicInteger> count = new HashMap<>(32);
            events.stream()
                    .filter(event -> event.getStartTime().isAfter(startTime)
                            && event.getStartTime().isBefore(endTime)
                    )
                    .map(MyJfrUtils::toJsonEvent)
                    .peek(event -> count.computeIfAbsent(event.getName(), k -> new AtomicInteger(0))
                            .incrementAndGet())
                    .forEach(event -> {
                        try {
                            IOUtils.write(JSON.toJSONString(event), w);
                            IOUtils.write("\n", w);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
            System.out.println("---------------- count:");
            System.out.println(JSON.toJSONString(count, true));
        }
    }


    @SneakyThrows
    @Test
    public void testScrub() {
        Path inputFile = Path.of(System.getProperty("user.home"), "Documents", "412.jfr");
        Path outputFile = Path.of("/tmp", "myScrub.jfr");
        Instant startTime = ZonedDateTime.of(2024, 04, 12, 14, 59, 49, 0, ZoneId.systemDefault())
                .toInstant();
        Instant endTime = ZonedDateTime.of(2024, 04, 12, 14, 59, 52, 0, ZoneId.systemDefault())
                .toInstant();
        MyJfrUtils.scrub(inputFile, outputFile, startTime, endTime);
        System.out.println("-----done.");
        /*
        jfr summary /tmp/myScrub.jfr
 Event Type                              Count  Size (bytes)
=============================================================
 jdk.ThreadPark                           2289        104932
 jdk.ThreadSleep                          1459         35164
 jdk.ObjectAllocationInNewTLAB            1400         34983
 jdk.Checkpoint                           1075        799781
 jdk.JavaMonitorWait                       312         11195
 jdk.ThreadCPULoad                         291          6988
 jdk.ObjectAllocationOutsideTLAB           245          5677
 jdk.NativeMethodSample                    145          2899
 jdk.ExecutionSample                       129          2690
 jdk.SafepointBegin                         11           286
 jdk.ExecuteVMOperation                     11           298
 jdk.Metadata                                5        439255
 jdk.ExceptionStatistics                     3            69
 jdk.BiasedLockRevocation                    3            94
 jdk.BiasedLockSelfRevocation                3            74
 jdk.CPULoad                                 3            78
 jdk.JavaThreadStatistics                    3            69
 jdk.ClassLoadingStatistics                  3            60
 jdk.CompilerStatistics                      3           123
 jdk.ThreadStart                             2            44
 jdk.NetworkUtilization                      2            44
 jdk.BiasedLockClassRevocation               1            31
 jdk.Compilation                             1            38
 jdk.Deoptimization                          1            38
 jdk.ThreadContextSwitchRate                 1            18
 jdk.SocketWrite                             1            41
 jdk.SocketRead                              1            53

        # 查看哪个线程 cpu user 利用率高(top 10)
        jfr print --json --events jdk.ThreadCPULoad  /tmp/myScrub.jfr | jq '.recording.events|sort_by(.values.user)|reverse|[limit(10;.[])]'
        # 查看哪个线程 cpu system 利用率高(top 10)
        jfr print --json --events jdk.ThreadCPULoad  /tmp/myScrub.jfr | jq '.recording.events|sort_by(.values.system)|reverse|[limit(10;.[])]'
        jfr print --events jdk.CompilerStatistics /tmp/myScrub.jfr
        jfr print --events jdk.Compilation /tmp/myScrub.jfr
        */
    }

    @SneakyThrows
    @Test
    public void testPrint() {
        Path inputFile = Path.of(System.getProperty("user.home"), "Documents", "412.jfr");
        Instant startTime = ZonedDateTime.of(2024, 04, 12, 14, 59, 49, 0, ZoneId.systemDefault())
                .toInstant();
        Instant endTime = ZonedDateTime.of(2024, 04, 12, 14, 59, 52, 0, ZoneId.systemDefault())
                .toInstant();
        List<RecordedEvent> events = RecordingFile.readAllEvents(inputFile);
        System.out.println("-----done.");
        events.stream()
                .filter(event -> Objects.equals("jdk.CPULoad", MyJfrUtils.getName(event)))
                .filter(event -> event.getStartTime().isAfter(startTime)
                        && event.getStartTime().isBefore(endTime))
                //.map(MyJfrUtils::toJsonEvent)
                .forEach(event -> {
                    System.out.println("=================== event:" + MyJfrUtils.getName(event));
                    System.out.println("startTime=" + MyJfrUtils.getStartTime(event));
                    System.out.println("duration=" + MyJfrUtils.getDuration(event));
                    System.out.println("endTime=" + MyJfrUtils.getEndTime(event));
                    System.out.println("stackTrace=" + MyJfrUtils.getStackTrace(event));
                    System.out.println("json=" + JSON.toJSONString(event,
                            SerializerFeature.WriteNonStringKeyAsString,
                            SerializerFeature.DisableCircularReferenceDetect
                    ));

                });
    }


    @Data
    @Builder(toBuilder = true)
    @AllArgsConstructor
    @NoArgsConstructor
    protected static class JsonEvent {
        String name;
        String startTime;
        /**
         * 纳秒
         */
        Integer duration;
        String endTime;

        String stackTrace;
        String threadName;
    }


    public static class MyJfrUtils {

        private static final ZoneOffset GMT8 = ZoneOffset.ofHours(8);

        /**
         * 将给定时间范围内的JFR事件另存到别的文件里。
         *
         * @param inputFile
         * @param outputFile
         * @param startTime
         * @param endTime
         */
        @SneakyThrows
        public static void scrub(Path inputFile, Path outputFile, Instant startTime, Instant endTime) {
            RecordingFile rf = new RecordingFile(inputFile);
            Predicate<RecordedEvent> filter = event -> event.getStartTime().isAfter(startTime)
                    && event.getStartTime().isBefore(endTime);
            rf.write(outputFile, filter);
        }

        public static JsonEvent toJsonEvent(RecordedEvent e) {
            return JsonEvent.builder()
                    .name(MyJfrUtils.getName(e))
                    .startTime(MyJfrUtils.getStartTime(e))
                    .duration(MyJfrUtils.getDuration(e))
                    .endTime(MyJfrUtils.getEndTime(e))
                    .stackTrace(MyJfrUtils.getStackTrace(e))
                    .threadName(MyJfrUtils.getThreadName(e))
                    .build();
        }

        public static String getName(RecordedEvent e) {
            return Optional.ofNullable(e)
                    .map(event -> event.getEventType().getName())
                    .orElse(null);
        }

        public static String getStackTrace(RecordedEvent e) {
            return Optional.ofNullable(e)
                    .map(RecordedEvent::getStackTrace)
                    .map(RecordedStackTrace::toString)
                    .orElse(null);
        }

        public static String getStartTime(RecordedEvent e) {
            return Optional.ofNullable(e)
                    .map(RecordedEvent::getStartTime)
                    .map(instant -> ZonedDateTime.ofInstant(instant, GMT8).format(DateTimeFormatter.ISO_DATE_TIME))
                    .orElse(null);
        }

        public static Integer getDuration(RecordedEvent e) {
            return Optional.ofNullable(e)
                    .map(RecordedEvent::getDuration)
                    .map(duration -> duration.getNano())
                    .orElse(null);
        }

        public static String getEndTime(RecordedEvent e) {
            return Optional.ofNullable(e)
                    .map(RecordedEvent::getEndTime)
                    .map(instant -> ZonedDateTime.ofInstant(instant, GMT8).format(DateTimeFormatter.ISO_DATE_TIME))
                    .orElse(null);
        }

        public static String getThreadName(RecordedEvent e) {
            return Optional.ofNullable(e)
                    .map(RecordedEvent::getThread)
                    .map(RecordedThread::getJavaName)
                    .orElse(null);
        }

//        public static String getThreadName1(RecordedEvent e) {
//            return Optional.ofNullable(e)
//                    .map(RecordedEvent::getFields)
//                    .map(RecordedThread::getJavaName)
//                    .orElse(null);
//        }

//        public static String getThreadName1(ValueDescriptor vd) {
//            vd.getFields().get(0).get
//            return Optional.ofNullable(vd)
//                    .map(RecordedEvent::getFields)
//                    .map(RecordedThread::getJavaName)
//                    .orElse(null);
//        }


    }

}
