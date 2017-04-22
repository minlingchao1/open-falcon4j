package com.lingchaomin.falcon.alarm.redis;


import com.lingchaomin.falcon.common.redis.RedisVisitorBaseFacade;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 下午1:32
 * @description
 */
public class SmsRedisVistor extends RedisVisitorBaseFacade {

    public static final String BASE_KEY = "sms";

    public SmsRedisVistor() {
        this("sms");
    }

    public SmsRedisVistor(String baseKey) {
        super(baseKey);
    }

}
