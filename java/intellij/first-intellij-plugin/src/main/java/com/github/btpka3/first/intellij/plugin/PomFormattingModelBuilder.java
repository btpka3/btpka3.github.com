package com.github.btpka3.first.intellij.plugin;

import com.intellij.formatting.FormattingContext;
import com.intellij.formatting.FormattingModel;
import com.intellij.formatting.FormattingModelBuilder;
import com.intellij.lang.ASTNode;
import com.intellij.lang.xml.XMLLanguage;
import com.intellij.lang.xml.XmlASTFactory;
import com.intellij.lang.xml.XmlFormattingModel;
import com.intellij.lang.xml.XmlFormattingModelBuilder;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.formatter.FormattingDocumentModelImpl;
import com.intellij.psi.formatter.xml.XmlBlock;
import com.intellij.psi.formatter.xml.XmlPolicy;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlElementType;
import com.intellij.psi.xml.XmlTag;
import com.intellij.psi.xml.XmlTagValue;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Stream;

import static com.intellij.psi.xml.XmlTokenType.XML_WHITE_SPACE;


/**
 * @author dangqian.zll
 * @date 2021/2/17AbstractBlock
 * @see XmlFormattingModelBuilder
 * @see XMLLanguage#INSTANCE
 */
public class PomFormattingModelBuilder implements FormattingModelBuilder {
    private static Logger log = LoggerFactory.getLogger(PomFormattingModelBuilder.class);
    private static final XmlTag START_NULL_TAG_PLACEHOLDER = (XmlTag) XmlASTFactory.composite(XmlElementType.XML_TAG);
    private static final XmlTag END_NULL_TAG_PLACEHOLDER = (XmlTag) XmlASTFactory.composite(XmlElementType.XML_TAG);
    private static final String START_NULL_TAG_NAME = StringUtils.repeat(' ', 32);
    private static final String END_NULL_TAG_NAME = StringUtils.repeat('~', 32);

    private static final PsiElement[] EMPTY_ARRAY = new PsiElement[0];
    private static String NEW_LINE_LINUX = "\n";
    private static String NEW_LINE_MACOS = "\n\r";
    private static String NEW_LINE_WIN = "\r\n";

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

        ASTNode rootNode = formattingContext.getNode();

        process(rootNode);

        XmlBlock xmlBlock = new XmlBlock(
                formattingContext.getNode(),
                null,
                null,
                new XmlPolicy(formattingContext.getCodeStyleSettings(), documentModel),
                null,
                null,
                false
        );

