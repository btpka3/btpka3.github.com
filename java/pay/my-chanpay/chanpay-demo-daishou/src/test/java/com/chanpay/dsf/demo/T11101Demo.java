package com.chanpay.dsf.demo;

import java.util.Map;

import com.chanpay.demo.util.BaseConstant;
import com.chanpay.demo.util.BaseParameter;
import com.chanpay.demo.util.ChanPayUtil;

/**
 * 
 * @ClassName: T11101Demo
 * @Description: 批量文件代付接口 Demo
 * @author yanghta yanghta@chanjet.com
 * @date 2017年5月3日 下午3:53:16
 *
 */
public class T11101Demo extends BaseParameter {

	public void send() {
		Map<String, String> map = this.requestBaseParameter("cjt_dsf_file",
				"200000400059");
		map.put("TransCode", "T11101");
		map.put("OutTradeNo", ChanPayUtil.generateOutTradeNo());
		map.put("CorpAcctNo", "62170000000000000");
		map.put("BusinessType", "0");
		map.put("CorpCheckNo", "201703061413");
		map.put("CorpPushUrl", "http://172.20.11.16");
		map.put("TotalNum", "2");
		map.put("TotalAmt", "301.24");

		ChanPayUtil.sendFilePost(map, BaseConstant.CHARSET,
				BaseConstant.MERCHANT_PRIVATE_KEY, "T11101.txt",
				System.getProperty("user.dir")
						+ "\\src\\com\\chanpay\\dsf\\file\\T11101.txt");
	}

	@org.junit.Test
	public void testSend() {
		T11101Demo demo = new T11101Demo();
		demo.send();
	}

}
