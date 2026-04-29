package com.github.btpka3.first.spring.boot4.shell;

import org.jline.reader.Highlighter;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author dangqian.zll
 * @date 2026/4/29
 * @see <a href="https://jline.org/">jline</a>
 */
public class JLineTest {


    public void x() throws IOException {
        Terminal terminal = TerminalBuilder.terminal();
        terminal = TerminalBuilder.builder()
                .system(true)
                .streams(System.in, System.out)
                .encoding(StandardCharsets.UTF_8)
                .jansi(true)
                .build();
    }

    public void lineReader() {
        Highlighter blueHighlighter = new Highlighter() {
            @Override
            public AttributedString highlight(LineReader reader, String buffer) {
                // Apply blue color to the entire buffer
                return new AttributedString(buffer, AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE));
            }
        };
        Terminal terminal = null;
        LineReader lineReader = LineReaderBuilder.builder()
                .terminal(terminal)
                .highlighter(blueHighlighter)
                .build();
    }

}
