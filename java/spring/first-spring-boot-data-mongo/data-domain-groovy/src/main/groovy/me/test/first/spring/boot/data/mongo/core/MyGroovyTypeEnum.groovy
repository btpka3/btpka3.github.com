package me.test.first.spring.boot.data.mongo.core;


enum MyGroovyTypeEnum {

    ONE("ONE", "壹"), TWO("TWO", "贰"), THREE("THREE", "叁")

    MyGroovyTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    private String code;
    private String desp;
}
