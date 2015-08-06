package com.github.btpka3.lucene.analysis;

import org.apache.lucene.analysis.ngram.EdgeNGramTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Assert;
import org.junit.Test;

import java.io.StringReader;

// http://svn.apache.org/repos/asf/lucene/dev/tags/lucene_solr_4_10_3/lucene/analysis/common/src/test/org/apache/lucene/analysis/ngram/
public class EdgeNGramTokenizerTest {

    @Test
    public void test1() throws Exception {
        StringReader input = new StringReader("abcde");
        EdgeNGramTokenizer tokenizer = new EdgeNGramTokenizer(input, 1, 3);
        CharTermAttribute termAtt = tokenizer.getAttribute(CharTermAttribute.class);
        tokenizer.reset();
        Assert.assertTrue(tokenizer.incrementToken());
        Assert.assertEquals("a", termAtt.toString());
        Assert.assertTrue(tokenizer.incrementToken());
        Assert.assertEquals("ab", termAtt.toString());
        Assert.assertTrue(tokenizer.incrementToken());
        Assert.assertEquals("abc", termAtt.toString());
        Assert.assertFalse(tokenizer.incrementToken());
        tokenizer.close();
    }
}