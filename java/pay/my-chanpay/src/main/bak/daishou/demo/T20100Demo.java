package com.chanpay.dsf.demo;

import java.util.Map;

import com.chanpay.demo.util.BaseConstant;
import com.chanpay.demo.util.BaseParameter;
import com.chanpay.demo.util.ChanPayUtil;

/**
 * 
 * @ClassName: T20100Demo
 * @Description: 异步单笔代付接口 Demo
 * @author yanghta yanghta@chanjet.com
 * @date 2017年5月3日 下午3:53:16
 *
 */
public class T20100Demo extends BaseParameter {

	public void send() {
		Map<String, String> map = this.requestBaseParameter();
		map.put("TransCode", "T20100"); // 交易码
		map.put("OutTradeNo", ChanPayUtil.generateOutTradeNo()); // 商户网站唯一订单号
		map.put("CorpAcctNo", "62233333333333"); // 企业账号
		map.put("BusinessType", "0"); // 业务类型
		map.put("ProtocolNo", "12345"); // 协议号
		map.put("BankCommonName", "中国银行"); // 通用银行名称
		map.put("AccountType", "00"); // 账户类型
		
		//**************  银行四要素信息需要用真实数据  *******************
		
		map.put("AcctNo", ChanPayUtil.encrypt("6217000010000000000",
				BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET)); // 对手人账号
		map.put("AcctName", ChanPayUtil.encrypt("畅捷支付",
				BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET)); // 对手人账户名称
		map.put("Province", "甘肃省"); // 省份信息
		map.put("City", "兰州市"); // 城市信息
//		map.put("BranchBankName", "中国建设银行股份有限公司兰州新港城支行"); // 对手行行名
//		map.put("BranchBankCode", "105821005604");
//		map.put("DrctBankCode", "105821005604");
		map.put("Currency", "CNY");
		map.put("TransAmt", "1");
		map.put("LiceneceType", "01");
		map.put("LiceneceNo", ChanPayUtil.encrypt("130103190000000000",
				BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET));
		map.put("Phone", ChanPayUtil.encrypt("13800000000",
				BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET));
		map.put("AcctExp", "exp");
		map.put("AcctCvv2", ChanPayUtil.encrypt("cvv",
				BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET));
		map.put("CorpCheckNo", "201703061413");
		map.put("Summary", "");
		map.put("CorpPushUrl", "http://172.20.11.16");
		map.put("PostScript", "用途");

		ChanPayUtil.sendPost(map, BaseConstant.CHARSET,
				BaseConstant.MERCHANT_PRIVATE_KEY);
	}

	@org.junit.Test
	public void testSend() {
		T20100Demo demo = new T20100Demo();
		demo.send();
	}

}
