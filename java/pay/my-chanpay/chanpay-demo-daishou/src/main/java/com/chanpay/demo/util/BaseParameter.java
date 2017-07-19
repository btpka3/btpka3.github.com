package com.chanpay.demo.util;

import java.util.HashMap;
import java.util.Map;

public class BaseParameter {

	/**
	 * 请求
	* @Title: requestBaseParam 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return Map<String,String>    返回类型 
	* @throws
	 */
	public Map<String,String> requestBaseParameter(){
		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap.put(BaseConstant.SERVICE, "cjt_dsf");// 鉴权绑卡确认的接口名
		origMap.put(BaseConstant.VERSION, "1.0");
		//origMap.put(BaseConstant.PARTNER_ID, "200000400059"); // Y 畅捷支付分配的商户号
		origMap.put(BaseConstant.PARTNER_ID, "200003660096"); //T
		origMap.put(BaseConstant.TRADE_DATE, BaseConstant.DATE);
		origMap.put(BaseConstant.TRADE_TIME, BaseConstant.TIME);
		origMap.put(BaseConstant.INPUT_CHARSET, BaseConstant.CHARSET);// 字符集
		origMap.put(BaseConstant.MEMO, "");// 备注
		return origMap;
		
	}
	
	public Map<String,String> requestBaseParameter(String service){
		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap.put(BaseConstant.SERVICE, service);// 鉴权绑卡确认的接口名
		origMap.put(BaseConstant.VERSION, "1.0");
		//origMap.put(BaseConstant.PARTNER_ID, "200000400059"); //Y 畅捷支付分配的商户号
		origMap.put(BaseConstant.PARTNER_ID, "200003660096"); //T
		origMap.put(BaseConstant.TRADE_DATE, BaseConstant.DATE);
		origMap.put(BaseConstant.TRADE_TIME, BaseConstant.TIME);
		origMap.put(BaseConstant.INPUT_CHARSET, BaseConstant.CHARSET);// 字符集
		origMap.put(BaseConstant.MEMO, "");// 备注
		return origMap;
		
	}
	
	public Map<String,String> requestBaseParameter(String service,String partnerId){
		Map<String, String> origMap = new HashMap<String, String>();
		// 2.1 基本参数
		origMap.put(BaseConstant.SERVICE, service);// 鉴权绑卡确认的接口名
		origMap.put(BaseConstant.VERSION, "1.0");
		origMap.put(BaseConstant.PARTNER_ID, partnerId); // 畅捷支付分配的商户号
		origMap.put(BaseConstant.TRADE_DATE, BaseConstant.DATE);
		origMap.put(BaseConstant.TRADE_TIME, BaseConstant.TIME);
		origMap.put(BaseConstant.INPUT_CHARSET, BaseConstant.CHARSET);// 字符集
		origMap.put(BaseConstant.MEMO, "");// 备注
		return origMap;
		
	}
}
