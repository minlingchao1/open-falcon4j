package com.lingchaomin.falcon.judge.cache;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import com.lingchaomin.falcon.common.entity.Expression;
import com.lingchaomin.falcon.common.entity.Strategy;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/10 下午9:55
 * @description
 */
public class CacheConfig {


    /**
     * hots metric 策略
     */
    public static Cache<String, Map<String, List<Strategy>>> hostMetricStrasCache = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();

    /**
     * 全局策略
     */
    public static Cache<String,Map<String,List<Expression>>> expressionMetricCache=CacheBuilder.newBuilder().expireAfterAccess(5,TimeUnit.MINUTES).build();


    public static final String CACHE_HOST_METRIC_STRAS = "CACHE_HOST_METRIC_STRAS";

    public static final String CACHE_EXPRESSION_METRIC="CACHE_EXPRESSION_METRIC";

}
