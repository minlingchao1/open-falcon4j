package com.lingchaomin.falcon.sender.redis;

import com.lingchaomin.falcon.common.redis.RedisVisitorBaseFacade;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 下午1:33
 * @description
 */
public class MailRedisVistor extends RedisVisitorBaseFacade {

    public static final String BASE_KEY = "mail";

    public MailRedisVistor() {
        this("mail");
    }

    public MailRedisVistor(String baseKey) {
        super(baseKey);
    }

}
