package me.test.jdk.java.text;

import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

/**
 *
 * @author dangqian.zll
 * @date 2025/12/26
 */
public class NumberFormatTest {
    @Test
    public void test01() {
        NumberFormat form = NumberFormat.getCurrencyInstance(Locale.CHINESE);
        assertInstanceOf(DecimalFormat.class, form);
        DecimalFormat f = (DecimalFormat) form;
        assertEquals("¤#,##0.00", f.toPattern());
        String str = form.format(1234567890.123);
        assertEquals("¤1,234,567,890.12", str);
    }
}
