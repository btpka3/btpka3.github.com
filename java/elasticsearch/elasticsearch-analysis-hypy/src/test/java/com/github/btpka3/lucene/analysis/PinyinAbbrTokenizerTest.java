package com.github.btpka3.lucene.analysis;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.*;
import org.apache.lucene.util.Attribute;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;

public class PinyinAbbrTokenizerTest {

    @Test
    public void test1() throws Exception {
        StringReader input = new StringReader("abc湖南省123");
        PinyinAbbrTokenizer ts = new PinyinAbbrTokenizer(input);
        print(ts);
        ts.close();
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