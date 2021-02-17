package com.github.btpka3.first.intellij.plugin;

import com.intellij.psi.FileViewProvider;
import com.intellij.psi.impl.source.xml.XmlFileImpl;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.xml.XmlFile;

/**
 * @author dangqian.zll
 * @date 2021/2/17
 */
public class PomFile extends XmlFileImpl implements XmlFile {

    static final IFileElementType POM_FILE_ELEMENT_TYPE = new IFileElementType("POM_FILE_ELEMENT_TYPE", PomLanguage.INSTANCE);

    public PomFile(FileViewProvider viewProvider) {
        super(viewProvider, PomFile.POM_FILE_ELEMENT_TYPE);
    }

    @Override
    public String toString() {
        return "PomFile:" + getName();
    }

}
