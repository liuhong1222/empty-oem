package com.cc.oem.modules.agent.service;


import org.springframework.stereotype.Service;

/**
 * 刷新缓存
 * @author liuh
 * @date 2021年11月10日
 */
@Service
public interface RefreshCacheService {
	
    /**
     * 代理商信息缓存刷新
     */
    void agentInfoRefresh(Long agentId);

    /**
     * api账号信息缓存刷新
     */
    void apiSettingsInfoRefresh(String appId);

    /**
     * 用户信息缓存刷新
     */
    void customerInfoRefresh(Long customerId);

    /**
     * 参数管理信息缓存刷新
     */
    void sysConfigInfoRefresh(String keys);

    void reflushCustExt(Long customerId,Long agentId,String phone);

    void reflushCustExt(Long customerId,Long sourceAgentId,Long destAgentId,String phone);
}
