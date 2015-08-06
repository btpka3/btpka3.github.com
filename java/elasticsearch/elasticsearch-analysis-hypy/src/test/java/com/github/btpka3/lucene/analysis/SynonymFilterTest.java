package com.github.btpka3.lucene.analysis;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.synonym.SynonymFilter;
import org.apache.lucene.analysis.synonym.SynonymFilterFactory;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.analysis.tokenattributes.*;
import org.apache.lucene.analysis.util.FilesystemResourceLoader;
import org.apache.lucene.util.Attribute;
import org.apache.lucene.util.CharsRef;
import org.apache.lucene.util.Version;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

// http://www.hankcs.com/program/java/lucene-synonymfilterfactory.html
public class SynonymFilterTest {
    HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();

    @Before
    public void befor() {
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
    }

    @Test
    public void test1() throws Exception {

        StringReader strReader = new StringReader(" I love 中国 ");
        Analyzer analyzer = new StandardAnalyzer();
        TokenStream ts0 = analyzer.tokenStream("name", strReader);

        SynonymMap.Builder builder = new SynonymMap.Builder(true);
        addEq(builder, new String[]{"love", "喜欢", "爱"});
        addEq(builder, new String[]{"i", "我", "偶"});
        addTo(builder,
                new String[]{"中"},
                new String[]{"z", "zh", "zho", "zhon", "zhong"});
        SynonymMap synonymMap = builder.build();

        SynonymFilter ts = new SynonymFilter(ts0, synonymMap, true);
        print(ts);

        analyzer.close();
    }

    private void addEq(SynonymMap.Builder builder, String[] eqSynoyms) {
        for (String input : eqSynoyms) {
            for (String output : eqSynoyms) {
                builder.add(new CharsRef(input), new CharsRef(output), false);
            }
        }
    }

    private void addTo(SynonymMap.Builder builder, String[] from, String[] to) {
        for (String input : from) {
            for (String output : to) {
                builder.add(new CharsRef(input), new CharsRef(output), false);
            }
        }
    }

    private void print(TokenStream ts) throws IOException {
        Iterator<Class<? extends Attribute>> it = ts.getAttributeClassesIterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        CharTermAttribute termAtt = ts.getAttribute(CharTermAttribute.class);
        PositionIncrementAttribute posIncrAtt = ts.getAttribute(PositionIncrementAttribute.class);
        PositionLengthAttribute posLenAtt = ts.getAttribute(PositionLengthAttribute.class);
        TypeAttribute typeAtt = ts.getAttribute(TypeAttribute.class);
        OffsetAttribute offsetAtt = ts.getAttribute(OffsetAttribute.class);
        TermToBytesRefAttribute byteRefAtt = ts.getAttribute(TermToBytesRefAttribute.class);

        ts.reset();
        while (ts.incrementToken()) {
            System.out.printf("%3d ~ %3d : %15s : %3d : %3d : '%s' - '%s' : %n",
                    offsetAtt.startOffset(),
                    offsetAtt.endOffset(),
                    typeAtt.type(),
                    posIncrAtt.getPositionIncrement(),
                    posLenAtt.getPositionLength(),
                    new String(byteRefAtt.getBytesRef().bytes),
                    termAtt.toString()
            );

        }
    }

    @Test
    public void test2() throws Exception {
        StringReader strReader = new StringReader(" I love 中国 ");
        Analyzer analyzer = new StandardAnalyzer();
        TokenStream ts0 = analyzer.tokenStream("name", strReader);

        Map<String, String> filterArgs = new HashMap<String, String>();
        filterArgs.put("luceneMatchVersion", Version.LATEST.toString());
        filterArgs.put("synonyms", "me/test/SynonymFilterTest.txt");

        filterArgs.put("expand", "true");
        SynonymFilterFactory factory = new SynonymFilterFactory(filterArgs);
        factory.inform(new FilesystemResourceLoader());

        TokenStream ts = factory.create(ts0);
        print(ts);
        analyzer.close();
    }

    @Test
    public void test3() throws Exception {

        StringReader strReader = new StringReader(" zhu zhi 中");
        Analyzer analyzer = new StandardAnalyzer();
        TokenStream ts0 = analyzer.tokenStream("name", strReader);

        SynonymMap.Builder builder = new SynonymMap.Builder(true);
        addTo(builder,
                new String[]{"zhu"},
                new String[]{"z", "zh", "zhu"});
        addTo(builder,
                new String[]{"zhi"},
                new String[]{"z", "zh", "zhi"});
        addTo(builder,
                new String[]{"中"},
                new String[]{"中", "z", "zh", "zho", "zhon", "zhong"});
        SynonymMap synonymMap = builder.build();

        SynonymFilter ts = new SynonymFilter(ts0, synonymMap, true);
        print(ts);

        analyzer.close();
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

    private String[] pinyinSynonymArr(char c) throws BadHanyuPinyinOutputFormatCombination {
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

    // 测试一次性构造所有汉字的拼音缩写词
    @Test
    public void test4() throws Exception {

        char[][] chineseChars = {
                // {from, to}
                {'\u4e00', '\u9fa5'}
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
                        new String[]{Character.toString(c)},
                        flattenPinyinArr(pinyinArr));
            }
        }

        StringReader strReader = new StringReader("abc重阳节123");
        Analyzer analyzer = new StandardAnalyzer();
        TokenStream ts0 = analyzer.tokenStream("name", strReader);
        SynonymMap synonymMap = builder.build();
        SynonymFilter ts = new SynonymFilter(ts0, synonymMap, true);
        print(ts0);

        analyzer.close();
    }
}
