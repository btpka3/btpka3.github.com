package me.test.first.spring.boot.data.mongo.core

/**
 * Created by zll on 06/03/2017.
 */
enum MyTypeEnum {


    ONE("ONE", "壹"),
    TWO("TWO", "贰"),
    THREE("THREE", "叁"),


    private String code
    private String desp

    MyTypeEnum(String code, String desp = null) {
        this.code = code
        this.desp = desp
    }


}