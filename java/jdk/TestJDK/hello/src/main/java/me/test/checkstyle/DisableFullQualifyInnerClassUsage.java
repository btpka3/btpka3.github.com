package me.test.checkstyle;

/**
 * 测试禁止使用 inner class : org.apache.commons.lang3.ObjectUtils.Nul;
 */
public class DisableFullQualifyInnerClassUsage {
    public static void main(String[] args) {
        org.apache.commons.lang3.ObjectUtils.Null nullObj = org.apache.commons.lang3.ObjectUtils.NULL;
        System.out.println(nullObj);
    }
}
