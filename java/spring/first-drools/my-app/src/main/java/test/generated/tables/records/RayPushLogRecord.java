/*
 * This file is generated by jOOQ.
*/
package test.generated.tables.records;


import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record21;
import org.jooq.Row21;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.ULong;

import test.generated.tables.RayPushLog;


/**
 * 进入推送页面及推送的日志
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class RayPushLogRecord extends UpdatableRecordImpl<RayPushLogRecord> implements Record21<ULong, Timestamp, Timestamp, String, String, String, String, ULong, ULong, String, String, String, String, String, String, ULong, String, String, String, String, String> {

    private static final long serialVersionUID = -911018457;

    /**
     * Setter for <code>SMETA_APP.ray_push_log.id</code>. 主键
     */
    public void setId(ULong value) {
        set(0, value);
    }

    /**
     * Getter for <code>SMETA_APP.ray_push_log.id</code>. 主键
     */
    public ULong getId() {
        return (ULong) get(0);
    }

    /**
     * Setter for <code>SMETA_APP.ray_push_log.gmt_create</code>. 创建时间
     */
    public void setGmtCreate(Timestamp value) {
        set(1, value);
    }

    /**
     * Getter for <code>SMETA_APP.ray_push_log.gmt_create</code>. 创建时间
     */
    public Timestamp getGmtCreate() {
        return (Timestamp) get(1);
    }

    /**
     * Setter for <code>SMETA_APP.ray_push_log.gmt_modified</code>. 修改时间
     */
    public void setGmtModified(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>SMETA_APP.ray_push_log.gmt_modified</code>. 修改时间
     */
    public Timestamp getGmtModified() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>SMETA_APP.ray_push_log.biz_id</code>. 创建者唯一标识符
     */
    public void setBizId(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>SMETA_APP.ray_push_log.biz_id</code>. 创建者唯一标识符
     */
    public String getBizId() {
        return (String) get(3);
    }

    /**
     * Setter for <code>SMETA_APP.ray_push_log.tenant_code</code>. 租户id
     */
    public void setTenantCode(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>SMETA_APP.ray_push_log.tenant_code</code>. 租户id
     */
    public String getTenantCode() {
        return (String) get(4);
    }

    /**
     * Setter for <code>SMETA_APP.ray_push_log.standard_check_result</code>. 标准化检测的结果
     */
    public void setStandardCheckResult(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>SMETA_APP.ray_push_log.standard_check_result</code>. 标准化检测的结果
     */
    public String getStandardCheckResult() {
        return (String) get(5);
    }

    /**
     * Setter for <code>SMETA_APP.ray_push_log.feature_info</code>. 特征详细描述json
     */
    public void setFeatureInfo(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>SMETA_APP.ray_push_log.feature_info</code>. 特征详细描述json
     */
    public String getFeatureInfo() {
        return (String) get(6);
    }

    /**
     * Setter for <code>SMETA_APP.ray_push_log.preheat_id</code>. 联合唯一主键
     */
    public void setPreheatId(ULong value) {
        set(7, value);
    }

    /**
     * Getter for <code>SMETA_APP.ray_push_log.preheat_id</code>. 联合唯一主键
     */
    public ULong getPreheatId() {
        return (ULong) get(7);
    }

    /**
     * Setter for <code>SMETA_APP.ray_push_log.pai_exp_id</code>. 联合唯一主键
     */
    public void setPaiExpId(ULong value) {
        set(8, value);
    }

    /**
     * Getter for <code>SMETA_APP.ray_push_log.pai_exp_id</code>. 联合唯一主键
     */
    public ULong getPaiExpId() {
        return (ULong) get(8);
    }

    /**
     * Setter for <code>SMETA_APP.ray_push_log.pai_exp_name</code>. 实验名称
     */
    public void setPaiExpName(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>SMETA_APP.ray_push_log.pai_exp_name</code>. 实验名称
     */
    public String getPaiExpName() {
        return (String) get(9);
    }

    /**
     * Setter for <code>SMETA_APP.ray_push_log.xflow</code>. xflow文件地址
     */
    public void setXflow(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>SMETA_APP.ray_push_log.xflow</code>. xflow文件地址
     */
    public String getXflow() {
        return (String) get(10);
    }

    /**
     * Setter for <code>SMETA_APP.ray_push_log.feature_push_info</code>. 每个变量的推送明细json
     */
    public void setFeaturePushInfo(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>SMETA_APP.ray_push_log.feature_push_info</code>. 每个变量的推送明细json
     */
    public String getFeaturePushInfo() {
        return (String) get(11);
    }

    /**
     * Setter for <code>SMETA_APP.ray_push_log.feature_push_status</code>. 总体推送进度
     */
    public void setFeaturePushStatus(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>SMETA_APP.ray_push_log.feature_push_status</code>. 总体推送进度
     */
    public String getFeaturePushStatus() {
        return (String) get(12);
    }

    /**
     * Setter for <code>SMETA_APP.ray_push_log.model_info</code>. 模型详细信息
     */
    public void setModelInfo(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>SMETA_APP.ray_push_log.model_info</code>. 模型详细信息
     */
    public String getModelInfo() {
        return (String) get(13);
    }

    /**
     * Setter for <code>SMETA_APP.ray_push_log.model_push_status</code>. 模型推送状态
     */
    public void setModelPushStatus(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>SMETA_APP.ray_push_log.model_push_status</code>. 模型推送状态
     */
    public String getModelPushStatus() {
        return (String) get(14);
    }

    /**
     * Setter for <code>SMETA_APP.ray_push_log.sql_log_id</code>. 执行sql记录id
     */
    public void setSqlLogId(ULong value) {
        set(15, value);
    }

    /**
     * Getter for <code>SMETA_APP.ray_push_log.sql_log_id</code>. 执行sql记录id
     */
    public ULong getSqlLogId() {
        return (ULong) get(15);
    }

    /**
     * Setter for <code>SMETA_APP.ray_push_log.pai_exp_project</code>. 实验工程
     */
    public void setPaiExpProject(String value) {
        set(16, value);
    }

    /**
     * Getter for <code>SMETA_APP.ray_push_log.pai_exp_project</code>. 实验工程
     */
    public String getPaiExpProject() {
        return (String) get(16);
    }

    /**
     * Setter for <code>SMETA_APP.ray_push_log.emp_id</code>. 创建者工号
     */
    public void setEmpId(String value) {
        set(17, value);
    }

    /**
     * Getter for <code>SMETA_APP.ray_push_log.emp_id</code>. 创建者工号
     */
    public String getEmpId() {
        return (String) get(17);
    }

    /**
     * Setter for <code>SMETA_APP.ray_push_log.standard_check_fail_reason</code>. 标准化检测失败原因
     */
    public void setStandardCheckFailReason(String value) {
        set(18, value);
    }

    /**
     * Getter for <code>SMETA_APP.ray_push_log.standard_check_fail_reason</code>. 标准化检测失败原因
     */
    public String getStandardCheckFailReason() {
        return (String) get(18);
    }

    /**
     * Setter for <code>SMETA_APP.ray_push_log.model_upload_status</code>. 模型文件上传状态
     */
    public void setModelUploadStatus(String value) {
        set(19, value);
    }

    /**
     * Getter for <code>SMETA_APP.ray_push_log.model_upload_status</code>. 模型文件上传状态
     */
    public String getModelUploadStatus() {
        return (String) get(19);
    }

    /**
     * Setter for <code>SMETA_APP.ray_push_log.push_type</code>. 推送类型
     */
    public void setPushType(String value) {
        set(20, value);
    }

    /**
     * Getter for <code>SMETA_APP.ray_push_log.push_type</code>. 推送类型
     */
    public String getPushType() {
        return (String) get(20);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<ULong> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record21 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row21<ULong, Timestamp, Timestamp, String, String, String, String, ULong, ULong, String, String, String, String, String, String, ULong, String, String, String, String, String> fieldsRow() {
        return (Row21) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row21<ULong, Timestamp, Timestamp, String, String, String, String, ULong, ULong, String, String, String, String, String, String, ULong, String, String, String, String, String> valuesRow() {
        return (Row21) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field1() {
        return RayPushLog.RAY_PUSH_LOG.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field2() {
        return RayPushLog.RAY_PUSH_LOG.GMT_CREATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return RayPushLog.RAY_PUSH_LOG.GMT_MODIFIED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return RayPushLog.RAY_PUSH_LOG.BIZ_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return RayPushLog.RAY_PUSH_LOG.TENANT_CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return RayPushLog.RAY_PUSH_LOG.STANDARD_CHECK_RESULT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return RayPushLog.RAY_PUSH_LOG.FEATURE_INFO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field8() {
        return RayPushLog.RAY_PUSH_LOG.PREHEAT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field9() {
        return RayPushLog.RAY_PUSH_LOG.PAI_EXP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return RayPushLog.RAY_PUSH_LOG.PAI_EXP_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return RayPushLog.RAY_PUSH_LOG.XFLOW;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field12() {
        return RayPushLog.RAY_PUSH_LOG.FEATURE_PUSH_INFO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field13() {
        return RayPushLog.RAY_PUSH_LOG.FEATURE_PUSH_STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field14() {
        return RayPushLog.RAY_PUSH_LOG.MODEL_INFO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field15() {
        return RayPushLog.RAY_PUSH_LOG.MODEL_PUSH_STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ULong> field16() {
        return RayPushLog.RAY_PUSH_LOG.SQL_LOG_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field17() {
        return RayPushLog.RAY_PUSH_LOG.PAI_EXP_PROJECT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field18() {
        return RayPushLog.RAY_PUSH_LOG.EMP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field19() {
        return RayPushLog.RAY_PUSH_LOG.STANDARD_CHECK_FAIL_REASON;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field20() {
        return RayPushLog.RAY_PUSH_LOG.MODEL_UPLOAD_STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field21() {
        return RayPushLog.RAY_PUSH_LOG.PUSH_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ULong component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component2() {
        return getGmtCreate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component3() {
        return getGmtModified();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getBizId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getTenantCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getStandardCheckResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getFeatureInfo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ULong component8() {
        return getPreheatId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ULong component9() {
        return getPaiExpId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component10() {
        return getPaiExpName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component11() {
        return getXflow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component12() {
        return getFeaturePushInfo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component13() {
        return getFeaturePushStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component14() {
        return getModelInfo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component15() {
        return getModelPushStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ULong component16() {
        return getSqlLogId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component17() {
        return getPaiExpProject();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component18() {
        return getEmpId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component19() {
        return getStandardCheckFailReason();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component20() {
        return getModelUploadStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component21() {
        return getPushType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ULong value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value2() {
        return getGmtCreate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value3() {
        return getGmtModified();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getBizId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getTenantCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getStandardCheckResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getFeatureInfo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ULong value8() {
        return getPreheatId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ULong value9() {
        return getPaiExpId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getPaiExpName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getXflow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value12() {
        return getFeaturePushInfo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value13() {
        return getFeaturePushStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value14() {
        return getModelInfo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value15() {
        return getModelPushStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ULong value16() {
        return getSqlLogId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value17() {
        return getPaiExpProject();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value18() {
        return getEmpId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value19() {
        return getStandardCheckFailReason();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value20() {
        return getModelUploadStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value21() {
        return getPushType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RayPushLogRecord value1(ULong value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RayPushLogRecord value2(Timestamp value) {
        setGmtCreate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RayPushLogRecord value3(Timestamp value) {
        setGmtModified(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RayPushLogRecord value4(String value) {
        setBizId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RayPushLogRecord value5(String value) {
        setTenantCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RayPushLogRecord value6(String value) {
        setStandardCheckResult(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RayPushLogRecord value7(String value) {
        setFeatureInfo(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RayPushLogRecord value8(ULong value) {
        setPreheatId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RayPushLogRecord value9(ULong value) {
        setPaiExpId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RayPushLogRecord value10(String value) {
        setPaiExpName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RayPushLogRecord value11(String value) {
        setXflow(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RayPushLogRecord value12(String value) {
        setFeaturePushInfo(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RayPushLogRecord value13(String value) {
        setFeaturePushStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RayPushLogRecord value14(String value) {
        setModelInfo(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RayPushLogRecord value15(String value) {
        setModelPushStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RayPushLogRecord value16(ULong value) {
        setSqlLogId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RayPushLogRecord value17(String value) {
        setPaiExpProject(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RayPushLogRecord value18(String value) {
        setEmpId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RayPushLogRecord value19(String value) {
        setStandardCheckFailReason(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RayPushLogRecord value20(String value) {
        setModelUploadStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RayPushLogRecord value21(String value) {
        setPushType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RayPushLogRecord values(ULong value1, Timestamp value2, Timestamp value3, String value4, String value5, String value6, String value7, ULong value8, ULong value9, String value10, String value11, String value12, String value13, String value14, String value15, ULong value16, String value17, String value18, String value19, String value20, String value21) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        value13(value13);
        value14(value14);
        value15(value15);
        value16(value16);
        value17(value17);
        value18(value18);
        value19(value19);
        value20(value20);
        value21(value21);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached RayPushLogRecord
     */
    public RayPushLogRecord() {
        super(RayPushLog.RAY_PUSH_LOG);
    }

    /**
     * Create a detached, initialised RayPushLogRecord
     */
    public RayPushLogRecord(ULong id, Timestamp gmtCreate, Timestamp gmtModified, String bizId, String tenantCode, String standardCheckResult, String featureInfo, ULong preheatId, ULong paiExpId, String paiExpName, String xflow, String featurePushInfo, String featurePushStatus, String modelInfo, String modelPushStatus, ULong sqlLogId, String paiExpProject, String empId, String standardCheckFailReason, String modelUploadStatus, String pushType) {
        super(RayPushLog.RAY_PUSH_LOG);

        set(0, id);
        set(1, gmtCreate);
        set(2, gmtModified);
        set(3, bizId);
        set(4, tenantCode);
        set(5, standardCheckResult);
        set(6, featureInfo);
        set(7, preheatId);
        set(8, paiExpId);
        set(9, paiExpName);
        set(10, xflow);
        set(11, featurePushInfo);
        set(12, featurePushStatus);
        set(13, modelInfo);
        set(14, modelPushStatus);
        set(15, sqlLogId);
        set(16, paiExpProject);
        set(17, empId);
        set(18, standardCheckFailReason);
        set(19, modelUploadStatus);
        set(20, pushType);
    }
}
