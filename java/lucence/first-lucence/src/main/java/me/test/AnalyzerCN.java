package me.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.Version;

/**
 * 查看分词结果。
 * http://www.iteye.com/news/9637
 *
 * @author zll
 *
 */
public class AnalyzerCN {

    static final String str = "Lucene是apache软件基金会4 jakarta项目组的一个子项目，"
            + "是一个开放源代码的全文检索引擎工具包，即它不是一个完整的全文检索引擎，"
            + "而是一个全文检索引擎的架构，提供了完整的查询引擎和索引引擎，"
            + "部分文本分析引擎（英文与德文两种西方语言）。"
            + "Apache LuceneTM is a high-performance, "
            + "full-featured text search engine library written entirely in Java. "
            + "It is a technology suitable for nearly any application "
            + "that requires full-text search, especially cross-platform.";

    public static void main(String[] args) {
        System.out.println("         : " + str);
        testWhitespaceAnalyzer();
        testSimpleAnalyzer();
        testStopAnalyzer();
        testStandardAnalyzer();
        testCJKAnalyzer();
        testSmartChineseAnalyzer();
    }

    /**
     * WhitespaceAnalyzer
     * 只以空格作为分词分隔符。不太实用。
     */
    private static void testWhitespaceAnalyzer() {
        List<String> result = new ArrayList<String>();
        Analyzer analyzer = new WhitespaceAnalyzer(Version.LUCENE_47);
        try {
            TokenStream tokenStream = analyzer.tokenStream("field", str);
            CharTermAttribute term = tokenStream.addAttribute(CharTermAttribute.class);
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                result.add(term.toString());
            }
            tokenStream.end();
            tokenStream.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        System.out.println("testWhitespaceAnalyzer : " + result);
    }

    /**
     * SimpleAnalyzer
     * 以非字母符来分割文本信息，并将语汇单元统一为小写形式，并去掉数字类型的字符。很明显不适用于中文环境。
     */
    private static void testSimpleAnalyzer() {
        List<String> result = new ArrayList<String>();
        Analyzer analyzer = new SimpleAnalyzer(Version.LUCENE_47);
        try {
            TokenStream tokenStream = analyzer.tokenStream("field", str);
            CharTermAttribute term = tokenStream.addAttribute(CharTermAttribute.class);
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                result.add(term.toString());
            }
            tokenStream.end();
            tokenStream.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        System.out.println("testSimpleAnalyzer : " + result);
    }

    /**
     * StopAnalyzer
     * 停顿词分析器会去除一些常有a,the,an等等，也可以自定义禁用词，不适用于中文环境。
     */
    private static void testStopAnalyzer() {
        List<String> result = new ArrayList<String>();
        Analyzer analyzer = new StopAnalyzer(Version.LUCENE_47);
        try {
            TokenStream tokenStream = analyzer.tokenStream("field", str);
            CharTermAttribute term = tokenStream.addAttribute(CharTermAttribute.class);
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                result.add(term.toString());
            }
            tokenStream.end();
            tokenStream.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        System.out.println("testStopAnalyzer : " + result);
    }

    /**
     * StandardAnalyzer
     * 标准分析器是Lucene内置的分析器,会将语汇单元转成小写形式，并去除停用词及标点符号，很明显也是不适合于中文环境
     */
    private static void testStandardAnalyzer() {
        List<String> result = new ArrayList<String>();
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_47);
        try {
            TokenStream tokenStream = analyzer.tokenStream("field", str);
            CharTermAttribute term = tokenStream.addAttribute(CharTermAttribute.class);
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                result.add(term.toString());
            }
            tokenStream.end();
            tokenStream.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        System.out.println("testStandardAnalyzer : " + result);
    }

    /**
     * 中日韩分析器，能对中，日，韩语言进行分析的分词器，但是对中文支持效果一般，一般不用
     */
    private static void testCJKAnalyzer() {
        List<String> result = new ArrayList<String>();
        Analyzer analyzer = new CJKAnalyzer(Version.LUCENE_47);
        try {
            TokenStream tokenStream = analyzer.tokenStream("field", str);
            CharTermAttribute term = tokenStream.addAttribute(CharTermAttribute.class);
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                result.add(term.toString());
            }
            tokenStream.end();
            tokenStream.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        System.out.println("testCJKAnalyzer : " + result);
    }

    /**
     * SmartChineseAnalyzer
     * 基于 Hidden Markov Model.基于
     * 对中文支持稍好，但扩展性差，扩展词库，禁用词库和同义词库等不好处理
     */
    private static void testSmartChineseAnalyzer() {

        // 自定义停用词
        String[] myStopWords = { "的", "了", "呢", "，", "0", "：", ",", "是", "流" };
        CharArraySet cas = new CharArraySet(Version.LUCENE_47, 0, true);
        for (int i = 0; i < myStopWords.length; i++) {
            cas.add(myStopWords[i]);
        }
        Iterator<Object> itor = SmartChineseAnalyzer.getDefaultStopSet().iterator();
        while (itor.hasNext()) {
            cas.add(itor.next());
        }

        List<String> result = new ArrayList<String>();
        Analyzer analyzer = new SmartChineseAnalyzer(Version.LUCENE_47, cas);
        try {
            TokenStream tokenStream = analyzer.tokenStream("field", str);
            CharTermAttribute term = tokenStream.addAttribute(CharTermAttribute.class);
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                result.add(term.toString());
            }
            tokenStream.end();
            tokenStream.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        System.out.println("testSmartChineseAnalyzer : " + result);
    }

}
