package com.lingchaomin.falcon.alarm.redis;


import com.lingchaomin.falcon.common.redis.RedisVisitorBaseFacade;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/9 上午12:38
 * @description 事件
 */
public class EventRedisVistor extends RedisVisitorBaseFacade {

    public static final String BASE_KEY = "event";

    public EventRedisVistor() {
        this("event");
    }

    public EventRedisVistor(String baseKey) {
        super(baseKey);
    }

}
