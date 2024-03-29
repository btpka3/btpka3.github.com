/*
 * This file is generated by jOOQ.
*/
package test.generated.tables.records;


import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;

import test.generated.tables.MAction;


/**
 * 处置动作表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MActionRecord extends UpdatableRecordImpl<MActionRecord> {

    private static final long serialVersionUID = 1264659182;

    /**
     * Setter for <code>SMETA_APP.m_action.id</code>. id
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>SMETA_APP.m_action.id</code>. id
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>SMETA_APP.m_action.code</code>. 动作名称、code
     */
    public void setCode(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>SMETA_APP.m_action.code</code>. 动作名称、code
     */
    public String getCode() {
        return (String) get(1);
    }

    /**
     * Setter for <code>SMETA_APP.m_action.friendlyname</code>. 友好的名称
     */
    public void setFriendlyname(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>SMETA_APP.m_action.friendlyname</code>. 友好的名称
     */
    public String getFriendlyname() {
        return (String) get(2);
    }

    /**
     * Setter for <code>SMETA_APP.m_action.catalogcode</code>. 类别
     */
    public void setCatalogcode(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>SMETA_APP.m_action.catalogcode</code>. 类别
     */
    public String getCatalogcode() {
        return (String) get(3);
    }

    /**
     * Setter for <code>SMETA_APP.m_action.catalogname</code>. 类别名称 2012-08-09
     */
    public void setCatalogname(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>SMETA_APP.m_action.catalogname</code>. 类别名称 2012-08-09
     */
    public String getCatalogname() {
        return (String) get(4);
    }

    /**
     * Setter for <code>SMETA_APP.m_action.returntype</code>. 返回值类型，注意要能在meta里找到，以实现自动提示
     */
    public void setReturntype(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>SMETA_APP.m_action.returntype</code>. 返回值类型，注意要能在meta里找到，以实现自动提示
     */
    public String getReturntype() {
        return (String) get(5);
    }

    /**
     * Setter for <code>SMETA_APP.m_action.comment</code>. 动作功能说明，返回值说明
     */
    public void setComment(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>SMETA_APP.m_action.comment</code>. 动作功能说明，返回值说明
     */
    public String getComment() {
        return (String) get(6);
    }

    /**
     * Setter for <code>SMETA_APP.m_action.args</code>. 动作参数说明，要包括动作参数名称、类型、参数说明
     */
    public void setArgs(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>SMETA_APP.m_action.args</code>. 动作参数说明，要包括动作参数名称、类型、参数说明
     */
    public String getArgs() {
        return (String) get(7);
    }

    /**
     * Setter for <code>SMETA_APP.m_action.announcement</code>. 该动作注意事项
     */
    public void setAnnouncement(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>SMETA_APP.m_action.announcement</code>. 该动作注意事项
     */
    public String getAnnouncement() {
        return (String) get(8);
    }

    /**
     * Setter for <code>SMETA_APP.m_action.metadata</code>. 动作元数据，比如用到了哪些组件
     */
    public void setMetadata(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>SMETA_APP.m_action.metadata</code>. 动作元数据，比如用到了哪些组件
     */
    public String getMetadata() {
        return (String) get(9);
    }

    /**
     * Setter for <code>SMETA_APP.m_action.initializationneeded</code>. 是否需要初始化，一般脚本动作则需要
     */
    public void setInitializationneeded(Integer value) {
        set(10, value);
    }

    /**
     * Getter for <code>SMETA_APP.m_action.initializationneeded</code>. 是否需要初始化，一般脚本动作则需要
     */
    public Integer getInitializationneeded() {
        return (Integer) get(10);
    }

    /**
     * Setter for <code>SMETA_APP.m_action.initializationinfo</code>. 初始化信息，比如何种脚本，如何初始化
     */
    public void setInitializationinfo(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>SMETA_APP.m_action.initializationinfo</code>. 初始化信息，比如何种脚本，如何初始化
     */
    public String getInitializationinfo() {
        return (String) get(11);
    }

    /**
     * Setter for <code>SMETA_APP.m_action.cacheincluded</code>. 是否包含缓存
     */
    public void setCacheincluded(Integer value) {
        set(12, value);
    }

    /**
     * Getter for <code>SMETA_APP.m_action.cacheincluded</code>. 是否包含缓存
     */
    public Integer getCacheincluded() {
        return (Integer) get(12);
    }

    /**
     * Setter for <code>SMETA_APP.m_action.createtime</code>. 创建时间
     */
    public void setCreatetime(Timestamp value) {
        set(13, value);
    }

    /**
     * Getter for <code>SMETA_APP.m_action.createtime</code>. 创建时间
     */
    public Timestamp getCreatetime() {
        return (Timestamp) get(13);
    }

    /**
     * Setter for <code>SMETA_APP.m_action.creator</code>. 添加人
     */
    public void setCreator(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>SMETA_APP.m_action.creator</code>. 添加人
     */
    public String getCreator() {
        return (String) get(14);
    }

    /**
     * Setter for <code>SMETA_APP.m_action.body</code>. 动作体，用于基本动作，需要初始化
     */
    public void setBody(String value) {
        set(15, value);
    }

    /**
     * Getter for <code>SMETA_APP.m_action.body</code>. 动作体，用于基本动作，需要初始化
     */
    public String getBody() {
        return (String) get(15);
    }

    /**
     * Setter for <code>SMETA_APP.m_action.lastupdatetime</code>. 最后修改时间
     */
    public void setLastupdatetime(Timestamp value) {
        set(16, value);
    }

    /**
     * Getter for <code>SMETA_APP.m_action.lastupdatetime</code>. 最后修改时间
     */
    public Timestamp getLastupdatetime() {
        return (Timestamp) get(16);
    }

    /**
     * Setter for <code>SMETA_APP.m_action.lastoperator</code>. 最后操作人
     */
    public void setLastoperator(String value) {
        set(17, value);
    }

    /**
     * Getter for <code>SMETA_APP.m_action.lastoperator</code>. 最后操作人
     */
    public String getLastoperator() {
        return (String) get(17);
    }

    /**
     * Setter for <code>SMETA_APP.m_action.editor</code>. 2013-02-27 何种编辑器编辑的
     */
    public void setEditor(String value) {
        set(18, value);
    }

    /**
     * Getter for <code>SMETA_APP.m_action.editor</code>. 2013-02-27 何种编辑器编辑的
     */
    public String getEditor() {
        return (String) get(18);
    }

    /**
     * Setter for <code>SMETA_APP.m_action.authorizationcode</code>. 授权码 2013-03-05
     */
    public void setAuthorizationcode(String value) {
        set(19, value);
    }

    /**
     * Getter for <code>SMETA_APP.m_action.authorizationcode</code>. 授权码 2013-03-05
     */
    public String getAuthorizationcode() {
        return (String) get(19);
    }

    /**
     * Setter for <code>SMETA_APP.m_action.status</code>. 状态,0表示正常,-100表示废弃
     */
    public void setStatus(Integer value) {
        set(20, value);
    }

    /**
     * Getter for <code>SMETA_APP.m_action.status</code>. 状态,0表示正常,-100表示废弃
     */
    public Integer getStatus() {
        return (Integer) get(20);
    }

    /**
     * Setter for <code>SMETA_APP.m_action.testcasescript</code>. 测试用例脚本
     */
    public void setTestcasescript(String value) {
        set(21, value);
    }

    /**
     * Getter for <code>SMETA_APP.m_action.testcasescript</code>. 测试用例脚本
     */
    public String getTestcasescript() {
        return (String) get(21);
    }

    /**
     * Setter for <code>SMETA_APP.m_action.tags</code>. 标记 2014-01-23
     */
    public void setTags(String value) {
        set(22, value);
    }

    /**
     * Getter for <code>SMETA_APP.m_action.tags</code>. 标记 2014-01-23
     */
    public String getTags() {
        return (String) get(22);
    }

    /**
     * Setter for <code>SMETA_APP.m_action.weight</code>. 权重 2014-05-12
     */
    public void setWeight(Integer value) {
        set(23, value);
    }

    /**
     * Getter for <code>SMETA_APP.m_action.weight</code>. 权重 2014-05-12
     */
    public Integer getWeight() {
        return (Integer) get(23);
    }

    /**
     * Setter for <code>SMETA_APP.m_action.snapshot</code>. 动作快照
     */
    public void setSnapshot(String value) {
        set(24, value);
    }

    /**
     * Getter for <code>SMETA_APP.m_action.snapshot</code>. 动作快照
     */
    public String getSnapshot() {
        return (String) get(24);
    }

    /**
     * Setter for <code>SMETA_APP.m_action.contacter</code>. 动作负责人
     */
    public void setContacter(String value) {
        set(25, value);
    }

    /**
     * Getter for <code>SMETA_APP.m_action.contacter</code>. 动作负责人
     */
    public String getContacter() {
        return (String) get(25);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached MActionRecord
     */
    public MActionRecord() {
        super(MAction.M_ACTION);
    }

    /**
     * Create a detached, initialised MActionRecord
     */
    public MActionRecord(Long id, String code, String friendlyname, String catalogcode, String catalogname, String returntype, String comment, String args, String announcement, String metadata, Integer initializationneeded, String initializationinfo, Integer cacheincluded, Timestamp createtime, String creator, String body, Timestamp lastupdatetime, String lastoperator, String editor, String authorizationcode, Integer status, String testcasescript, String tags, Integer weight, String snapshot, String contacter) {
        super(MAction.M_ACTION);

        set(0, id);
        set(1, code);
        set(2, friendlyname);
        set(3, catalogcode);
        set(4, catalogname);
        set(5, returntype);
        set(6, comment);
        set(7, args);
        set(8, announcement);
        set(9, metadata);
        set(10, initializationneeded);
        set(11, initializationinfo);
        set(12, cacheincluded);
        set(13, createtime);
        set(14, creator);
        set(15, body);
        set(16, lastupdatetime);
        set(17, lastoperator);
        set(18, editor);
        set(19, authorizationcode);
        set(20, status);
        set(21, testcasescript);
        set(22, tags);
        set(23, weight);
        set(24, snapshot);
        set(25, contacter);
    }
}