        return new XmlFormattingModel(containingFile, xmlBlock, documentModel);
    }


    public void process(ASTNode root) {
        try {
            Stream.of(root.getChildren(null))
                    .filter(node -> node instanceof XmlDocument)
                    .map(XmlDocument.class::cast)
                    .forEach(this::processDocument);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            log.error("lalalalala", e);
        }
    }

    public void processDocument(XmlDocument doc) {

        CommandProcessor commandProcessor = CommandProcessor.getInstance();
        commandProcessor.executeCommand(doc.getProject(), () -> {

            XmlTag rootTag = doc.getRootTag();
            xddd(rootTag);
        }, null, null);

    }

    public void processProject(XmlTag project) {

        XmlTag[] tags = project.findSubTags("aaa", "a");
        if (tags.length == 0) {
            XmlTag tag = project.createChildTag("aaa", "a", "p1<p>p2</p>p3", false);
            project.addSubTag(tag, false);
        }
    }

    public void addAaa(XmlTag project) {

        XmlTag[] tags = project.findSubTags("aaa", "a");
        if (tags.length == 0) {
            XmlTag tag = project.createChildTag("aaa", "a", "p1<p>p2</p>p3", false);
            project.addSubTag(tag, false);
        }
    }


    public void addCdata(XmlTag project) {

        XmlTag[] tags = project.findSubTags("g");

        if (tags.length == 0) {
            XmlTag tag = project.createChildTag("g", null, "<b>bbb</b>", false);

            XmlTagValue v = tag.getValue();
            v.setEscapedText(v.getText() + "xxx<![CDATA[yyyy]]>zzz");
//            XmlTagChild[] oldChild = v.getChildren();
//            v.setEscapedText("xxx<![CDATA[yyyy]]>zzz");
//            XmlTagChild[] newChild = v.getChildren();


//            XmlText xmlText = (XmlText) XmlASTFactory.composite(XmlElementType.XML_TEXT);
//            tag.add(xmlText);
//
//            CompositeElement cdata = XmlASTFactory.composite(XmlElementType.XML_CDATA);
//            xmlText.getNode().addChild(cdata);
//            cdata.putUserData(TreeUtil.CONTAINING_FILE_KEY_AFTER_REPARSE, project.getUserData(TreeUtil.CONTAINING_FILE_KEY_AFTER_REPARSE));
//            cdata.addChild(XmlASTFactory.leaf(XmlTokenType.XML_CDATA_START, "<![CDATA["));
//            {
//                cdata.addChild(XmlASTFactory.leaf(XmlTokenType.XML_DATA_CHARACTERS, "999"));
//            }
//            cdata.addChild(XmlASTFactory.leaf(XmlTokenType.XML_CDATA_END, "]]>"));
//
            project.addSubTag(tag, false);
        }

    }

    protected void xddd(XmlTag project) {
        Stream.of(project.getSubTags())
                .filter(tag -> Objects.equals("dependencies", tag.getName()))
                .forEach(this::processDependencies);
    }

    protected void processDependencies(XmlTag dependencies) {
        Stream.of(dependencies.getSubTags())
                .filter(tag -> Objects.equals("dependency", tag.getName()))
                .forEach(this::processDependency);
    }

    protected void processDependency(XmlTag dependency) {
        PsiElement[] subElements = dependency.getValue().getChildren();
        XmlTag[] sbuTags = dependency.getSubTags();
        if (sbuTags.length <= 1) {
            return;
        }
        PsiElement lastElement = subElements[subElements.length - 1];

        LinkedHashMap<XmlTag, Pair<StringBuilder, StringBuilder>> g = splitElementsByTag01(dependency);
//        LinkedHashMap<XmlTag, PsiElement[]> b = group(g);
        LinkedHashMap<XmlTag, Pair<StringBuilder, StringBuilder>> s = sort(g);

        StringBuilder newTextBuf = new StringBuilder();


        for (Map.Entry<XmlTag, Pair<StringBuilder, StringBuilder>> entry : s.entrySet()) {

            newTextBuf.append(entry.getValue().getLeft());

            XmlTag tag = entry.getKey();
            if (tag != START_NULL_TAG_PLACEHOLDER && tag != END_NULL_TAG_PLACEHOLDER) {
                newTextBuf.append(tag.getText());
            }
            newTextBuf.append(entry.getValue().getRight());
        }
        dependency.getValue().setText(newTextBuf.toString());
        //dependency.deleteChildRange(subElements[0], subElements[subElements.length - 1]);
    }

    @NotNull
    protected LinkedHashMap<XmlTag, Pair<List<PsiElement>, List<PsiElement>>> splitElementsByTag(XmlTag parent) {

        XmlTagValue tagValue = parent.getValue();

        // allChildren 包含：开始标签和结束标签
        PsiElement[] allChildren = tagValue.getChildren();
        XmlTag[] subTags = parent.getSubTags();

        List<XmlTag> allTagsWithMock = new ArrayList<>(subTags.length + 2);
        allTagsWithMock.add(START_NULL_TAG_PLACEHOLDER);
        allTagsWithMock.addAll(Arrays.asList(subTags));
        allTagsWithMock.add(END_NULL_TAG_PLACEHOLDER);

        LinkedHashMap<XmlTag, Pair<List<PsiElement>, List<PsiElement>>> result = new LinkedHashMap<>(subTags.length);
        allTagsWithMock.forEach(tag -> result.put(tag, Pair.of(new ArrayList<>(), new ArrayList<>())));

        for (int i = 1; i < allTagsWithMock.size(); i++) {
            XmlTag startTag = allTagsWithMock.get(i - 1);
            XmlTag endTag = allTagsWithMock.get(i);

            PsiElement[] elements = findElementsBetweenTag(allChildren, fixPlaceHolder(startTag), fixPlaceHolder(endTag));

            int idx = split(elements);

            if (idx > 0) {
                result.get(startTag)
                        .getRight()
                        .addAll(Arrays.asList(ArrayUtils.subarray(elements, 0, idx)));
            }
            if (idx < elements.length) {
                result.get(endTag)
                        .getLeft()
                        .addAll(Arrays.asList(ArrayUtils.subarray(elements, idx, elements.length)));
            }
        }
        return result;
    }


    @NotNull
    protected LinkedHashMap<XmlTag, Pair<StringBuilder, StringBuilder>> splitElementsByTag01(XmlTag parent) {

        XmlTagValue tagValue = parent.getValue();

        // allChildren 包含：开始标签和结束标签
        PsiElement[] allChildren = tagValue.getChildren();
        XmlTag[] subTags = parent.getSubTags();

        List<XmlTag> allTagsWithMock = new ArrayList<>(subTags.length + 2);
        allTagsWithMock.add(START_NULL_TAG_PLACEHOLDER);
        allTagsWithMock.addAll(Arrays.asList(subTags));
        allTagsWithMock.add(END_NULL_TAG_PLACEHOLDER);

        LinkedHashMap<XmlTag, Pair<StringBuilder, StringBuilder>> result = new LinkedHashMap<>(subTags.length);
        allTagsWithMock.forEach(tag -> result.put(tag, Pair.of(new StringBuilder(), new StringBuilder())));

        for (int i = 1; i < allTagsWithMock.size(); i++) {
            XmlTag startTag = allTagsWithMock.get(i - 1);
            XmlTag endTag = allTagsWithMock.get(i);

            PsiElement[] elements = findElementsBetweenTag(allChildren, fixPlaceHolder(startTag), fixPlaceHolder(endTag));

            int idx = split(elements);

            StringBuilder startEleRightBuf = result.get(startTag).getRight();
            StringBuilder endEleLeftBuf = result.get(endTag).getLeft();

            PsiElement[] leftArr = idx > 0
                    ? ArrayUtils.subarray(elements, 0, idx)
                    : EMPTY_ARRAY;
            PsiElement splitEle = idx >= 0 && idx < elements.length
                    ? elements[idx]
                    : null;
            PsiElement[] rightArr = idx < (elements.length - 1)
                    ? ArrayUtils.subarray(elements, idx + 1, elements.length)
                    : EMPTY_ARRAY;

            for (PsiElement ele : leftArr) {
                startEleRightBuf.append(ele.getText());
            }
            if (splitEle != null) {
                Pair<String, String> pair = splitText(splitEle.getText());
                startEleRightBuf.append(pair.getLeft());
                endEleLeftBuf.append(pair.getRight());
            }
            for (PsiElement ele : rightArr) {
                endEleLeftBuf.append(ele.getText());
            }
        }
        return result;
    }

    protected Pair<String, String> splitText(String str) {

        if (str == null || str.length() == 0) {
            return Pair.of("", "");
        }

        {
            int idx = str.indexOf(NEW_LINE_MACOS);
            if (idx >= 0) {
                int splitIdx = idx + NEW_LINE_MACOS.length();
                return Pair.of(str.substring(0, splitIdx), str.substring(splitIdx));
            }
        }
        {
            int idx = str.indexOf(NEW_LINE_WIN);
            if (idx >= 0) {
                int splitIdx = idx + NEW_LINE_WIN.length();
                return Pair.of(str.substring(0, splitIdx), str.substring(splitIdx));
            }
        }
        {
            int idx = str.indexOf(NEW_LINE_LINUX);
            if (idx >= 0) {
                int splitIdx = idx + NEW_LINE_LINUX.length();
                return Pair.of(str.substring(0, splitIdx), str.substring(splitIdx));
            }
        }
        return Pair.of(str, "");
    }


    protected XmlTag fixPlaceHolder(XmlTag tag) {
        if (tag == START_NULL_TAG_PLACEHOLDER || tag == END_NULL_TAG_PLACEHOLDER) {
            return null;
        }
        return tag;
    }

