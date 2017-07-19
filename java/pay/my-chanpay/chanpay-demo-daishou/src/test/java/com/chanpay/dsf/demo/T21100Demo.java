package com.chanpay.dsf.demo;

import java.util.Map;

import com.chanpay.demo.util.BaseConstant;
import com.chanpay.demo.util.BaseParameter;
import com.chanpay.demo.util.ChanPayUtil;

/**
 * 
 * @ClassName: T21100Demo
 * @Description: 批量收款接口 Demo
 * @author yanghta yanghta@chanjet.com
 * @date 2017年5月3日 下午3:53:16
 *
 */
public class T21100Demo extends BaseParameter {

	public void send() {
		Map<String, String> map = this.requestBaseParameter();
		map.put("TransCode", "T21100"); // 交易码
		map.put("OutTradeNo", ChanPayUtil.generateOutTradeNo()); // 商户网站唯一订单号
		map.put("CorpAcctNo", "62233333333333"); // 企业账号
		map.put("BusinessType", "0"); // 业务类型
		map.put("CorpCheckNo", "201703061413");
		map.put("CorpPushUrl", "http://172.20.11.16");
		map.put("TotalNum", "1");
		map.put("TotalAmt", "6");
		map.put("ProdInfoList",
				"[{'DetailNo':'2017050810382739288745404629','TransAmt':'6','BankCommonName':'中国建设银行','AccountType':'00','AcctNo':'62170042600000000','AcctName':'李四','Province':'甘肃省','City':'兰州市','BranchBankName':'中国建设银行股份有限公司兰州新港城支行','BranchBankCode':'105821005604','DrctBankCode':'105821005604','Currency':'CNY','ProtocolNo':'123','LiceneceType':'01','LiceneceNo':'600000201703160001','Phone':'18219910000','AcctExp':'','AcctCvv2':'','Summary':'备注','Postscript':'用途'}]");

		ChanPayUtil.sendPost(map, BaseConstant.CHARSET,
				BaseConstant.MERCHANT_PRIVATE_KEY);
	}
	
	@org.junit.Test
	public void testSend() {
		T21100Demo demo = new T21100Demo();
		demo.send();
	}		

}
