package me.test;

import com.google.common.base.Throwables;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.owasp.html.*;
import org.owasp.html.examples.EbayPolicyExample;
import org.owasp.validator.html.*;

import java.io.IOException;
import java.io.InputStream;


public class Test123 {
    static final String drityInput = "<script>alert(1)</script>"
            + "<a href='#bb' data-one='1' class='aa bb' style='z-index:999; width:100%;       aa:bb;display:block;overflow:hidden;color:red;' onclick='xxx'>aa 汉字。「，，，</a>"
            + "<p align='center' style='z-index:999; width:100%; align:left;display:block;overflow:hidden;color:red;'>1111 &nbsp; </p>"
            + "<iframe src='javascript:xxx'></iframe>"
            + "<xxx>xxx 中文。「，，，=&nbsp;="
            + "&lt;script/&gt;alert('111');&lt;script/&gt; </xxx>";

    public static void main(String[] args) throws ScanException, PolicyException {
        System.out.println("------------------------------------------ testJsoup()");
        testJsoup();

        System.out.println("------------------------------------------ testAntiSamy()");
        testAntiSamy();

        System.out.println("------------------------------------------ testHtmlSanitizer()");
        testHtmlSanitizer();

    }

    public static void testJsoup() {
        String safe = Jsoup.clean(drityInput, Whitelist.basic());
        System.out.println(safe);
    }

    public static void testAntiSamy() throws PolicyException, ScanException {

        InputStream prolicyIn = Test123.class.getResourceAsStream("antisamy-ebay-1.4.4.xml");
        Policy policy = Policy.getInstance(prolicyIn);
        AntiSamy as = new AntiSamy();

        // 输出： <a href="#bb" style="width: 100.0%;">aa</a> xxx
        CleanResults cr = as.scan(drityInput, policy, AntiSamy.SAX);
        String cleanResult = cr.getCleanHTML();
        System.out.println(cleanResult);
    }

    public static void testHtmlSanitizer() {
        StringBuilder buf = new StringBuilder();
        HtmlStreamRenderer renderer = HtmlStreamRenderer.create(
                buf,
                // Receives notifications on a failure to write to the output.
                new Handler<IOException>() {
                    public void handle(IOException ex) {
                        Throwables.propagate(ex);  // System.out suppresses IOExceptions
                    }
                },
                // Our HTML parser is very lenient, but this receives notifications on
                // truly bizarre inputs.
                new Handler<String>() {
                    public void handle(String x) {
                        throw new AssertionError(x);
                    }
                });
        HtmlSanitizer.sanitize(drityInput, EbayPolicyExample.POLICY_DEFINITION.apply(renderer));
        System.out.println(buf);
        System.out.println(StringEscapeUtils.unescapeHtml4(buf.toString()));
    }
}
