package me.test.org.apache.commons.text;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.text.StringTokenizer;
import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/4/30
 */
public class StringTokenizerTest {

    public void x() {
        StringTokenizer st = StringTokenizer.getCSVInstance();
        while (st.hasNext()) {}
    }

    @Test
    public void test01() {
        // 格式: k1=v1,k2=v2,k3=v3
        // 引用字符串: '`'

        StringTokenizer kvPairSt = new StringTokenizer();
        kvPairSt.setDelimiterMatcher(StringMatcherFactory.INSTANCE.charSetMatcher(',', '='));
        //        kvPairSt.setDelimiterMatcher(StringMatcherFactory.INSTANCE.commaMatcher());
        kvPairSt.setQuoteMatcher(StringMatcherFactory.INSTANCE.charMatcher('`'));
        kvPairSt.setIgnoredMatcher(StringMatcherFactory.INSTANCE.noneMatcher());
        kvPairSt.setTrimmerMatcher(StringMatcherFactory.INSTANCE.noneMatcher());
        kvPairSt.setEmptyTokenAsNull(false);
        kvPairSt.setIgnoreEmptyTokens(false);

        String str = "aa=aaaa,"
                + "`bb`=`bbbb`,"
                + "`c=c`=`cc=cc`,"
                + "`d,d`=`dd,dd`,"
                + "`e``e`=`ee``ee`,"
                + "f`f=ff`ff,"
                + "g``g=gg``gg,"
                + "hh=";

        kvPairSt.reset(str);

        AtomicInteger count = new AtomicInteger(0);
        while (kvPairSt.hasNext()) {
            count.addAndGet(1);
            int tokenPos = kvPairSt.nextIndex();
            String token = kvPairSt.next();
            System.out.println(" " + count + " :" + token + "     : tokenPos =" + tokenPos + ", charAtTokenPos="
                    + str.charAt(tokenPos));
        }

        List<String> kvPairList = kvPairSt.getTokenList();

        Map<String, String> kvMap = new LinkedHashMap<>(16);
        for (int i = 0; i < kvPairList.size(); i += 2) {

            String k = kvPairList.get(i);
            String v = null;
            if (i + 1 < kvPairList.size()) {
                v = kvPairList.get(i + 1);
            }
            kvMap.put(k, v);
        }
        System.out.println("kvMap = " + kvMap);

        //
        //        StringTokenizer kvSt = new StringTokenizer();
        //        kvSt.setDelimiterMatcher(StringMatcherFactory.INSTANCE.charMatcher('='));
        //        kvSt.setQuoteMatcher(StringMatcherFactory.INSTANCE.charMatcher('`'));
        //        kvSt.setIgnoredMatcher(StringMatcherFactory.INSTANCE.noneMatcher());
        //        kvSt.setTrimmerMatcher(StringMatcherFactory.INSTANCE.trimMatcher());
        //        kvSt.setEmptyTokenAsNull(false);
        //        kvSt.setIgnoreEmptyTokens(false);
        //
        //        Map<String, String> kvMap = new LinkedHashMap<>(16);
        //        for (String kvPair : kvPairList) {
        //            kvSt.reset(kvPair);
        //            List<String> kvList = kvSt.getTokenList();
        //            String k = kvList.size() >= 1 ? kvList.get(0) : null;
        //            String v = kvList.size() >= 2 ? kvList.get(1) : null;
        //            kvMap.put(k, v);
        //        }
        //        System.out.println(kvMap);
        //        Assertions.assertEquals("aaaa", kvMap.get("aa"));
        //        Assertions.assertEquals("bbbb", kvMap.get("bb"));
        //        Assertions.assertEquals("cc=cc", kvMap.get("c=c"));
        //        Assertions.assertEquals("dd,dd", kvMap.get("d,d"));
        //        Assertions.assertEquals("ff`ff", kvMap.get("f`f"));
        //        Assertions.assertEquals("gg``gg", kvMap.get("g``g"));
    }
}
