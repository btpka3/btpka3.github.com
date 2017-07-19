package me.test.first.chanpay.api.agency;

import me.test.first.chanpay.api.*;

/**
 * 畅捷支付 - 代收付。
 *
 * 参考 : 《代收付产品接口说明书》
 */
public interface CpAgencyApi extends CpApi {


    String S_trade = "cjt_dsf";
    String S_asyncTrade = "cjt_dsf";

    // 4.2.3 同步单笔代付
    void payTrade();

    // 4.2.4 异步单笔代付
    void asyncPayTrade();


    // 4.2.5 批量代付加急
    void urgentPayTrades();

    // 4.2.6 批量代付非加急
    void payTrades();

    // 4.2.7 同步单笔代扣
    void withholdTrade();

    // 4.2.8 异步单笔代扣
    void asyncQithholdTrade();


    // 4.2.9 批量代扣加急
    void urgentWithholdTrades();

    // 4.2.10 批量代扣非加急
    void withholdTrades();

    // 4.2.11 单笔交易查询
    void queryTrade();

    // 4.2.12 批量交易查询接口
    void queryTrades();

    // 4.2.13 商户余额查询
    void queryBalance();

    // 4.2.14 卡BIN信息查询
    void queryCardBin();

    // 4.2.15 单笔实名认证
    void realNameAuthTrade();

    // 4.2.16 单笔实名认证查询
    void queryRealNameAuthTrade();

    // 4.2.17 批量实名认证
    void realNameAuthTrades();

    // 4.2.18 批量实名认证查询
    void queryRealNameAuthTrades();
}
