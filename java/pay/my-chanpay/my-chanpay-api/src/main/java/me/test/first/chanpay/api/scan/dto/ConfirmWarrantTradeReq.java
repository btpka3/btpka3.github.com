package me.test.first.chanpay.api.scan.dto;

import me.test.first.chanpay.api.scan.*;

import javax.annotation.*;
import javax.xml.bind.annotation.*;
import java.util.*;

/**
 *
 */
public class ConfirmWarrantTradeReq extends Req {

    public ConfirmWarrantTradeReq() {
        this.setService(CpScanApi.S_confirmWarrantTrade);
    }

    /**
     * 商户唯一订单号
     */
    @Nonnull
    private String trxId;

    /**
     * 担保订单号
     */
    @Nonnull
    private String outTradeNo;

    /**
     * 分润账号集
     */
    private List<Split> royaltyParameters;

    /**
     * 扩展字段
     */
    private String ext;


    public static class Split {

        /**
         * 收方商户号
         */
        @XmlAttribute(name = "UserId")
        private String userId;

        /**
         *
         */
        @XmlAttribute(name = "PID")
        private String pid;

        /**
         *
         */
        @XmlAttribute(name = "AccountType")
        private String accountType;

        /**
         * 分账金额
         */
        @XmlAttribute(name = "Amount")
        private Double amount;

        // ------------------------------------ getter && setter

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getAccountType() {
            return accountType;
        }

        public void setAccountType(String accountType) {
            this.accountType = accountType;
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
                    Objects.equals(pid, split.pid) &&
                    Objects.equals(accountType, split.accountType) &&
                    Objects.equals(amount, split.amount);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userId, pid, accountType, amount);
        }
    }

    // ------------------------------------ getter && setter

    @Nonnull
    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(@Nonnull String trxId) {
        this.trxId = trxId;
    }

    @Nonnull
    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(@Nonnull String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public List<Split> getRoyaltyParameters() {
        return royaltyParameters;
    }

    public void setRoyaltyParameters(List<Split> royaltyParameters) {
        this.royaltyParameters = royaltyParameters;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }


    // ------------------------------------ equals && hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ConfirmWarrantTradeReq that = (ConfirmWarrantTradeReq) o;
        return Objects.equals(trxId, that.trxId) &&
                Objects.equals(outTradeNo, that.outTradeNo) &&
                Objects.equals(royaltyParameters, that.royaltyParameters) &&
                Objects.equals(ext, that.ext);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), trxId, outTradeNo, royaltyParameters, ext);
    }
}
