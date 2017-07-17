package com.chanpay.dsf.demo;

import java.util.Map;

import com.chanpay.demo.util.BaseConstant;
import com.chanpay.demo.util.BaseParameter;
import com.chanpay.demo.util.ChanPayUtil;

/**
 * 
 * @ClassName: T00005Demo
 * @Description: 同步单笔实名认证接口 Demo
 * @author yanghta yanghta@chanjet.com
 * @date 2017年5月3日 下午3:53:16
 *
 */
public class T00005Demo extends BaseParameter {

	public void send() {
		Map<String, String> map = this.requestBaseParameter();
		map.put("TransCode", "T00005");
		map.put("OutTradeNo", ChanPayUtil.generateOutTradeNo());
		map.put("BankCommonName", "中国银行");
		map.put("BankName", "中国银行海淀支行");
		map.put("BankCode", "00000");
		map.put("AccountType", "00");
		map.put("AcctNo", ChanPayUtil.encrypt("6214920205614264",
				BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET));
		map.put("AcctName", ChanPayUtil.encrypt("胡乃辉",
				BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET));
		map.put("LiceneceType", "01");
		map.put("LiceneceNo", ChanPayUtil.encrypt("370628197706227018",
				BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET));
		map.put("Phone", ChanPayUtil.encrypt("13810509999",
				BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET));
		map.put("AcctExp", "");
		map.put("AcctCvv2", ChanPayUtil.encrypt("",
				BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET));

		ChanPayUtil.sendPost(map, BaseConstant.CHARSET,
				BaseConstant.MERCHANT_PRIVATE_KEY);
	}

	@org.junit.Test
	public void testSend() {
		T00005Demo demo = new T00005Demo();
		demo.send();
	}

}
