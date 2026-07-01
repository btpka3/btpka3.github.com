package me.test.com.ibm.icu;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;
import java.io.InputStream;
import lombok.SneakyThrows;
import me.test.com.github.albfernandez.UniversalDetectorTest;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

/**
 * CU4J（com.ibm.icu:icu4j）—— 首推
 * <p>
 * IBM/Unicode 联盟官方维护的国际化大库，里面的 com.ibm.icu.text.CharsetDetector 是工业级实现：
 * <p>
 * 支持声明 hint：detector.setDeclaredEncoding("GBK") 把已知编码权重提高
 * detectAll() 直接拿到候选列表+置信度+语言，业务层可以挑
 * 覆盖编码最全（GB18030/Big5/EUC-JP/Shift_JIS/EUC-KR/UTF-7/UTF-16/UTF-32/各种 Windows-125x/ISO-8859-x/IBM4xx/KOI8-R 等几十种）
 * 跟 Unicode 标准同步更新，算法跟 Mozilla universalchardet 类似但实现更扎实
 * 唯一缺点：jar 大（10+ MB），如果只为检测会嫌重
 *
 * @author dangqian.zll
 * @date 2026/6/30
 * @see UniversalDetectorTest
 */
public class Icu4jTest {

    @Test
    @SneakyThrows
    public void test() {
        // vim : `:set fileencoding?`
        System.out.println("test-UTF8.txt : \n" + guessEncoding("test-UTF8.txt", "GB18030"));
        System.out.println("test-GBK.txt  : \n" + guessEncoding("test-GBK.txt","UTF-8"));
    }

    @SneakyThrows
    public String guessEncoding(String resource, String declaredEncoding) {
        InputStream in = UniversalDetectorTest.class.getResourceAsStream(resource);
        byte[] bytes = IOUtils.toByteArray(in);
        CharsetDetector detector = new CharsetDetector();
        detector.setDeclaredEncoding(declaredEncoding);
        detector.setText(bytes);

        CharsetMatch[] matches = detector.detectAll();  // 返回所有候选，按 confidence 排序
        StringBuilder buf = new StringBuilder();
        for (CharsetMatch m : matches) {
            buf.append(m.getName()).append(" ").append(m.getConfidence()).append(" ").append(m.getLanguage()).append("\n");
        }
        return buf.toString();
    }
}
