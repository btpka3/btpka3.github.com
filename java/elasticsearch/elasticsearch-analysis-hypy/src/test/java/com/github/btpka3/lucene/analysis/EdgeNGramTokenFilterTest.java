package com.github.btpka3.lucene.analysis;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.ngram.EdgeNGramTokenFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

public class EdgeNGramTokenFilterTest {

    @Test
    public void test() throws IOException {
        TokenStream input = new WhitespaceTokenizer(new StringReader("abcde"));
        EdgeNGramTokenFilter tokenizer = new EdgeNGramTokenFilter(input, 1, 3);
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
