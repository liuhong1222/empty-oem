package com.cc.oem.common.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

/**
 * @author liuh
 * @date 2021年3月24日
 */
public class DistributedLockWrapper extends RedisDistributeLock {

	public String lockName;
	public String identifier;

    public DistributedLockWrapper(Jedis jedis, String lockName, Long acquireTimeout, int timeout) {
        super(jedis);
        this.lockName = lockName;
        this.identifier = super.tryLock(lockName,acquireTimeout, timeout);;
    }

    public boolean releaseLock() {
        if (this.identifier == null) {
            return true;
        }
        return super.tryUnLock(this.lockName, this.identifier);
    }

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
}
