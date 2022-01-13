package com.cinnabar.client.config.redisHelper;

import org.springframework.boot.autoconfigure.cache.CacheProperties;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * @author cinnabar-1
 * @version 1.0.0
 * @ClassName RedisClusterTest.java
 * @Description
 * @createTime 2021-10-19  15:59:00
 */
public class RedisClusterTest {
    public static void main(String[] args) throws Exception {
        single();
    }

    static void cluster() {
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("39.99.145.27", 6379));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        jedisCluster.set("a", "1");
        String result = jedisCluster.get("a");
        System.out.println(result);
        jedisCluster.close();
    }

    static void single() {
        Jedis jedis = new Jedis("39.99.145.27", 6379);
        jedis.set("a", "1");
        System.out.println(jedis.get("a"));
        jedis.del("a");
        jedis.close();
    }
}
