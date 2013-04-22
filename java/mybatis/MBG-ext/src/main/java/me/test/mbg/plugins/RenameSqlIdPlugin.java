package me.test.mbg.plugins;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

public class RenameSqlIdPlugin extends PluginAdapter {

    public void initialized(IntrospectedTable introspectedTable) {

        introspectedTable.setCountByExampleStatementId("countByCriteria");
        introspectedTable.setDeleteByExampleStatementId("deleteByCriteria");
        introspectedTable.setDeleteByPrimaryKeyStatementId("deleteByPrimaryKey");
        introspectedTable.setInsertStatementId("insert");
        introspectedTable.setInsertSelectiveStatementId("insertSelective");
        introspectedTable.setSelectAllStatementId("selectAll");
        introspectedTable.setSelectByExampleStatementId("selectByCriteria");
        introspectedTable.setSelectByExampleWithBLOBsStatementId("selectByCriteriaWithBLOBs");
        introspectedTable.setSelectByPrimaryKeyStatementId("selectByPrimaryKey");
        introspectedTable.setUpdateByExampleStatementId("updateByCriteria");
        introspectedTable.setUpdateByExampleSelectiveStatementId("updateByCriteriaSelective");
        introspectedTable.setUpdateByExampleWithBLOBsStatementId("updateByCriteriaWithBLOBs");
        introspectedTable.setUpdateByPrimaryKeyStatementId("updateByPrimaryKey");
        introspectedTable.setUpdateByPrimaryKeySelectiveStatementId("updateByPrimaryKeySelective");
        introspectedTable.setUpdateByPrimaryKeyWithBLOBsStatementId("updateByPrimaryKeyWithBLOBs");
        introspectedTable.setBaseResultMapId("BaseResultMap");
        introspectedTable.setResultMapWithBLOBsId("ResultMapWithBLOBs");
        introspectedTable.setExampleWhereClauseId("CriteriaWhereClause");
        introspectedTable.setBaseColumnListId("BaseColumnList");
        introspectedTable.setBlobColumnListId("BlobColumnList");
        introspectedTable.setMyBatis3UpdateByExampleWhereClauseId("UpdateByCriteriaWhereClause");

    }

    public boolean validate(List<String> arg0) {
        return true;
    }

}