//    @NotNull
//    protected LinkedHashMap<XmlTag, PsiElement[]> group(LinkedHashMap<XmlTag, PsiElement[]> map) {
//
////
////        XmlTagValue tagValue = parent.getValue();
////
////        // allChildren 包含：开始标签和结束标签
////        PsiElement[] allChildren = tagValue.getChildren();
////        XmlTag[] subTags = parent.getSubTags();
//
//        LinkedHashMap<XmlTag, PsiElement[]> result = new LinkedHashMap<>(subTags.length + 1);
////        XmlTag preTag = null;
////        XmlTag nextTag = null;
//
//        map.entrySet()
//                .stream()
//                .sorted(Comparator.comparing((Map.Entry<XmlTag, PsiElement[]> entry) -> {
//                    XmlTag tag = entry.getKey();
//                    if (START_NULL_TAG_PLACEHOLDER == tag) {
//                        return START_NULL_TAG_NAME;
//                    }
//                    if (END_NULL_TAG_PLACEHOLDER == tag) {
//                        return END_NULL_TAG_NAME;
//                    }
//                    return tag.getName();
//                }))
//                .forEach(entry -> result.put(entry.getKey(), entry.getValue()));
////        ;
////
////        for (Map.Entry<XmlTag, PsiElement[]> entry : map.entrySet()) {
////            preTag = nextTag;
////            nextTag = entry.getKey();
////            PsiElement[] elements = entry.getValue();
////            for (PsiElement element : elements) {
////                lastElement = dependency.addAfter(element, lastElement);
////            }
////        }
////
////        for (int i = 0; i < subTags.length; i++) {
////            preTag = nextTag;
////            nextTag = subTags[i];
////            PsiElement[] elements = findElementsBetweenTag(allChildren, preTag, nextTag);
////
////
////            result.put(nextTag, elements);
////        }
////        // the elements between last child tag end parent tag's end tag
////        result.put(END_NULL_TAG_PLACEHOLDER, findElementsBetweenTag(allChildren, nextTag, null));
//        return result;
//    }

    @NotNull
    protected PsiElement[] findElementsBetweenTag(
            @Nullable PsiElement[] allChildren,
            @Nullable XmlTag firstTag,
            @Nullable XmlTag endTag
    ) {
        if (firstTag == null && endTag == null) {
            return EMPTY_ARRAY;
        }
        if (allChildren == null || allChildren.length == 0) {
            return EMPTY_ARRAY;
        }

        int startIndex = 0;
        if (firstTag != null) {
            startIndex = ArrayUtils.indexOf(allChildren, firstTag);
            if (startIndex == allChildren.length - 1) {
                return EMPTY_ARRAY;
            }
            if (startIndex <= 0) {
                startIndex = 0;
            } else {
                startIndex = startIndex + 1;
            }
        }

        int endIndex = allChildren.length;
        if (endTag != null) {
            endIndex = ArrayUtils.indexOf(allChildren, endTag);
            if (endIndex <= 0) {
                endIndex = allChildren.length;
            }
        }
        return ArrayUtils.subarray(allChildren, startIndex, endIndex);
    }


    /**
     * the elements between tags.
     * <p>
     * - between parent tag's start tag and fist child tag's start tag
     * - between (n)th child tag's end tag and (n+1)th child tag's start tag
     * - between last child tag's end tag and parent tag's end tag
     *
     * @param nonTagElements
     * @return the element index which should group to previous tag.
     */
    protected int split(PsiElement[] nonTagElements) {
        if (nonTagElements == null || nonTagElements.length == 0) {
            return -1;
        }
        int elementsLen = nonTagElements.length;
        PsiElement lastElement = nonTagElements[elementsLen - 1];
        //if (Objects.equals(XML_EMPTY_ELEMENT_END, lastElement.getNode().getElementType())) {
        //    return -1;
        //}

        for (int i = 0; i < nonTagElements.length; i++) {
            PsiElement ele = nonTagElements[i];
            IElementType eleType = ele.getNode().getElementType();
            if (Objects.equals(XmlElementType.XML_TEXT, eleType)) {
                for (PsiElement textChild : ele.getChildren()) {
                    IElementType textChildEleType = textChild.getNode().getElementType();
                    if (Objects.equals(XML_WHITE_SPACE, textChildEleType)) {
                        PsiWhiteSpace whiteSpace = (PsiWhiteSpace) textChild;
                        String text = whiteSpace.getText();
                        if (containsNewLine(text)) {
                            return i;
                        }
                    }
                }
            }
        }

        return elementsLen;
    }

    protected boolean containsNewLine(String text) {
        if (text == null) {
            return false;
        }
        return text.contains(NEW_LINE_LINUX) || text.contains(NEW_LINE_MACOS) || text.contains(NEW_LINE_WIN);
    }

    private static List<String> DEP_LIST = Arrays.asList("groupId", "artifactId", "version", "*");


    protected LinkedHashMap<XmlTag, Pair<StringBuilder, StringBuilder>> sort(
            @NotNull LinkedHashMap<XmlTag, Pair<StringBuilder, StringBuilder>> map
    ) {
        LinkedHashMap<XmlTag, Pair<StringBuilder, StringBuilder>> result = new LinkedHashMap<>(map.size());

        map.entrySet()
                .stream()
                .sorted(Comparator.comparing(entry -> {
                    XmlTag tag = entry.getKey();
                    if (START_NULL_TAG_PLACEHOLDER == tag) {
                        return -1;
                    }
                    if (END_NULL_TAG_PLACEHOLDER == tag) {
                        return Integer.MAX_VALUE;
                    }
                    String tagName = tag.getName();
                    Integer idx = DEP_LIST.indexOf(tagName);
                    return idx < 0
                            ? Integer.MAX_VALUE - 1
                            : idx;
                }))
                .forEach(entry -> result.put(entry.getKey(), entry.getValue()));
        return result;
//        ;

//
//        if (map.size() <= 1) {
//            return map;
//        }

//        Comparator<XmlTag> c = (tag1, tag2) -> {
//            if (tag1 == tag2) {
//                return 0;
//            }
//            if (START_NULL_TAG_PLACEHOLDER == tag1) {
//                return -1;
//            }
//            if (END_NULL_TAG_PLACEHOLDER == tag1) {
//                return 1;
//            }
//            return 0;
//        };

//        Comparator.comparing()
//        List<Map.Entry<XmlTag, List<PsiElement>>> list = map.entrySet().stream()
//                .sorted(Comparator.nullsLast(Comparator.comparing((Map.Entry<XmlTag, PsiElement[]> entry) -> {
//                            XmlTag tag = entry.getKey();
//                            if (tag == null) {
//                                return null;
//                            }
//                            String tagName = tag.getName();
//                            int idx = DEP_LIST.indexOf(tagName);
//                            return idx < 0
//                                    ? Integer.MAX_VALUE
//                                    : idx;
//                        })
//                                .thenComparing(Comparator.nullsLast((Map.Entry<XmlTag, PsiElement[]> entry) -> {
//                                    entry.getKey().getName()
//                                }))
//                ))
//                .collect(Collectors.toList());
//        LinkedHashMap<XmlTag, PsiElement[]> newResult = new LinkedHashMap<>(list.size());
//        list.forEach(entry -> {
//            newResult.put(entry.getKey(), entry.getValue());
//        });
//        return newResult;
    }

}
