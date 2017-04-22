package com.lingchaomin.falcon.judge.task;


import com.lingchaomin.falcon.common.api.IExpressionDubboApi;
import com.lingchaomin.falcon.common.api.IStrategyDubboApi;
import com.lingchaomin.falcon.common.entity.Expression;
import com.lingchaomin.falcon.common.entity.Strategy;
import com.lingchaomin.falcon.judge.cache.CacheConfig;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 上午10:08
 * @description 同步策略
 */
@Component
public class SyncStrategies {

    private static final Logger LOG=LoggerFactory.getLogger(SyncStrategies.class);

    @Autowired
    private IStrategyDubboApi strategyDubboApi ;

    @Autowired
    private IExpressionDubboApi expressionDubboApi;

    public void syncStrategies(){
        //同步主机相关策略
        rebuildStrategies();

    }

    public void syncExpressions(){

        //同步全局策略
        rebuildExpressions();
    }

    private void rebuildStrategies(){

        Map<String,List<Strategy>> hostMetricStragies= CacheConfig.hostMetricStrasCache.getIfPresent(CacheConfig.CACHE_HOST_METRIC_STRAS);

        if(hostMetricStragies!=null) {
            LOG.warn("hostMetricStragies:{}", hostMetricStragies.toString());
            return;
        }

        hostMetricStragies=new HashMap<>();

        Map<String,List<Strategy>> hostStras=strategyDubboApi.getAllStrategy();

        if(hostStras==null){
            LOG.warn("host strategies is null~~~");
            return;
        }


        for(Map.Entry<String,List<Strategy>> entry:hostStras.entrySet()){
            List<Strategy> strategies=entry.getValue();

            for(Strategy strategy:strategies){

                String key=String.format("%s/%s",entry.getKey(),strategy.getMetric());

                if(hostMetricStragies.containsKey(key)){
                    hostMetricStragies.get(key).add(strategy);
                }else {
                    List<Strategy> list=new ArrayList<Strategy>();
                    list.add(strategy);
                    hostMetricStragies.put(key,list);
                }

            }
        }

        LOG.warn("hostMetricStragies:{}",hostMetricStragies.toString());

        CacheConfig.hostMetricStrasCache.put(CacheConfig.CACHE_HOST_METRIC_STRAS,hostMetricStragies);
    }

    private void rebuildExpressions(){

        Map<String,List<Expression>> expressionMap=CacheConfig.expressionMetricCache.getIfPresent(CacheConfig.CACHE_EXPRESSION_METRIC);

        if(expressionMap!=null){
            return;
        }

        List<Expression> expressions=expressionDubboApi.getAllExpressions();

        //再判断一次
        if(CollectionUtils.isEmpty(expressions)){
            LOG.warn("EXPRESSIONS IS NULL");
            return;
        }

        expressionMap=new HashMap<>();
        for(Expression expression:expressions){

           for(Map.Entry<String,String> entry:expression.getTags().entrySet()){
               String key= String.format("%s/%s=%s",expression.getMetric(),entry.getKey(),entry.getValue());
               if(expressionMap.containsKey(key)){
                   expressionMap.get(key).add(expression);
               }else {
                   List<Expression> expressionsList=new ArrayList<>();
                   expressionsList.add(expression);
                   expressionMap.put(key,expressionsList);
               }
           }
        }

        LOG.warn("expressions:{}",expressionMap.toString());
        CacheConfig.expressionMetricCache.put(CacheConfig.CACHE_EXPRESSION_METRIC,expressionMap);

    }
}
