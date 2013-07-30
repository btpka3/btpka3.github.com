
package me.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Adc {

    /**
     * 将行政区划代码的以为数组转换为树状结构。
     * from adc.js to adc-tree-pretty.js
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {

        JsonFactory jsonFactory = new JsonFactory();
        jsonFactory.configure(Feature.ALLOW_COMMENTS, true);
        ObjectMapper mapper = new ObjectMapper(jsonFactory);

        Map rootObj = new LinkedHashMap();

        ArrayList<Map> m = mapper.readValue(new File("D:\\zll\\git\\github\\btpka3.github.com\\js\\dojo\\my\\adc.js"), ArrayList.class);
        for (Map adcObj : m) {
            String code = (String) adcObj.get("adc");
            String name = (String) adcObj.get("area");
            if (code.endsWith("0000")) {
                Map lv1Obj = new LinkedHashMap();
                lv1Obj.put("code", code);
                lv1Obj.put("name", name);
                lv1Obj.put("children", new LinkedHashMap());
                rootObj.put(code, lv1Obj);
            } else if (code.endsWith("00")) {
                Map lv1Obj = (Map) rootObj.get(code.substring(0, 2) + "0000");
                Assert.notNull(lv1Obj, code + " 父元素未找到");
                Map lv2Obj = new LinkedHashMap();
                lv2Obj.put("code", code);
                lv2Obj.put("name", name);
                lv2Obj.put("children", new LinkedHashMap());
                ((Map) lv1Obj.get("children")).put(code, lv2Obj);
            } else {
                Map lv1Obj = (Map) rootObj.get(code.substring(0, 2) + "0000");
                Assert.notNull(lv1Obj, code + " 父元素未找到");

                Map lv3Obj = new LinkedHashMap();
                lv3Obj.put("code", code);
                lv3Obj.put("name", name);

                // 直辖市的三级行政单位跳过二级行政单位，而直接隶属于一级行政单位
                if (lv1Obj.get("name").equals("北京市")
                        || lv1Obj.get("name").equals("上海市")
                        || lv1Obj.get("name").equals("天津市")
                        || lv1Obj.get("name").equals("重庆市")) {
                    ((Map) lv1Obj.get("children")).put(code, lv3Obj);
                } else {
                    Map lv2Obj = (Map) ((Map) lv1Obj.get("children")).get(code.substring(0, 4) + "00");
                    Assert.notNull(lv2Obj, code + " 父元素未找到");
                    ((Map) lv1Obj.get("children")).put(code, lv3Obj);
                }
            }
        }
        System.out.println(m.size());
        System.out.println(m.get(0).getClass());
        mapper.writer().writeValue(new OutputStreamWriter(new FileOutputStream("D:\\zll\\git\\github\\btpka3.github.com\\js\\dojo\\my\\adc-tree.js"), "UTF-8"), rootObj);
        mapper.writer().withDefaultPrettyPrinter().writeValue(new OutputStreamWriter(new FileOutputStream("D:\\zll\\git\\github\\btpka3.github.com\\js\\dojo\\my\\adc-tree-pretty.js"), "UTF-8"), rootObj);
    }
}
