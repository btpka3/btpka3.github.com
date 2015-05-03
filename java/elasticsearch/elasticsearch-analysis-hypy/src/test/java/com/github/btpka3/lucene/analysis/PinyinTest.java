package com.github.btpka3.lucene.analysis;

import java.util.Arrays;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

import org.junit.Test;

public class PinyinTest {
    @Test
    public void test1() throws Exception {
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        String[] strs = PinyinHelper.toHanyuPinyinStringArray('禅', defaultFormat);
        System.out.println(Arrays.asList(strs));
        strs = PinyinHelper.toHanyuPinyinStringArray('a', defaultFormat);
        System.out.println("--" + (strs == null ? "null" : Arrays.asList(strs)));
    }
    // 重阳节
    // 全拼： chong/zhong yang jie
    // 首字母 c/z y j
    // 按字拼音打头 ch/zh yang jie
}
