package com.github.btpka3.first.spring.boot.data.elasticsearch.domain;

/**
 *
 */

public enum MyTypeEnum {
    ONE("ONE", "壹"), TWO("TWO", "贰"), THREE("THREE", "叁");

    MyTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    MyTypeEnum(String code) {
        this(code, null);
    }

    private String code;
    private String desp;
}
