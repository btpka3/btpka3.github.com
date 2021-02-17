package com.github.btpka3.first.intellij.plugin;

import com.intellij.lang.Language;
import com.intellij.lang.xml.XMLLanguage;

/**
 * @author dangqian.zll
 * @date 2021/2/17
 */
public class PomLanguage extends Language {

    public final static PomLanguage INSTANCE = new PomLanguage();

    protected PomLanguage() {
        super(XMLLanguage.INSTANCE, "POM");
    }
}
