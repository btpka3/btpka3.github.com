package com.github.btpka3.first.spring.boot4.shell;

import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStyle;
import org.springframework.messaging.Message;
import org.springframework.shell.jline.tui.component.view.TerminalUI;
import org.springframework.shell.jline.tui.component.view.TerminalUIBuilder;
import org.springframework.shell.jline.tui.component.view.TerminalUICustomizer;
import org.springframework.shell.jline.tui.component.view.control.BoxView;
import org.springframework.shell.jline.tui.component.view.control.DialogView;
import org.springframework.shell.jline.tui.component.view.event.EventLoop;
import org.springframework.shell.jline.tui.component.view.event.KeyEvent;
import org.springframework.shell.jline.tui.component.view.event.MouseEvent;
import org.springframework.shell.jline.tui.style.FigureSettings;
import org.springframework.shell.jline.tui.style.StyleSettings;
import org.springframework.shell.jline.tui.style.ThemeResolver;
import reactor.core.publisher.Flux;

/**
 *
 * @author dangqian.zll
 * @date 2026/4/29
 */
public class TerminalUITest {

    public void x() {
        ThemeResolver resolver = null;

        String resolvedStyle = resolver.resolveStyleTag(StyleSettings.TAG_TITLE);
        // bold,fg:bright-white

        AttributedStyle style = resolver.resolveStyle(resolvedStyle);
        // jline attributed style from expression above
        String resolvedFigure = resolver.resolveFigureTag(FigureSettings.TAG_ERROR);

        Terminal terminal = null;
        TerminalUICustomizer customizer = null;
        TerminalUIBuilder builder = new TerminalUIBuilder(terminal, customizer);
        TerminalUI ui = builder.build();

        BoxView view = new BoxView();
        ui.configure(view);

        ui.run();

        {
            DialogView dialog = new DialogView();
            // set modal
            ui.setModal(dialog);
            // clear modal
            ui.setModal(null);
        }

        EventLoop eventLoop = ui.getEventLoop();
        Flux<? extends Message<?>> events = eventLoop.events();
        events.subscribe();

        eventLoop.keyEvents().subscribe((KeyEvent event) -> {
            // do something with key event
            if (event.getPlainKey() == KeyEvent.Key.c && event.hasCtrl()) {
                // 实现 double-tap 逻辑
            }
        });

        eventLoop.mouseEvents().subscribe((MouseEvent event) -> {
            // do something with mouse event
        });
    }
}
