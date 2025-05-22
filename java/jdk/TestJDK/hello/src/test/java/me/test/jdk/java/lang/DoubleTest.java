package me.test.jdk.java.lang;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.MapUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 该示例旨在说明使用浮点数进行计算时可能会引起的精度问题。
 * <p>
 * 参考：《 <a
 * href="http://en.wikipedia.org/wiki/Double-precision_floating-point_format"
 * >Double-precision floating-point format</a>》、《<a
 * href="http://en.wikipedia.org/wiki/IEEE_floating_point">IEEE floating
 * point</a>》。
 * </p>
 */
public class DoubleTest {

    public static void main(String[] args) {

        showError();
        fixError();

        showError2();
    }

    public static void showError2() {
        double d = 2.55;
        System.out.println(d * 100);
    }

    /**
     * 使用不当而引发错误的例子。实际输出结果：
     * <p>
     * <code>
     * 0.1
     * 0.2
     * 0.30000000000000004
     * 0.4
     * 0.5
     * 0.6
     * 0.7
     * 0.7999999999999999
     * 0.8999999999999999
     * 0.9999999999999999
     * </code>
     * </p>
     */
    public static void showError() {
        System.out.println("================ This is an error showing sample.");
        double val = 0;
        for (int i = 0; i < 10; i++) {
            val += 0.1;
            System.out.println(val);
        }
        System.out.println();
    }

    /**
     * 使用BigDecimal的正确的例子。
     * <p>
     * <code>
     * 0.1
     * 0.2
     * 0.3
     * 0.4
     * 0.5
     * 0.6
     * 0.7
     * 0.7
     * 0.8
     * 0.9
     * </code>
     */
    public static void fixError() {
        System.out.println("================ This is an error fixed sample.");
        // Do NOT use this constructor, See the javadoc.
        // ERROR example : System.out.println(new BigDecimal(2.55));
        //BigDecimal b = new BigDecimal(0.1); 
        BigDecimal b = new BigDecimal("0.1");
        //b = b.setScale(1, RoundingMode.HALF_UP);
        BigDecimal val = BigDecimal.ZERO;
        for (int i = 0; i < 10; i++) {
            val = val.add(b);
            System.out.println(val.doubleValue());
        }
        System.out.println();
    }

    @Test
    public void testDouble() {

        Long l = 1746705327990L;
        Double d = (double)l;
        Assertions.assertEquals("1.74670532799E12", d.toString());

        Map<String, Object> map = new HashMap<>(4);
        map.put("a", d);
        String jsonStr = JSON.toJSONString(map);
        Assertions.assertEquals("{\"a\":1.74670532799E12}", jsonStr);
        JSONObject jsonObj = JSON.parseObject(jsonStr);
        Assertions.assertEquals("1.74670532799E12", jsonObj.getString("a"));
        Assertions.assertEquals("1.74670532799E12", MapUtils.getString(jsonObj, "a"));

    }
}
