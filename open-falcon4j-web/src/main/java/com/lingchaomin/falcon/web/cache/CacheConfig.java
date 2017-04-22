package com.lingchaomin.falcon.web.cache;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import com.lingchaomin.falcon.common.entity.Strategy;
import com.lingchaomin.falcon.web.dto.TplDto;
import com.lingchaomin.falcon.web.entity.Host;

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
     * host缓存
     */
    public static Cache<String, Map<Long, Host>> hostCache = CacheBuilder.newBuilder().build();

    /**
     * 模版缓存
     */
    public static Cache<String, Map<Long, TplDto>> templateCache = CacheBuilder.newBuilder().build();

    /**
     * host策略
     */
    public static Cache<String, Map<String, List<Strategy>>> hostStrasCache = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();

    /**
     * 策略缓存
     */
    public static Cache<String, Map<Long, Strategy>> strategyCache = CacheBuilder.newBuilder().build();

    public static final String CACHE_TEMPLTE = "CACHE_TEMPLATE";

    public static final String CACHE_STRATEGY = "CACHE_STRATEGY";

    public static final String CACHE_HOST = "CACHE_HOST";

    public static final String CACHE_HOST_STRAS = "CACHE_HOST_STRAS";

    public static final String CACHE_HOST_METRIC_STRAS = "CACHE_HOST_METRIC_STRAS";

    public static final String CACHE_EXPRESSION="CACHE_EXPRESSION";

    public static final String CACHE_EXPRESSION_METRIC="CACHE_EXPRESSION_METRIC";

}
