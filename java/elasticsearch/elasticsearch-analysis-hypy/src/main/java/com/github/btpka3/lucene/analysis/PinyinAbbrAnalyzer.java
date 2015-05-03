package com.github.btpka3.lucene.analysis;

import java.io.IOException;
import java.io.Reader;
import java.util.Set;
import java.util.TreeSet;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.synonym.SynonymFilter;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.util.CharsRef;

public class PinyinAbbrAnalyzer extends Analyzer {

    
    private SynonymMap pinyinSynonymMap;
    private HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
    private SynonymFilter synonymFilter;
    
    

    private void setupSynonymMap()
            throws IOException, BadHanyuPinyinOutputFormatCombination {

        char[][] chineseChars = {
                // {from, to}
                { '\u4e00', '\u9fa5' }
        };

        SynonymMap.Builder builder = new SynonymMap.Builder(true);

        for (int i = 0; i < chineseChars.length; i++) {
            char[] charRange = chineseChars[i];
            for (char c = charRange[0]; c <= charRange[1]; c++) {
                String[] pinyinArr = pinyinSynonymArr(c);
                if (pinyinArr == null) {
                    continue;
                }
                addTo(builder,
                        new String[] { Character.toString(c) },
                        flattenPinyinArr(pinyinArr));
            }
        }
        pinyinSynonymMap = builder.build();
    }

    private String[] pinyinSynonymArr(char c)
            throws BadHanyuPinyinOutputFormatCombination {
        String[] pinyinArr = PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat);
        if (pinyinArr == null) {
            return null;
        }

        String[] flattenPinyinArr = flattenPinyinArr(pinyinArr);
        String[] withOrgCharPinyinArr = new String[flattenPinyinArr.length + 1];
        withOrgCharPinyinArr[0] = Character.toString(c);
        System.arraycopy(flattenPinyinArr, 0, withOrgCharPinyinArr, 1, flattenPinyinArr.length);
        return withOrgCharPinyinArr;
    }

    // 称 {chèn, chēng } -> {c,ch,che,cheng}
    // 重 {zhòng,chóng} -> {z,zh,zho,zhon,zhong, c,ch,cho,chon,chong}
    private String[] flattenPinyinArr(String[] pinyinArr) {
        if (pinyinArr == null) {
            return null;
        }
        Set<String> pinyinAbbrSet = new TreeSet<String>();
        for (int i = 0; i < pinyinArr.length; i++) {
            String pinyin = pinyinArr[i];
            for (int j = 1; j <= pinyin.length(); j++) {
                pinyinAbbrSet.add(pinyin.substring(0, 0 + j));
            }
        }

        return pinyinAbbrSet.toArray(new String[0]);
    }

    private void addTo(SynonymMap.Builder builder, String[] from, String[] to) {
        for (String input : from) {
            for (String output : to) {
                builder.add(new CharsRef(input), new CharsRef(output), false);
            }
        }
    }

    public static boolean isChineseChar(char c) {
        return '\u4e00' <= c && c <= '\u9fa5';
    }

    public static boolean containsChineseChar(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (isChineseChar(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
        
        
        return new TokenStreamComponents(null);
    }

}
