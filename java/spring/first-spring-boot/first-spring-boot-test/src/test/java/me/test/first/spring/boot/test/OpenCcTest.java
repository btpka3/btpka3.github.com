package me.test.first.spring.boot.test;

import com.github.houbb.opencc4j.util.ZhConverterUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author dangqian.zll
 * @date 2023/10/20
 * @see <a href="https://github.com/BYVoid/OpenCC">opencc</a>
 * @see <a href="https://github.com/houbb/opencc4j">opencc4j</a>
 */
public class OpenCcTest {

    @Test
    public void test(){
        assertEquals("生命不息，奋斗不止", ZhConverterUtil.toSimple("生命不息，奮鬥不止"));
        {
            // 🌶🌶🌶🌶🌶
            // "\uD83C\uDF36\uFE0F"️
            // "\uD83C\uDF36"️
            String str = "\uD83C\uDF36\uFE0F";
            assertEquals(str, ZhConverterUtil.toSimple(str));
            // [🌶️]
            str="\uD83C\uDF36";
            assertEquals(str, ZhConverterUtil.toSimple(str));
        }
    }
}
