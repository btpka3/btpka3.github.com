package me.test.first.spring.boot.test.json.c;

import com.alibaba.fastjson.JSONObject;
import me.test.first.spring.boot.test.json.b.A10;

import java.util.Map;

/**
 * @author dangqian.zll
 * @date 2024/2/26
 */
// @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)                                                        // {"name":"zhang3","age":38}
// @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)                                                       // {"@class":"me.test.first.spring.boot.test.json.c.A11","name":"zhang3","age":38}
// @JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS)                                               // {"@c":".A11","name":"zhang3","age":38}
// @JsonTypeInfo(use = JsonTypeInfo.Id.NAME)                                                        // {"@type":"A11","name":"zhang3","age":38}
// @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,           include = JsonTypeInfo.As.WRAPPER_ARRAY)    // ["me.test.first.spring.boot.test.json.c.A11",{"name":"zhang3","age":38}]
// @JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS,   include = JsonTypeInfo.As.WRAPPER_ARRAY)    // [".A11",{"name":"zhang3","age":38}]
// @JsonTypeInfo(use = JsonTypeInfo.Id.NAME,            include = JsonTypeInfo.As.WRAPPER_ARRAY)    // ["A11",{"name":"zhang3","age":38}]
// @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,           include = JsonTypeInfo.As.WRAPPER_OBJECT)   // {"me.test.first.spring.boot.test.json.c.A11":{"name":"zhang3","age":38}}
// @JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS,   include = JsonTypeInfo.As.WRAPPER_OBJECT)   // {".A11":{"name":"zhang3","age":38}}
// @JsonTypeInfo(use = JsonTypeInfo.Id.NAME,            include = JsonTypeInfo.As.WRAPPER_OBJECT)   // {"A11":{"name":"zhang3","age":38}}

public class A11 extends A10 {

    public A11(){}

    private Integer age;
    private JSONObject o;
    private Map<String, Object> o2;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public JSONObject getO() {
        return o;
    }

    public void setO(JSONObject o) {
        this.o = o;
    }

    public Map<String, Object> getO2() {
        return o2;
    }

    public void setO2(Map<String, Object> o2) {
        this.o2 = o2;
    }
}
