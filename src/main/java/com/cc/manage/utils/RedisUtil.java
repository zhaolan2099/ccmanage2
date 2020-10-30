package com.cc.manage.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Slf4j
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

    public static String set(String key,String value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.set(key,value);
        }catch (Exception e){
            log.info("jedis连接异常.");
            e.printStackTrace();
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return null;
    }
    public static String get(String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        }catch (Exception e){
            log.info("jedis连接异常.");
            e.printStackTrace();
        }finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return null;
    }
}