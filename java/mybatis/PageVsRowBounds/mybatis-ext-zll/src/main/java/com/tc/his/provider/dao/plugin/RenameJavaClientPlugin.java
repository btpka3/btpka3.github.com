package com.tc.his.provider.dao.plugin;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

public class RenameJavaClientPlugin extends PluginAdapter {
    private String searchString;
    private String replaceString;
    private Pattern pattern;

    /**
     *
     */
    public RenameJavaClientPlugin() {
    }

    public boolean validate(List<String> warnings) {

        searchString = properties.getProperty("searchString"); //$NON-NLS-1$
        replaceString = properties.getProperty("replaceString"); //$NON-NLS-1$

        boolean valid = stringHasValue(searchString) && stringHasValue(replaceString);

        if (valid) {
            pattern = Pattern.compile(searchString);
        } else {
            if (!stringHasValue(searchString)) {
                warnings.add(getString("ValidationError.18", //$NON-NLS-1$
                        "RenameJavaClientPlugin", //$NON-NLS-1$
                        "searchString")); //$NON-NLS-1$
            }
            if (!stringHasValue(replaceString)) {
                warnings.add(getString("ValidationError.18", //$NON-NLS-1$
                        "RenameJavaClientPlugin", //$NON-NLS-1$
                        "replaceString")); //$NON-NLS-1$
            }
        }

        return valid;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        String oldType = introspectedTable.getMyBatis3JavaMapperType();

        introspectedTable.setCountByExampleStatementId("countByCriteria"); //$NON-NLS-1$
        introspectedTable.setDeleteByExampleStatementId("deleteByCriteria"); //$NON-NLS-1$
        introspectedTable.setDeleteByPrimaryKeyStatementId("deleteById"); //$NON-NLS-1$
        introspectedTable.setInsertStatementId("insert"); //$NON-NLS-1$
        introspectedTable.setInsertSelectiveStatementId("insertSelective"); //$NON-NLS-1$
        introspectedTable.setSelectAllStatementId("selectAll"); //$NON-NLS-1$
        introspectedTable.setSelectByExampleStatementId("selectByCriteria"); //$NON-NLS-1$
        introspectedTable.setSelectByExampleWithBLOBsStatementId("selectByCriteriaWithBLOBs"); //$NON-NLS-1$
        introspectedTable.setSelectByPrimaryKeyStatementId("selectById"); //$NON-NLS-1$
        introspectedTable.setUpdateByExampleStatementId("updateByCriteria"); //$NON-NLS-1$
        introspectedTable.setUpdateByExampleSelectiveStatementId("updateByCriteriaSelective"); //$NON-NLS-1$
        introspectedTable.setUpdateByExampleWithBLOBsStatementId("updateByCriteriaWithBLOBs"); //$NON-NLS-1$
        introspectedTable.setUpdateByPrimaryKeyStatementId("updateById"); //$NON-NLS-1$
        introspectedTable.setUpdateByPrimaryKeySelectiveStatementId("updateByIdSelective"); //$NON-NLS-1$
        introspectedTable.setUpdateByPrimaryKeyWithBLOBsStatementId("updateByIdWithBLOBs"); //$NON-NLS-1$
        introspectedTable.setBaseResultMapId("BaseResultMap"); //$NON-NLS-1$
        introspectedTable.setResultMapWithBLOBsId("ResultMapWithBLOBs"); //$NON-NLS-1$
        introspectedTable.setExampleWhereClauseId("Criteria_Where_Clause"); //$NON-NLS-1$
        introspectedTable.setBaseColumnListId("Base_Column_List"); //$NON-NLS-1$
        introspectedTable.setBlobColumnListId("Blob_Column_List"); //$NON-NLS-1$
        introspectedTable.setMyBatis3UpdateByExampleWhereClauseId("Update_By_Criteria_Where_Clause"); //$NON-NLS-1$

        Matcher matcher = pattern.matcher(oldType);
        oldType = matcher.replaceAll(replaceString);

        introspectedTable.setMyBatis3JavaMapperType(oldType);
    }
}
