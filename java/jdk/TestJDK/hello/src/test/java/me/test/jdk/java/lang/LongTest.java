package me.test.jdk.java.lang;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2020/11/2
 */
public class LongTest {

    @Test
    public void x() {
        Long l = 4159777755L;
        System.out.println(String.valueOf(l));
        System.out.println(l.toString());
    }

    @Test
    public void y() {
        Number l = 4159777755.0;
        System.out.println(String.valueOf(l));
        System.out.println(l.toString());
    }

    @Test
    public void z() {
        Long l = 1746705327990L;

        // "1.74670532799E+12"
        Assertions.assertEquals("1746705327990", l.toString());
        Assertions.assertEquals("1746705327990", Long.toString(l));

        {
            Map<String, Object> map = new HashMap<>(4);
            map.put("a", l);
            String jsonStr = JSON.toJSONString(map);
            System.out.println("jsonStr=" + jsonStr);
            JSONObject jsonObj = JSON.parseObject(jsonStr);
            System.out.println(jsonObj.getString("a"));
        }
        {
            BigDecimal n = new BigDecimal(l);
            Map<String, Object> map = new HashMap<>(4);
            map.put("a", n);
            String jsonStr = JSON.toJSONString(map);
            System.out.println("jsonStr=" + jsonStr);
            System.out.println("n.toEngineeringString=" + n.toEngineeringString());
        }
    }

    @Test
    public void toStringWithE() {
        Long l = 1746705327990L;

        // 方式1：通过 printf + double 的方式
        // E: 表示以科学计数法显示，保留 2 位小数，且指数部分始终显示 E+
        System.out.printf("%.2E%n", (double) l);
        // G: 数值的大小自动选择普通或科学计数法
        System.out.printf("%.2G%n", (double) l);
        System.out.println(String.format("%.2E", (double) l));

        // 方式2：通过 DecimalFormat#format
        DecimalFormat df = new DecimalFormat("0.00E0");
        System.out.println("DecimalFormat:" + df.format(l));


        System.out.println("n.toString()            =" + new BigDecimal(l).toString());
        System.out.println("n.toEngineeringString() =" + new BigDecimal(l).toEngineeringString());
    }
}
