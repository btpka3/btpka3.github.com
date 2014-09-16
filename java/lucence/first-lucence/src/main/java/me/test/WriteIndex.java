package me.test;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class WriteIndex {

    public static void main(String[] args) throws IOException {
        File indexDir = new File("myIdx");
        if (indexDir.exists()) {
            indexDir.delete();
        }
        indexDir.mkdirs();

        // 创建索引
        Directory dir = FSDirectory.open(indexDir);
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_47);
        IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_47, analyzer);
        iwc.setOpenMode(OpenMode.CREATE);
        IndexWriter writer = new IndexWriter(dir, iwc);
        for (String adc : adcArr) {
            Document doc = new Document();
            Field pathField = new TextField("name", adc, Field.Store.YES);
            doc.add(pathField);
            writer.addDocument(doc);
            System.out.println(adc);
        }
        writer.close();
        System.out.println("done.");

    }

    public static final String[] adcArr = {
            "a b c",
            "abc",
            "北 京 市",
            "北京市",
            "东城区",
            "西城区",
            "朝阳区",
            "丰台区",
            "石景山区",
            "海淀区",
            "门头沟区",
            "房山区",
            "通州区",
            "顺义区",
            "昌平区",
            "大兴区",
            "怀柔区",
            "平谷区",
            "密云县",
            "延庆县",
            "天津市",
            "和平区",
            "河东区",
            "河西区",
            "南开区",
            "河北区",
            "红桥区",
            "东丽区",
            "西青区",
            "津南区",
            "北辰区",
            "武清区",
            "宝坻区",
            "滨海新区",
            "宁河县",
            "静海县",
            "蓟县",
            "河北省",
            "石家庄市",
            "长安区",
            "桥东区",
            "桥西区",
            "新华区",
            "井陉矿区",
            "裕华区",
            "井陉县",
            "正定县",
            "栾城县",
            "行唐县",
            "灵寿县",
            "高邑县",
            "深泽县",
            "赞皇县",
            "无极县",
            "平山县",
            "元氏县",
            "赵县",
            "辛集市",
            "藁城市",
            "晋州市",
            "新乐市",
            "鹿泉市",
            "唐山市",
            "路南区",
            "路北区",
            "古冶区",
            "开平区",
            "丰南区",
            "丰润区",
            "曹妃甸区",
            "滦县",
            "滦南县",
            "乐亭县",
            "迁西县",
            "玉田县",
            "遵化市",
            "迁安市",
            "秦皇岛市",
            "海港区",
            "山海关区",
            "北戴河区",
            "青龙满族自治县",
            "昌黎县",
            "抚宁县",
            "卢龙县",
            "邯郸市",
            "邯山区",
            "丛台区",
            "复兴区",
            "峰峰矿区",
            "邯郸县",
            "临漳县",
            "成安县",
            "大名县",
            "涉县",
            "磁县",
            "肥乡县",
            "永年县",
            "邱县",
            "鸡泽县",
            "广平县",
            "馆陶县",
            "魏县",
            "曲周县",
            "武安市",
            "邢台市",
            "桥东区",
            "桥西区",
            "邢台县",
            "临城县",
            "内丘县",
            "柏乡县",
            "隆尧县",
            "任县",
            "南和县",
            "宁晋县",
            "巨鹿县",
            "新河县",
            "广宗县",
            "平乡县",
            "威县",
            "清河县",
            "临西县",
            "南宫市",
            "沙河市",
            "保定市",
            "新市区",
            "北市区",
            "南市区",
            "满城县",
            "清苑县",
            "涞水县",
            "阜平县",
            "徐水县",
            "定兴县",
            "唐县",
            "高阳县",
            "容城县",
            "涞源县",
            "望都县",
            "安新县",
            "易县",
            "曲阳县",
            "蠡县",
            "顺平县",
            "博野县",
            "雄县",
            "涿州市",
            "定州市",
            "安国市",
            "高碑店市",
            "张家口市",
            "桥东区",
            "桥西区",
            "宣化区",
            "下花园区",
            "宣化县",
            "张北县",
            "康保县",
            "沽源县",
            "尚义县",
            "蔚县",
            "阳原县",
            "怀安县",
            "万全县",
            "怀来县",
            "涿鹿县",
            "赤城县",
            "崇礼县",
            "承德市",
            "台湾省",
            "香港特别行政区",
            "澳门特别行政区"
    };
}
