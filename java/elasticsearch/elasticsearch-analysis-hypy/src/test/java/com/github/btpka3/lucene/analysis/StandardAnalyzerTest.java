package com.github.btpka3.lucene.analysis;

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionLengthAttribute;
import org.apache.lucene.analysis.tokenattributes.TermToBytesRefAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.Attribute;
import org.junit.Assert;
import org.junit.Test;

public class StandardAnalyzerTest {
    @Test
    public void test01() throws IOException {
        Analyzer analyzer = new StandardAnalyzer();
        StringReader strReader = new StringReader(" I love abc中国123 ");
        TokenStream ts = analyzer.tokenStream("name", strReader);

//        Assert.assertTrue(ts.hasAttribute(CharTermAttribute.class));
//        CharTermAttribute termAtt = ts.getAttribute(CharTermAttribute.class);
//
//        Assert.assertTrue(ts.hasAttribute(OffsetAttribute.class));
//        OffsetAttribute offsetAtt = ts.getAttribute(OffsetAttribute.class);
//
//        Assert.assertTrue(ts.hasAttribute(TypeAttribute.class));
//        TypeAttribute typeAtt = ts.getAttribute(TypeAttribute.class);
//
//        Assert.assertTrue(ts.hasAttribute(PositionIncrementAttribute.class));
//        PositionIncrementAttribute posIncrAtt = ts.getAttribute(PositionIncrementAttribute.class);
//
//        System.out.printf("%12s ~ %10s : %15s : %18s :  %s  : %n",
//                "startOffset",
//                "endOffset",
//                "type",
//                "positionIncrement",
//                "term"
//                );
//        ts.reset();
//        while (ts.incrementToken()) {
//            System.out.printf("%12d ~ %10d : %15s : %18d : '%s' : %n",
//                    offsetAtt.startOffset(),
//                    offsetAtt.endOffset(),
//                    typeAtt.type(),
//                    posIncrAtt.getPositionIncrement(),
//                    termAtt.toString()
//                    );
//
//        }
        print(ts);
        analyzer.close();
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
}
