package me.test.first.spring.boot.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dangqian.zll
 * @date 2019-07-22
 * @see <a href="https://cassiomolin.com/2016/09/17/converting-pojo-map-vice-versa-with-jackson/">Converting POJO to Map and vice versa with Jackson</a>
 */
public class JacksonEnumTest {

    @Test
    public void test02() {
        String a1 = "aaa";
        String a2 = JSONObject.toJSONString(a1);
        Assertions.assertNotEquals(a1, a2);
    }

    @Test
    public void test01() throws JsonProcessingException {
        Map map = new HashMap<>(4);
        map.put("a", AaaEnum.A01);
        map.put("b", BbbEnum.B01);
        map.put("c", CccEnum.C01);

        {

            System.out.println(JSON.toJSONString(map, true));
            String a1 = "aaaa";
            String a2 = JSONObject.toJSONString(a1);
            System.out.println(JSON.toJSONString("xxxxxxxxx", true));
        }

        {
            ObjectMapper m = new ObjectMapper();
            System.out.println(m.writeValueAsString(map));
        }
    }


    public enum AaaEnum {
        A01,
        A02
    }

    public enum BbbEnum {
        B01("b01"),
        B02("b02"),
        ;
        private String code;

        BbbEnum(String code) {
            this.code = code;
        }
    }

    public enum CccEnum {
        C01("c01"),
        C02("c02"),
        ;
        private String _name;

        CccEnum(String code) {
            this._name = code;
        }

        public String getCode() {
            return _name;
        }

        @Override
        public String toString() {
            return this._name;
        }
    }

}
