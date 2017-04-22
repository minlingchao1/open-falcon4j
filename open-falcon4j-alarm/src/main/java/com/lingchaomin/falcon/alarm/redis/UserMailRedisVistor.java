package com.lingchaomin.falcon.alarm.redis;

import com.lingchaomin.falcon.common.redis.RedisVisitorBaseFacade;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 下午1:34
 * @description
 */
public class UserMailRedisVistor extends RedisVisitorBaseFacade {

    public static final String BASE_KEY = "user:mail";

    public UserMailRedisVistor() {
        this("user:mail");
    }

    public UserMailRedisVistor(String baseKey) {
        super(baseKey);
    }

}
