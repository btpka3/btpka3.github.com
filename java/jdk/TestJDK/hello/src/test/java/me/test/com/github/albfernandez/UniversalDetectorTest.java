package me.test.com.github.albfernandez;

import java.io.IOException;
import java.io.InputStream;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mozilla.universalchardet.UniversalDetector;

/**
 * WINDOWS-1252 vs. cp936: 两者在 0x00–0x7F 都和 ASCII 兼容，纯英文文本三者（含 UTF-8）字节流一致。但：
 *
 * Windows-1252 的 0x80–0xFF 每个字节都是独立字符
 * cp936 的 0x81–0xFE 是双字节首字节，必须和后一个字节合起来解码
 * 都不是 UTF-8 的子集，互相误判就出乱码。
 * 实际踩坑场景
 * 把 cp936 的中文文件当 Windows-1252 打开，每个汉字会变成两个西欧怪字符（比如 中 0xD6 0xD0 显示成 ÖÐ）。反过来把 Windows-1252 的弯引号当 cp936，单字节 0x91/0x92 落进 cp936 的 ASCII 区（< 0x81）只会当成控制码或被丢弃，更常见的是单字节没法构成合法 cp936 序列直接报错。
 * Windows 中文版默认 ANSI 代码页就是 cp936，所以国内老 Windows 程序导出的 txt/csv 默认是 cp936；西欧地区的老 Windows 默认是 Windows-1252。现在统一上 UTF-8 就没这些问题了
 *
 * IBM855（也叫 CP855）是 IBM 给西里尔字母（俄语、保加利亚语、塞尔维亚语、乌克兰语等）设计的单字节字符集，属于 DOS 时代 OEM 代码页家族的一员。
 *
 * @author dangqian.zll
 * @date 2026/6/29
 */
public class UniversalDetectorTest {

    /*
是短文本下 GBK 和 Windows-1252 在字节层面本来就高度歧义，uchardet 的统计模型在样本不够时容易倒向 Windows-1252。原因拆开看：

1. 字节分布是合法的 Windows-1252

GBK 汉字首字节 0x81–0xFE、尾字节 0x40–0xFE，这些字节在 Windows-1252 里每一个都是合法可打印字符（拉丁扩展字母、弯引号、em dash 等）。也就是说，任何 GBK 文件用 Windows-1252 解码都不会报错，只会出现一堆"看起来像欧洲语言"的怪字符。两个候选编码都"解得通"，于是变成统计题。

2. uchardet 走的是 n-gram 频率打分

它对每种编码维护一份字符/二元组频率表，扫文件后看哪种语言模型分数最高。GBK 检测依赖足够多的高频汉字双字节对（"的"、"是"、"在"……）出现。README.md 如果是：

内容偏短（几 KB 以内）
中英文混排，ASCII 占比很高（代码块、链接、Markdown 语法）
中文里夹杂大量标点和西文符号
GBK 模型的统计置信度就拉不开，Windows-1252 的"通用拉丁文本"模型反而拿到更高分。这是 uchardet 已知的弱项，GitHub issue 上有不少类似反馈。

3. Vim 为什么对

Vim 不做内容统计，它按 fileencodings 列表顺序试解码，第一个能完整解码不报错的就采用。典型配置：


set fileencodings=ucs-bom,utf-8,cp936,gb18030,big5,euc-jp,latin1
UTF-8 因为有严格的字节模式约束（多字节序列必须 110xxxxx 10xxxxxx 这种结构），GBK 文件里随便一个汉字双字节（比如 0xD6 0xD0）就不符合 UTF-8 规则 → UTF-8 解码失败 → 退到下一个 cp936 → 成功 → 采用。Windows-1252 排在 latin1 这一档，前面的 cp936 已经匹配上了根本轮不到它。

juniversalchardet 是 Mozilla universalchardet（Firefox 用的那个）的 Java 移植，核心思路是多探测器并行 + 三段式投票，跟 uchardet（C 移植版）算法基本同源
     */

    @Test
    @SneakyThrows
    public void test() {
        // vim : `:set fileencoding?`
        System.out.println("test-UTF8.txt : " + guessEncoding("test-UTF8.txt"));
        System.out.println("test-GBK.txt  : " + guessEncoding("test-GBK.txt"));
    }

    public String guessEncoding(String resource) throws IOException {
        byte[] buf = new byte[4096];
        InputStream in = UniversalDetectorTest.class.getResourceAsStream(resource);

        // (1)
        UniversalDetector detector = new UniversalDetector();

        // (2)
        int nread;
        while ((nread = in.read(buf)) > 0 && !detector.isDone()) {
            detector.handleData(buf, 0, nread);
        }
        // (3)
        detector.dataEnd();

        // (4)
        String encoding = detector.getDetectedCharset();

        // (5)
        detector.reset();
        return encoding;
    }
}
