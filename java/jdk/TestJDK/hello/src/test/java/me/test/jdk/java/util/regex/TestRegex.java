package me.test.jdk.java.util.regex;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegex {

    public static void main(String[] args) {
        testEscape();

    }

    /**
     * 处理字符串时，常常会有转义字符。
     * 顺序很重要。reg1 和 reg2 中的或是调换顺序的。
     */
    public static void testEscape() {
        // mysqldump出的VARCHAR字段可能会是这样 :
        // INSERT INTO `my_table` VALUES ('aa\'bb','',NULL,0),('cc\'dd','',NULL,0);
        String sql = "INSERT INTO `my_table` VALUES ('aa\\'bb','',NULL,0,'111'),('cc\\'dd','',NULL,0,'222');\n";

        String reg1 = "'(\\\\'|[^'])*'";
        Pattern p1 = Pattern.compile(reg1);
        Matcher m1 = p1.matcher(sql);
        List<String> reusltList1 = new ArrayList<String>();
        while (m1.find()) {
            reusltList1.add(m1.group(0));
        }
        System.out.println("reg1 match result : " + reusltList1);

        String reg2 = "'([^']|\\\\')*'";
        Pattern p2 = Pattern.compile(reg2);
        Matcher m2 = p2.matcher(sql);
        List<String> reusltList2 = new ArrayList<String>();
        while (m2.find()) {
            reusltList2.add(m2.group(0));
        }
        System.out.println("reg2 match result : " + reusltList2);

        /*
         * output:
         * reg1 match result : ['aa\'bb', '', '111', 'cc\'dd', '', '222']
         * reg2 match result : ['aa\', ',', ',NULL,0,', '),(', 'dd', '', '222']
         */
    }

    @Test
    public void x(){
        String REGEX_A_NUM = "^A\\d+$";
        Pattern P_ANum = Pattern.compile(REGEX_A_NUM);
        String s = "A22851";
        boolean result = P_ANum.matcher(s).matches();
        Assert.assertTrue(result);
    }

}
