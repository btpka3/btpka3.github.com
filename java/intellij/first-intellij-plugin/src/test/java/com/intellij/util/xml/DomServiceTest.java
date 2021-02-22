package com.intellij.util.xml;

import com.intellij.openapi.project.Project;
import com.intellij.psi.search.GlobalSearchScope;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author dangqian.zll
 * @date 2021/2/19
 */
public class DomServiceTest {


    @Test
    public void test01() {

        Project project = null;
        // 当前项目的所有元素 mapper, 分别填入类型, 作用域 GlobalSearchScope
        List<DomFileElement<Mapper>> fileElements = DomService.getInstance()
                .getFileElements(Mapper.class, project, GlobalSearchScope.allScope(project));

    }

    public interface Mapper extends DomElement {

        /**
         * namespace
         *
         * @return
         */
        @Attribute("namespace")
        GenericAttributeValue<String> getNamespace();
//
//        /**
//         * 增删改查对应的节点
//         *
//         * @return
//         */
//        @SubTagsList({"select", "insert", "update", "delete"})
//        List<Statement> getStatements();
//
//        @SubTagList("select")
//        List<Select> getSelects();
//
//        @SubTagList("insert")
//        List<Insert> getInserts();
//
//        @SubTagList("update")
//        List<Update> getUpdates();
//
//        @SubTagList("delete")
//        List<Delete> getDeletes();

    }


}
