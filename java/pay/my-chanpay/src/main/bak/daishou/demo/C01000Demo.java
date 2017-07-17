package com.chanpay.dsf.demo;

import java.util.Map;

import com.chanpay.demo.util.BaseConstant;
import com.chanpay.demo.util.BaseParameter;
import com.chanpay.demo.util.ChanPayUtil;

/**
 * 
 * @ClassName: T00006Demo
 * @Description: 批量交易查询接口 Demo
 * @author yanghta yanghta@chanjet.com
 * @date 2017年5月3日 下午3:53:16
 *
 */
public class C01000Demo extends BaseParameter {

	public void send() {
		Map<String, String> map = this.requestBaseParameter();
		map.put("TransCode", "C01000");
		map.put("OutTradeNo", ChanPayUtil.generateOutTradeNo());
		map.put("OriOutTradeNo", "20170602173514860988113");
		map.put("BeginIdx", "1");
		map.put("QueryNum", "5");
		map.put("DetailNo", "");
		ChanPayUtil.sendPost(map, BaseConstant.CHARSET,
				BaseConstant.MERCHANT_PRIVATE_KEY);
	}
	
	@org.junit.Test
	public void testSend() {
		C01000Demo demo = new C01000Demo();
		demo.send();
	}	

}
