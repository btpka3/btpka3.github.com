package me.test.first.spring.boot.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author dangqian.zll
 * @date 2019-07-03
 */
public class Jackson8601Test {


    @Test
    public void test01() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        {

            // 支持 Java8 中的时间类型
            mapper.registerModule(new JavaTimeModule());

            // 输出JSON 时，如果字段值为null，则不输出字段名。
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

            // 输出JSON 时，时间不输出为时间戳，而是按照 ISO-8601 格式输出（含时区信息:但是按照默认服务器时区）
            // https://www.baeldung.com/jackson-serialize-dates
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            //mapper.setDateFormat(StdDateFormat.getISO8601Format(TimeZone.getDefault(), Locale.CHINESE));
            mapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));


            // 如果要解析的 JSON 中有未定义的 java 字段，则直接忽略，而不报错
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        }

        MyPojo p1 = new MyPojo();
        p1.setName("zhang3");
        p1.setJoinTime(new Date());

        System.out.println("p1 = " + p1);

        String jsonStr = mapper.writeValueAsString(p1);
        System.out.println(jsonStr);

        jsonStr = "{\"name\":\"zhang3\",\"joinTime\":\"2019-07-03T07:37:56.197Z\"}";

        MyPojo p2 = mapper.readValue(jsonStr, MyPojo.class);
        System.out.println("p2 = " + p2);

    }


    public static class MyPojo {
        private String name;
        private Date joinTime;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Date getJoinTime() {
            return joinTime;
        }

        public void setJoinTime(Date joinTime) {
            this.joinTime = joinTime;
        }

        @Override
        public String toString() {
            return "MyPojo{" +
                    "name='" + name + '\'' +
                    ", joinTime=" + joinTime +
                    '}';
        }
    }

    @Test(expected = Exception.class)
    public void x() throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss.SSSZ");
        Date d = df.parse("2019-07-03T07:37:56.197Z");
        System.out.println(d);
    }
}
