package me.test.first.chanpay.api.scan.dto;

import me.test.first.chanpay.api.scan.*;

import javax.xml.bind.annotation.*;
import java.util.*;

/**
 *
 */
public class RefundReq extends Req {

    public RefundReq() {
        this.setService(CpScanApi.S_refund);
    }


    /**
     * 商户唯一订单号
     */

    private String trxId;

    /**
     * 支付订单号
     */

    private String oriTrxId;

    /**
     * 退款金额
     */

    private Double trxAmt;

    /**
     * 退担保金额
     */
    private Double refundEnsureAmount;

    /**
     * 分润账户集
     */
    private List<Split> royaltyParameters;

    /**
     * 服务器异步通知页面路径
     */
    private String notifyUrl;

    /**
     * 扩展字段
     */
    private String extension;

    public static class Split {

        /**
         * 分账用户标识
         */
        @XmlAttribute(name = "userId")
        private String userId;

        /**
         * 分账用户类型
         */
        @XmlAttribute(name = "account_type")
        private String accountType;


        /**
         * 分账用户类型
         */
        @XmlAttribute(name = "PID")
        private String pid;


        /**
         * 分账金额
         */
        @XmlAttribute(name = "amount")
        private Double amount;

        // ------------------------------------ getter && setter

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getAccountType() {
            return accountType;
        }

        public void setAccountType(String accountType) {
            this.accountType = accountType;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        // ------------------------------------ equals && hashCode


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Split split = (Split) o;
            return Objects.equals(userId, split.userId) &&
                    Objects.equals(accountType, split.accountType) &&
                    Objects.equals(pid, split.pid) &&
                    Objects.equals(amount, split.amount);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, accountType, pid, amount);
        }
    }

    // ------------------------------------ getter && setter


    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId;
    }


    public String getOriTrxId() {
        return oriTrxId;
    }

    public void setOriTrxId(String oriTrxId) {
        this.oriTrxId = oriTrxId;
    }


    public Double getTrxAmt() {
        return trxAmt;
    }

    public void setTrxAmt(Double trxAmt) {
        this.trxAmt = trxAmt;
    }

    public Double getRefundEnsureAmount() {
        return refundEnsureAmount;
    }

    public void setRefundEnsureAmount(Double refundEnsureAmount) {
        this.refundEnsureAmount = refundEnsureAmount;
    }

    public List<Split> getRoyaltyParameters() {
        return royaltyParameters;
    }

    public void setRoyaltyParameters(List<Split> royaltyParameters) {
        this.royaltyParameters = royaltyParameters;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    // ------------------------------------ equals && hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RefundReq refundReq = (RefundReq) o;
        return Objects.equals(trxId, refundReq.trxId) &&
                Objects.equals(oriTrxId, refundReq.oriTrxId) &&
                Objects.equals(trxAmt, refundReq.trxAmt) &&
                Objects.equals(refundEnsureAmount, refundReq.refundEnsureAmount) &&
                Objects.equals(royaltyParameters, refundReq.royaltyParameters) &&
                Objects.equals(notifyUrl, refundReq.notifyUrl) &&
                Objects.equals(extension, refundReq.extension);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), trxId, oriTrxId, trxAmt, refundEnsureAmount, royaltyParameters, notifyUrl, extension);
    }
}
