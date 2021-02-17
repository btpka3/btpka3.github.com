package com.github.btpka3.first.intellij.plugin;

import com.intellij.formatting.FormattingContext;
import com.intellij.formatting.FormattingModel;
import com.intellij.formatting.FormattingModelBuilder;
import com.intellij.lang.xml.XMLLanguage;
import com.intellij.lang.xml.XmlFormattingModel;
import com.intellij.lang.xml.XmlFormattingModelBuilder;
import com.intellij.psi.PsiFile;
import com.intellij.psi.formatter.FormattingDocumentModelImpl;
import com.intellij.psi.formatter.xml.XmlBlock;
import com.intellij.psi.formatter.xml.XmlPolicy;
import org.jetbrains.annotations.NotNull;

/**
 * @author dangqian.zll
 * @date 2021/2/17AbstractBlock
 * @see XmlFormattingModelBuilder
 * @see XMLLanguage#INSTANCE
 */
public class PomFormattingModelBuilder implements FormattingModelBuilder {

    public PomFormattingModelBuilder() {
        System.out.println("first.intellij.plugin PomFormattingModelBuilder");
    }

    @Override
    @NotNull
    public FormattingModel createModel(@NotNull FormattingContext formattingContext) {

        System.out.println("first.intellij.plugin first log");

        // com.intellij.psi.impl.source.xml.XmlDocumentImpl : formattingContext.getNode().getFirstChildNode().getClass()

        PsiFile containingFile = formattingContext.getContainingFile();
        final FormattingDocumentModelImpl documentModel = FormattingDocumentModelImpl.createOn(containingFile);

        XmlBlock xmlBlock = new XmlBlock(
                formattingContext.getNode(),
                null,
                null,
                new XmlPolicy(formattingContext.getCodeStyleSettings(), documentModel),
                null,
                null,
                false
        );

//        return FormattingModelProvider.createFormattingModelForPsiFile(
//                containingFile,
//                xmlBlock,
//                formattingContext.getCodeStyleSettings()
//        );

        return new XmlFormattingModel(containingFile, xmlBlock, documentModel);
    }
}
