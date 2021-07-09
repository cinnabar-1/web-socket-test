package com.cinnabar.client.config.redisHelper;

import com.cinnabar.client.config.handelException.CommonException;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

/**
 * @author cinnabar-1
 * @version 1.0.0
 * @ClassName RedisHelper.java
 * @Description
 * @createTime 2021-07-09  16:13:00
 */
public class RedisHelper {
    // Redis服务器IP
    @Value(value = "${spring.redis.host}")
    private static String ADDR;
    // Redis的端口号
    @Value(value = "${spring.redis.port}")
    private static int PORT;
    // 可用连接实例的最大数目，默认值为8；
    // 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE = 16;
    // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 6;
    // 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 10000;

    // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;
    private static JedisPool jedisPool = null;

    /*
     * 初始化Redis连接池
     */
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, ADDR, PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Jedis实例
     *
     * @return Jedis
     */
    public synchronized static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                return jedisPool.getResource();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    void setKeyValue(String key, String value, int expiredTime) throws CommonException {
        Jedis jedis = getJedis();
        if (jedis != null) {
            jedis.flushDB();
            Pipeline pip = jedis.pipelined();
            pip.set(key, value);
            if (expiredTime > 0)
                pip.expire(key, expiredTime);
            pip.sync();// 同步获取所有的回应
        } else {
            throw new CommonException("redis init failed");
        }
    }

    void setKeyValue(String key, String value) throws CommonException {
        setKeyValue(key, value, -1);
    }

    void getValue(String key) throws CommonException {
        Jedis jedis = getJedis();
        if (jedis != null) {
            jedis.flushDB();
            Pipeline pip = jedis.pipelined();
            pip.get(key);
            pip.sync();// 同步获取所有的回应
        } else {
            throw new CommonException("redis init failed");
        }
    }
}
