package com.cc.manage.utils;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


@Component
public class RedisUtil {

    private static JedisPool jedisPool;


    public static void initPool(String host,int port ,int database,String password){

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(200);
        poolConfig.setMaxIdle(50);
        poolConfig.setMinIdle(8);
        poolConfig.setMaxWaitMillis(10000);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setTimeBetweenEvictionRunsMillis(30000);
        poolConfig.setNumTestsPerEvictionRun(10);
        poolConfig.setMinEvictableIdleTimeMillis(60000);

        jedisPool = new JedisPool(poolConfig,host,port,20*1000,password,database);
    }


    public static void initPool(String host,int port ,int database ){

        JedisPoolConfig poolConfig = new JedisPoolConfig();

        poolConfig.setMaxTotal(200);
        poolConfig.setMaxIdle(50);
        poolConfig.setMinIdle(8);
        poolConfig.setMaxWaitMillis(10000);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setTimeBetweenEvictionRunsMillis(30000);
        poolConfig.setNumTestsPerEvictionRun(10);
        poolConfig.setMinEvictableIdleTimeMillis(60000);

        jedisPool=new JedisPool(poolConfig,host,port,20*1000);
    }


    public static Jedis getJedis(){

        Jedis jedis = jedisPool.getResource();
        return jedis;
    }

}