package com.chanpay.dsf.demo;

import java.util.Map;

import com.chanpay.demo.util.BaseConstant;
import com.chanpay.demo.util.BaseParameter;
import com.chanpay.demo.util.ChanPayUtil;

/**
 * 
 * @ClassName: T00006Demo
 * @Description: 批量实名认证接口 Demo
 * @author yanghta yanghta@chanjet.com
 * @date 2017年5月3日 下午3:53:16
 *
 */
public class T00006Demo extends BaseParameter {

	public void send() {
		Map<String, String> map = this.requestBaseParameter();
		map.put("TransCode", "T00006");
		map.put("OutTradeNo", ChanPayUtil.generateOutTradeNo());
		map.put("ProdInfoList",
				"[{'DetailNo':'2017050519200037784482967810','BankCommonName':'中国银行','BankName':'中国银行海淀支行','BankCode':'00000','AccountType':'00','AcctNo':'6214920205614264','AcctName':'胡乃辉','LiceneceType':'01','LiceneceNo':'370628197706227018','Phone':'13810509999','AcctExp':'','AcctCvv2':''}]");

		ChanPayUtil.sendPost(map, BaseConstant.CHARSET,
				BaseConstant.MERCHANT_PRIVATE_KEY);
	}

	@org.junit.Test
	public void testSend() {
		T00006Demo demo = new T00006Demo();
		demo.send();
	}		

}
