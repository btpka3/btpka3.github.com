

- [IntelliJ Platform](https://www.jetbrains.com/opensource/idea/)
- [gradle-intellij-plugin](https://github.com/JetBrains/gradle-intellij-plugin/)
- [IntelliJ Platform SDK](https://plugins.jetbrains.com/docs/intellij/welcome.html)
    - [xml-dom-api](https://plugins.jetbrains.com/docs/intellij/xml-dom-api.html#abstract)
        - com.intellij.psi.xml.XmlDocument
    - [Extension Point List](https://plugins.jetbrains.com/docs/intellij/extension-point-list.html#orgjetbrainspluginsyaml)
- [xml-psi-api](https://github.com/JetBrains/intellij-community/tree/master/xml)
- [intellij-sdk-code-samples](https://github.com/JetBrains/intellij-sdk-code-samples/blob/main/action_basics/src/main/resources/META-INF/plugin.xml)
- [com.intellij.psi.PsiFileFactory](https://github.com/JetBrains/intellij-community/blob/master/platform/core-api/src/com/intellij/psi/PsiFileFactory.java)
- [intellij-community](https://github.com/JetBrains/intellij-community)
- com.intellij.util.xml.DomElement
- com.intellij.util.xml.DomManager
- com.intellij.openapi.application.ApplicationStarter
- com.intellij.formatting.commandLine.FileSetFormatter
- com.intellij.lang.ASTFactory.composite
- com.intellij.lang.xml.XmlASTFactory.createComposite
- com.intellij.psi.xml.XmlTagValue.hasCDATA
- com.intellij.psi.xml.XmlElementType.XML_CDATA
- com.intellij.psi.xml.XmlTokenType.XML_CDATA_START
- com.intellij.psi.impl.source.xml.XmlTextImpl.getValue
- com.intellij.psi.PsiRecursiveElementWalkingVisitor
- LanguageParserDefinitions.INSTANCE.forLanguage(language).createFile(fileViewProvider)
- com.intellij.psi.util.PsiTreeUtil.getParentOfType
- com.intellij.psi.util.PsiTreeUtil.findChildrenOfType
- com.intellij.lang.xml.XmlFormattingModel

XmlText#getChildren()[0] == XmlASTFactory$1

XmlASTFactory$1#getChildren()[0] == XmlTokenImpl (text="<![CDATA[", elementType="XML_CDATA_START") 
XmlASTFactory$1#getChildren()[1] == XmlTokenImpl (text="ggg",       elementType="XML_DATA_CHARACTERS" )
XmlASTFactory$1#getChildren()[2] == XmlTokenImpl (text="]]>",       elementType="XML_CDATA_END")

- com.intellij.psi.codeStyle.autodetect.IndentOptionsDetectorImpl.calcLineIndentInfo
- com.intellij.psi.codeStyle.autodetect.FormatterBasedLineIndentInfoBuilder
- com.intellij.codeInsight.actions.ReformatCodeProcessor
- com.intellij.psi.codeStyle.CodeStyleManager.reformatText 
- com.intellij.psi.impl.source.codeStyle.CodeFormatterFacade.processText
- com.intellij.formatting.FormatterImpl.format
- com.intellij.formatting.FormatProcessor.format 
- com.intellij.formatting.FormattingProgressTask
- com.intellij.formatting.WhiteSpace.performModification
- com.intellij.formatting.WhiteSpace.arrangeLineFeeds
- com.intellij.formatting.engine.WrapProcessor.processWrap  # 缩进