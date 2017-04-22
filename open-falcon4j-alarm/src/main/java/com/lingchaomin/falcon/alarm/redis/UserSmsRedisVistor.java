package com.lingchaomin.falcon.alarm.redis;

import com.lingchaomin.falcon.common.redis.RedisVisitorBaseFacade;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 下午1:33
 * @description
 */
public class UserSmsRedisVistor extends RedisVisitorBaseFacade {

    public static final String BASE_KEY = "user:sms";

    public UserSmsRedisVistor() {
        this("user:sms");
    }

    public UserSmsRedisVistor(String baseKey) {
        super(baseKey);
    }

}
