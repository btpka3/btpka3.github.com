package me.test.checkstyle;

import org.apache.commons.lang3.ObjectUtils;

/**
 * 测试禁止使用 inner class : org.apache.commons.lang3.ObjectUtils.Nul;
 */
public class DisableFullQualifyInnerClassUsage2 {
    public static void main(String[] args) {
        ObjectUtils.Null nullObj = ObjectUtils.NULL;
        System.out.println(nullObj);
    }
}
