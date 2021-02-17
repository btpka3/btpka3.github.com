package com.github.btpka3.first.intellij.plugin;

import com.intellij.icons.AllIcons;
import com.intellij.ide.highlighter.XmlLikeFileType;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.util.NlsSafe;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author dangqian.zll
 * @date 2021/2/17
 */
public class PomLanguageFileType extends XmlLikeFileType {

    public static final PomLanguageFileType INSTANCE = new PomLanguageFileType();


    protected PomLanguageFileType() {
        super(PomLanguage.INSTANCE);
    }

    @Override
    public @NonNls
    @NotNull String getName() {
        return "POM";
    }

    @Override
    public @NlsContexts.Label @NotNull String getDescription() {
        return "apache maven POM xml";
    }

    @Override
    public @NlsSafe @NotNull String getDefaultExtension() {
        return "pom.xml";
    }

    @Override
    public @Nullable Icon getIcon() {
        return AllIcons.FileTypes.Xml;
    }
}
