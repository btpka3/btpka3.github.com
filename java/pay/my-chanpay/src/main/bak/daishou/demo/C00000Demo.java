package com.chanpay.dsf.demo;

import java.util.Map;

import com.chanpay.demo.util.BaseConstant;
import com.chanpay.demo.util.BaseParameter;
import com.chanpay.demo.util.ChanPayUtil;

/**
 * 
 * @ClassName: C00000Demo
 * @Description: 单笔交易查询接口 Demo
 * @author yanghta yanghta@chanjet.com
 * @date 2017年5月3日 下午3:53:16
 *
 */
public class C00000Demo extends BaseParameter {

	public void send() {
		Map<String, String> map = this.requestBaseParameter();
		map.put("TransCode", "C00000");

		map.put("OutTradeNo", ChanPayUtil.generateOutTradeNo());
		map.put("OriOutTradeNo", "20170602172243163138262");
		ChanPayUtil.sendPost(map, BaseConstant.CHARSET,
				BaseConstant.MERCHANT_PRIVATE_KEY);
	}

	@org.junit.Test
	public void testSend() {
		C00000Demo demo = new C00000Demo();
		demo.send();
	}

}
