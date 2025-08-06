package me.test.checkstyle;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.ObjectUtils.Null;

/**
 * 测试禁止import inner class : org.apache.commons.lang3.ObjectUtils.Null ;
 */
public class DisableImportInnerClass {
    public static void main(String[] args) {
        Null nullObj = ObjectUtils.NULL;
        System.out.println(nullObj);
    }
}
