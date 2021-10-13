package com.cinnabar.client.config.redisHelper;

import com.cinnabar.client.config.handelException.CommonException;
import lombok.Data;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author cinnabar-1
 * @version 1.0.0
 * @ClassName RedisHelper.java
 * @Description
 * @createTime 2021-07-09  16:13:00
 */
@Component
public class RedisHelper {

    private static Logger log = LoggerFactory.getLogger(RedisHelper.class);

    @Value(value = "${spring.redis.host}")
    public void setADDR(String ADDR) {
        this.ADDR = ADDR;
    }

    @Value(value = "${spring.redis.port}")
    public void setPORT(int PORT) {
        this.PORT = PORT;
    }

    // Redis服务器IP
    @Getter
    private static String ADDR;
    // Redis的端口号
    @Getter
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
    private static void init() {
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
    public static synchronized Jedis getJedis() {
        try {
            if (jedisPool == null) {
                init();
            }
            log.info("jedis pool connect num: {}", jedisPool.getNumActive());
            return jedisPool.getResource();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String get(String key) {
        Jedis jedis;
        if ((jedis = RedisHelper.getJedis()) != null) {
            try {
                return jedis.get(key);
            } catch (Exception e) {
                log.info("jedis get exception: {}", e.getMessage());
            } finally {
                jedis.close();
            }
        }
        return null;
    }

    public static void set(String key, String value) {
        set(key, value, -1);
    }

    public static void set(String key, String value, int expiredTime) {
        Jedis jedis;
        if ((jedis = RedisHelper.getJedis()) != null) {
            try {
                if (expiredTime < 1) {
                    jedis.set(key, value);
                } else {
                    jedis.set(key, value);
                    jedis.expire(key, expiredTime);
                }
            } catch (Exception e) {
                log.info("jedis set exception: {}", e.getMessage());
            } finally {
                jedis.close();
            }
        }
    }

    public static void del(String key) {
        Jedis jedis;
        if ((jedis = RedisHelper.getJedis()) != null) {
            try {
                jedis.del(key);
            } catch (Exception e) {
                log.info("jedis del exception: {}", e.getMessage());
            } finally {
                jedis.close();
            }
        }
    }

    public static void expire(String key, int expiredTime) {
        Jedis jedis = getJedis();
        jedis.expire(key, expiredTime);
        jedis.close();
    }

    public static Map<String, Response<String>> pipGetValue(List<String> keys) throws CommonException {
        Jedis jedis = getJedis();
        Map<String, Response<String>> redisValueMap = new HashMap<>();
        if (jedis != null) {
            Pipeline pip = jedis.pipelined();
            for (String key :
                    keys) {
                redisValueMap.put(key, pip.get(key));
            }
            //通过读取所有响应来同步管道。此操作将关闭管道。为了从流水线命令获取返回值，请捕获所执行命令的不同Response <？>。
            pip.sync();
            jedis.close();
            return redisValueMap;
        } else {
            throw new CommonException("redis init failed");
        }
    }

    public static void pipSetKeyValue(List<HelperSet> sets) throws CommonException {
        Jedis jedis = getJedis();
        if (jedis != null) {
            // 清除所有key
//            jedis.flushDB();
            Pipeline pip = jedis.pipelined();
            for (HelperSet set :
                    sets) {
                pip.set(set.getKey(), set.getValue());
                if (set.getExpiredTime() > 0)
                    pip.expire(set.getKey(), set.getExpiredTime());
            }
            pip.sync();// 同步获取所有的回应
            jedis.close();
        } else {
            throw new CommonException("redis init failed");
        }
    }

    public static void pipSetKeyValue(Map<String, String> map) throws CommonException {
        List<HelperSet> helperSets = new LinkedList<>();
        for (String key :
                map.keySet()) {
            HelperSet helperSet = new HelperSet();
            helperSet.setKey(key);
            helperSet.setValue(map.get(key));
            helperSets.add(helperSet);
        }
        pipSetKeyValue(helperSets);
    }

    @Data
    public static class HelperSet {
        String key;
        String value;
        int expiredTime;

        public HelperSet() {
        }

        public HelperSet(String key, String value, int expiredTime) {
            this.key = key;
            this.value = value;
            this.expiredTime = expiredTime;
        }
    }
}
