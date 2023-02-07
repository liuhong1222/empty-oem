package com.cc.oem.common.utils;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

/**
 * @author songb
 * @since  2018年6月4日
 *
 */
public class DistributedLockHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(DistributedLockHandler.class);  
	  
    private final static long LOCK_EXPIRE = 20 * 1000L; //单个业务持有锁的时间30s,防止死锁  
  
    private final static long LOCK_TRY_INTERVAL = 30L; //默认30ms尝试一次  
  
    private final static long LOCK_TRY_TIMEOUT = 30 * 1000L; // 默认尝试30s  
  
    @Autowired  
    private StringRedisTemplate template;  
  
    /** 
     * 操作redis获取全局锁 
     * 
     * @param lock           锁的名称 
     * @param timeout        获取的超时时间 
     * @param tryInterval    多少ms尝试一次 
     * @param lockExpireTime 获取成功后锁的过期时间 
     * @return true 获取成功，false获取失败 
     */  
    public boolean  getLock(Locker lock,long timeout,long tryInterval,long lockExpireTime){  
  
        try{  
            if(StringUtils.isEmpty(lock.getName()) || StringUtils.isEmpty(lock.getValue())){  
                return false;  
            }  
            long startTime = System.currentTimeMillis();  
            while (true){  
                if(template.opsForValue().setIfAbsent(lock.getName(),lock.getValue())){  
                    template.opsForValue().set(lock.getName(),lock.getValue(),lockExpireTime,TimeUnit.MILLISECONDS);  
                    logger.info(Thread.currentThread().getName() + " : get lock");  
                    return true;  
                }else {  
                    logger.info(Thread.currentThread().getName() + " : ----> locking is exist!!!");  
                }  
                if(System.currentTimeMillis() - startTime > timeout){  
                    return false;  
                }  
                Thread.sleep(tryInterval);
            }  
        }catch (Exception e){  
            logger.error(e.getMessage());  
            return false;  
        }  
    }  
  
  
    /** 
     * 尝试获取全局锁 
     * 
     * @param lock 锁的名称 
     * @return true 获取成功，false获取失败 
     */  
    public boolean tryLock(Locker lock) {  
        return getLock(lock, LOCK_TRY_TIMEOUT, LOCK_TRY_INTERVAL, LOCK_EXPIRE);  
    }  
  
  
    /** 
     * 尝试获取全局锁 
     * 
     * @param lock    锁的名称 
     * @param timeout 获取超时时间 单位ms 
     * @return true 获取成功，false获取失败 
     */  
    public boolean tryLock(Locker lock, long timeout) {  
        return getLock(lock, timeout, LOCK_TRY_INTERVAL, LOCK_EXPIRE);  
    }  
  
  
    /** 
     * 尝试获取全局锁 
     * 
     * @param lock        锁的名称 
     * @param timeout     获取锁的超时时间 
     * @param tryInterval 多少毫秒尝试获取一次 
     * @return true 获取成功，false获取失败 
     */  
    public boolean tryLock(Locker lock, long timeout, long tryInterval) {  
        return getLock(lock, timeout, tryInterval, LOCK_EXPIRE);  
    }  
  
  
    /** 
     * 尝试获取全局锁 
     * 
     * @param lock           锁的名称 
     * @param timeout        获取锁的超时时间 
     * @param tryInterval    多少毫秒尝试获取一次 
     * @param lockExpireTime 锁的过期 
     * @return true 获取成功，false获取失败 
     */  
    public boolean tryLock(Locker lock, long timeout, long tryInterval, long lockExpireTime) {  
        return getLock(lock, timeout, tryInterval, lockExpireTime);  
    }
  
    /** 
     * 释放锁 
     */  
    public void releaseLock(Locker lock) {  
        if (!StringUtils.isEmpty(lock.getName())) {
            logger.info(Thread.currentThread().getName() + " : del lock");  
            template.delete(lock.getName());  
        }  
    } 
}
