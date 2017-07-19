package com.chanpay.dsf.demo;

import java.util.Map;

import com.chanpay.demo.util.BaseConstant;
import com.chanpay.demo.util.BaseParameter;
import com.chanpay.demo.util.ChanPayUtil;

/**
 * 
 * @ClassName: C00005Demo
 * @Description: 商户余额查询接口 Demo
 * @author yanghta yanghta@chanjet.com
 * @date 2017年5月3日 下午3:53:16
 *
 */
public class C00005Demo extends BaseParameter {

	public void send() {
		Map<String, String> map = this.requestBaseParameter();
		map.put("TransCode", "C00005");
		map.put("OutTradeNo", ChanPayUtil.generateOutTradeNo());
		map.put("AcctNo", ChanPayUtil.encrypt("200100100120000040005900001",
				BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET));
		map.put("AcctName", ChanPayUtil.encrypt("测试",
				BaseConstant.MERCHANT_PUBLIC_KEY, BaseConstant.CHARSET));
		ChanPayUtil.sendPost(map, BaseConstant.CHARSET,
				BaseConstant.MERCHANT_PRIVATE_KEY);
	}

	@org.junit.Test
	public void testSend() {
		C00005Demo demo = new C00005Demo();
		demo.send();
	}

}
