
package me.test.service;

import org.springframework.transaction.annotation.Propagation;

public class UpdateRecord {

    /** 记录序号 */
    private int i;

    /** 更新此记录时的应使用的事务传播机制 */
    private Propagation propagation;

    /** 要更新的医院ID */
    private Long hospitalId;

    /** 要更新的用户ID */
    private Long userId;

    /** 要更的评论 */
    private String remark;

    /** 该记录是否应当更新成功 */
    private boolean succeed;

    public UpdateRecord(int i, Propagation propagation, Long hospitalId, Long userId, String remark, boolean succeed) {

        super();
        this.i = i;
        this.propagation = propagation;
        this.hospitalId = hospitalId;
        this.userId = userId;
        this.remark = remark;
        this.succeed = succeed;
    }

    public int getI() {

        return i;
    }

    public Propagation getPropagation() {

        return propagation;
    }

    public Long getHospitalId() {

        return hospitalId;
    }

    public Long getUserId() {

        return userId;
    }

    public String getRemark() {

        return remark;
    }

    public boolean isSucceed() {

        return succeed;
    }

    @Override
    public String toString() {

        return "UpdateRecord [i=" + i + ", propagation=" + propagation + ", hospitalId=" + hospitalId + ", userId=" + userId + ", remark=" + remark + ", succeed=" + succeed + "]";
    }
}
