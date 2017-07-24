package me.test.first.chanpay.api.scan.dto;

import me.test.first.chanpay.api.scan.*;

import javax.annotation.*;
import javax.xml.bind.annotation.*;
import java.util.*;

/**
 *
 */
public class GetDailyRefundFileReq extends Req {

    public GetDailyRefundFileReq() {
        this.setService(CpScanApi.S_getDailyRefundFile);
    }
    /**
     * 交易日期
     */
    @Nonnull
    private Date transDate;

    // ------------------------------------ getter && setter

    @Nonnull
    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(@Nonnull Date transDate) {
        this.transDate = transDate;
    }


    // ------------------------------------ equals && hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GetDailyRefundFileReq that = (GetDailyRefundFileReq) o;
        return Objects.equals(transDate, that.transDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), transDate);
    }
}