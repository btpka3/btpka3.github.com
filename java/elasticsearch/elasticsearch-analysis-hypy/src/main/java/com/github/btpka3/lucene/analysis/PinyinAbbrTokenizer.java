package com.github.btpka3.lucene.analysis;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.synonym.SynonymFilter;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.analysis.tokenattributes.*;
import org.apache.lucene.util.CharsRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

public class PinyinAbbrTokenizer extends Tokenizer {
    private Logger logger = LoggerFactory.getLogger(PinyinAbbrTokenizer.class);

    private SynonymMap pinyinSynonymMap;
    private HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
    private SynonymFilter synonymFilter;

    private void setupSynonymMap()
            throws IOException, BadHanyuPinyinOutputFormatCombination {

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

    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private final PositionIncrementAttribute posIncrAtt = addAttribute(PositionIncrementAttribute.class);
    private final PositionLengthAttribute posLenAtt = addAttribute(PositionLengthAttribute.class);
    private final TypeAttribute typeAtt = addAttribute(TypeAttribute.class);
    private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);

    private static final int DEFAULT_BUFFER_SIZE = 256;

    private HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
    private static Pattern pattern = Pattern.compile("^[\\u4e00-\\u9fa5]$");

    // public PinyinAbbrTokenizer(AttributeFactory factory, Reader input) {
    // this(factory, input, DEFAULT_BUFFER_SIZE);
    // }

    public PinyinAbbrTokenizer(Reader input) {
        super(input);
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

        try {
            setupSynonymMap();

            Analyzer analyzer = new StandardAnalyzer();
            TokenStream ts0 = analyzer.tokenStream("name", input);
            synonymFilter = new SynonymFilter(ts0, pinyinSynonymMap, true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    // public PinyinAbbrTokenizer(AttributeFactory factory, Reader input, int bufferSize) {
    // super(factory, input);
    // termAtt.resizeBuffer(bufferSize);
    // format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
    // format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    // format.setVCharType(HanyuPinyinVCharType.WITH_V);
    // }

    // public PinyinAbbrTokenizer(Reader input, int bufferSize) {
    // super(input);
    // }

    // @Override
    // public boolean isTokenChar(int c) {
    // if (Character.isLetterOrDigit(c)) {
    // return true;
    // }
    //
    // Matcher matcher = pattern.matcher(String.valueOf(c));
    // return matcher.matches();
    // }
    //
    // @Override
    // protected int normalize(int c) {
    // try {
    // String[] strs = PinyinHelper.toHanyuPinyinStringArray((char) c, format);
    // if (strs != null) {
    // termAtt.append(strs[0]);
    // return strs[0].codePointAt(0);
    // }
    // } catch (BadHanyuPinyinOutputFormatCombination e) {
    // logger.debug(e.getMessage(), e);
    // }
    // return c;
    // }

    @Override
    public final boolean incrementToken() throws IOException {

        boolean hasMore = synonymFilter.incrementToken();

        CharTermAttribute termAtt0 = synonymFilter.getAttribute(CharTermAttribute.class);

        if (termAtt.length() < termAtt0.length()) {
            termAtt.resizeBuffer(termAtt0.length() * 2);
        }
        termAtt.setEmpty();
        termAtt.copyBuffer(termAtt0.buffer(), 0, termAtt0.length());

        PositionIncrementAttribute posIncrAtt0 = synonymFilter.getAttribute(PositionIncrementAttribute.class);
        posIncrAtt.setPositionIncrement(posIncrAtt0.getPositionIncrement());

        PositionLengthAttribute posLenAtt0 = synonymFilter.getAttribute(PositionLengthAttribute.class);
        posLenAtt.setPositionLength(posLenAtt0.getPositionLength());

        TypeAttribute typeAtt0 = synonymFilter.getAttribute(TypeAttribute.class);
        typeAtt.setType(typeAtt0.type());

        OffsetAttribute offsetAtt0 = synonymFilter.getAttribute(OffsetAttribute.class);
        offsetAtt.setOffset(offsetAtt0.startOffset(), offsetAtt0.endOffset());

        return hasMore;
    }

    @Override
    public void close() throws IOException {
        // TODO Auto-generated method stub
        synonymFilter.close();
    }

    @Override
    public void reset() throws IOException {
        // TODO Auto-generated method stub
        synonymFilter.reset();
    }
}
