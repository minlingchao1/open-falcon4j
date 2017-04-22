package com.lingchaomin.falcon.alarm.redis;


import com.lingchaomin.falcon.common.redis.RedisVisitorBaseFacade;
import com.lingchaomin.falcon.common.redis.exception.MissAnnotationException;
import com.lingchaomin.falcon.common.redis.parse.ListParser;

import redis.clients.jedis.Jedis;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/10 上午10:01
 * @description
 */
public class MetricRedisVistor extends RedisVisitorBaseFacade {

    public static final String KEY_BASE = "metric" ;


    public MetricRedisVistor() {
        this("metric");
    }

    public MetricRedisVistor(String baseKey) {
        super(KEY_BASE);
    }

    /**
     * 列表-key列表从右侧取出一个值
     */
    @Override
    public <T> Long rpush(T obj) throws MissAnnotationException {
        Jedis jedis=null;

        try {
            String str= ListParser.obj2Str(obj);

            return getRedisManager().rpush(getBaseKey(),str);

        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }
}
