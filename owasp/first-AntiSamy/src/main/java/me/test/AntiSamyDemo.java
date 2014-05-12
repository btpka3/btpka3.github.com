package me.test;

import java.io.IOException;
import java.io.InputStream;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;

public class AntiSamyDemo {

    public static void main(String[] args) throws PolicyException, IOException, ScanException {

        InputStream prolicyIn = AntiSamyDemo.class.getResourceAsStream("antisamy-ebay-1.4.4.xml");
        Policy policy = Policy.getInstance(prolicyIn);
        AntiSamy as = new AntiSamy();
        String drityInput = "<script>alert(1)</script>"
                + "<a href='#bb' style='z-index:999; width:100%;' onclick='xxx'>aa</a>"
                + "<iframe src='javascript:xxx'></iframe>"
                + "<xxx>xxx</xxx>";

        // 输出： <a href="#bb" style="width: 100.0%;">aa</a> xxx
        CleanResults cr = as.scan(drityInput, policy, AntiSamy.SAX);
        String cleanResult = cr.getCleanHTML();
        System.out.println(cleanResult);
    }
}
