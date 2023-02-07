package com.cc.oem.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cc.oem.common.redis.RedisClient;

/**
 * @author songb
 * @since  2018年6月25日
 *
 */
@Component
public class OrderNoUtil {
	
	@Autowired
	private RedisClient redisClient;
	
	/**
	 * 网站平台编号
	 */
	private String webPlatform = "1";
	
	/**
	 * 产品平台编号
	 */
	private String productPlatform = "13";
	
	/**
	 * 日期格式化 string
	 */
	private String dateFormatStr = "yyMMdd";
	
	/**
	 * 生成订单号
	 * @param type
	 * @return
	 */
	public String getOrderNoStr(ProductType type) {
		StringBuffer str = new StringBuffer(webPlatform);
		str.append(productPlatform);
		str.append(type.getProductCode());
		str.append(DateTimeUtil.getCurDateTime(dateFormatStr));
		long dailySerialNo = redisClient.incr(RedisKeys.getOrderNoIncrKey(type.getProductCode()));
		str.append(fillFixLength(dailySerialNo));
		return str.toString();
	}
	
	/**
	 * 拼装固定长度的字符串
	 * @param value
	 * @return
	 */
	private String fillFixLength(long value) {
		if(value >= 100000) {
			throw new IllegalArgumentException("generated order no is too big");
		}
		String numStr = String.valueOf(value);
		if(numStr.length() == 5) {
			return numStr;
		}else {
			StringBuffer str = new StringBuffer();
			for(int i = 0;i<5-numStr.length();i++) {
				str.append("0");
			}
			str.append(numStr);
			return str.toString();
		}
		
	}
	
	/**
	 * 产品类型枚举
	 * @author songb
	 * @since  2018年6月26日
	 *
	 */
	public enum ProductType{
		WanShu("142","创蓝万数")
		;
		/**
		 * 产品代码
		 */
		private String productCode;
		
		/**
		 * 产品描叙
		 */
		private String productDesc;
		
		/**
		 * private constructor
		 * @param productCode
		 * @param productDesc
		 */
		private ProductType(String productCode,String productDesc) {
			this.productCode = productCode;
			this.productDesc = productDesc;
		}
		
		/**
		 * get product code
		 * @return
		 */
		public String getProductCode() {
			return this.productCode;
		}
		
		public String getProductDesc() {
			return this.productDesc;
		}
	}

}
