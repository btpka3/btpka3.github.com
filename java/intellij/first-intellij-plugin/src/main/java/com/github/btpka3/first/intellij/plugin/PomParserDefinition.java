package com.github.btpka3.first.intellij.plugin;

import com.intellij.lang.xml.XMLParserDefinition;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;

/**
 * @author dangqian.zll
 * @date 2021/2/17
 */
public class PomParserDefinition extends XMLParserDefinition {

    @Override
    public IFileElementType getFileNodeType() {
        return PomFile.POM_FILE_ELEMENT_TYPE;
    }

    @Override
    public PsiFile createFile(FileViewProvider viewProvider) {
        return new PomFile(viewProvider);
    }
}
