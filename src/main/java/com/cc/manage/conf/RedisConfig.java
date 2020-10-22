package com.cc.manage.conf;



import com.cc.manage.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

    //读取配置文件中的redis的ip地址
    @Value("${redis.host:disabled}")
    private String host;

    @Value("${redis.port:0}")
    private int port;

    @Value("${redis.password:0}")
    private String password;

    @Value("${redis.database:0}")
    private int database;


    @Bean   //spring在启动的时候就创建出来
    public RedisUtil getRedisUtil(){
        if(host.equals("disabled")){
            return null;
        }
        RedisUtil redisUtil=new RedisUtil();
        if(StringUtils.isEmpty(password)){
            RedisUtil.initPool(host,port,database);
        }else {
            RedisUtil.initPool(host,port,database, password);
        }
        return redisUtil;
    }

}
