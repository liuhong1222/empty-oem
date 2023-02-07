package com.cc.oem.modules.agent.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cc.oem.common.redis.RedisClient;
import com.cc.oem.common.utils.CacheConstant;
import com.cc.oem.modules.agent.dao.CustInfoMapper;
import com.cc.oem.modules.agent.entity.CustInfo;
import com.cc.oem.modules.agent.service.RefreshCacheService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 刷新缓存实现类
 * @author liuh
 * @date 2021年11月10日
 */
@Service
public class RefreshCacheServiceImpl implements RefreshCacheService {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisClient redisClient;
    @Autowired
    private CustInfoMapper custInfoMapper;

	@Override
	public void agentInfoRefresh(Long agentId) {
		JSONObject json = new JSONObject();
		json.put("option", CacheConstant.AGENT_CACHE);
		json.put("id", agentId);
		publish(json);
	}

	@Override
	public void apiSettingsInfoRefresh(String appId) {
		JSONObject json = new JSONObject();
		json.put("option", CacheConstant.API_SETTINGS_CACHE);
		json.put("appId", appId);
		publish(json);
	}

	@Override
	public void customerInfoRefresh(Long customerId) {
		JSONObject json = new JSONObject();
		json.put("option", CacheConstant.CUSTOMER_CACHE);
		json.put("customerId", customerId);
		publish(json);
	}

	@Override
	public void sysConfigInfoRefresh(String keys) {
		JSONObject json = new JSONObject();
		json.put("option", CacheConstant.SYS_CONFIG_CACHE);
		json.put("keys", keys);
		publish(json);
	}

    private void publish(JSONObject json) {
    	redisClient.publish(CacheConstant.CACHE_REFRESH_CHANNEL, json.toJSONString());
    }

	@Override
	public void reflushCustExt(Long customerId,Long agentId,String phone) {

 		String key1 = "customer:vo:key:"+customerId;
		String key3 = "customer:ext:key:"+customerId;
		String key2 = "front:login:user:"+agentId+"-"+phone;
		logger.info(key1);
		logger.info(key2);
		logger.info(key3);
		redisClient.remove(key1);
		redisClient.remove(key3);
		CustInfo custInfo = custInfoMapper.findByPhone(phone, agentId);
		// - 解压密码脱敏
		String unzipPwd = setUnknown(custInfo);
		custInfo.setUnzipPassword(unzipPwd);
		redisClient.set(key2,JSONObject.toJSONString(custInfo),86400);
	}

	/**
	 * 转换代理商单独处理方法，需要删除老的CustToken的redis存储，并且将新的cust信息存放到新的键中
	 * @param customerId
	 * @param sourceAgentId
	 * @param destAgentId
	 * @param phone
	 */
	@Override
	public void reflushCustExt(Long customerId,Long sourceAgentId,Long destAgentId,String phone) {
		String key1 = "customer:vo:key:"+customerId;
		String key3 = "customer:ext:key:"+customerId;
		String oldCustKey = "front:login:user:"+sourceAgentId+"-"+phone;
		logger.info(key1);
		logger.info(oldCustKey);
		logger.info(key3);
		redisClient.remove(key1);
		redisClient.remove(key3);
		redisClient.remove(oldCustKey);
	}

	/**
	 * 解压密码脱敏处理
	 * @date 2021/11/20
	 * @param customer
	 * @return String
	 */
	private String setUnknown(CustInfo customer) {
		String unzipPassword = customer.getUnzipPassword();
		if (!StringUtils.isBlank(unzipPassword)) {
			char first = unzipPassword.charAt(0);
			char last = unzipPassword.charAt(unzipPassword.length()-1);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < unzipPassword.length()-2; i++) {
				sb.append("*");
			}
			return first+sb.toString()+last;
		} else {
			return null;
		}
	}
}
