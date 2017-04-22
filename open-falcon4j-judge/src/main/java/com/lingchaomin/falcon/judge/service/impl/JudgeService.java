package com.lingchaomin.falcon.judge.service.impl;


import com.lingchaomin.falcon.common.entity.Event;
import com.lingchaomin.falcon.common.entity.Expression;
import com.lingchaomin.falcon.common.entity.JudgeItem;
import com.lingchaomin.falcon.common.entity.Strategy;
import com.lingchaomin.falcon.common.redis.exception.MissAnnotationException;
import com.lingchaomin.falcon.common.util.DateUtil;
import com.lingchaomin.falcon.common.util.MapUtil;
import com.lingchaomin.falcon.judge.cache.CacheConfig;
import com.lingchaomin.falcon.judge.constant.EventConfig;
import com.lingchaomin.falcon.judge.constant.JudgeConfig;
import com.lingchaomin.falcon.judge.his.JudgeItemQueue;
import com.lingchaomin.falcon.judge.logic.IParseFuncLogic;
import com.lingchaomin.falcon.judge.redis.EventRedisVistor;
import com.lingchaomin.falcon.judge.service.IJudgeService;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/8 下午7:25
 * @description 告警判断
 */
@Service
public class JudgeService implements IJudgeService {

    private static final Logger LOG=LoggerFactory.getLogger(JudgeService.class);

    @Autowired
    private IParseFuncLogic parseFunc;


    @Autowired
    private EventRedisVistor eventRedisVistor;

    @Autowired
    private JudgeConfig judgeConfig;


    /**
     * 告警判断
     */
    public void judge(JudgeItemQueue judgeItemQueue, JudgeItem judgeItem) {

        //查找strategy
        checkStrategy(judgeItemQueue,judgeItem);

        //查找expression
        checkExpression(judgeItemQueue,judgeItem);
    }


    /**
     * 检测主机相关策略
     * @param judgeItem
     */
    private void checkStrategy(JudgeItemQueue judgeItemQueue,JudgeItem judgeItem){

        Map<String,List<Strategy>> hostMetricStras= CacheConfig.hostMetricStrasCache.getIfPresent(CacheConfig.CACHE_HOST_METRIC_STRAS);

        if(hostMetricStras==null){
            LOG.warn("hostMetricStras is null~~~~");
            return;
        }

        String key=String.format("%s/%s",judgeItem.getEndPoint(),judgeItem.getMetric());

        List<Strategy> strategies=hostMetricStras.get(key);

        if(CollectionUtils.isEmpty(strategies)){
            LOG.warn("strategies is empty, endpoint:{},metric:{}",judgeItem.getEndPoint(),judgeItem.getMetric());
            return;
        }

        for (Strategy strategy:strategies){
            String tags=strategy.getTag();

            boolean related=true;
            //判断标签是否一致,排除掉一些不符合条件的
            if(StringUtils.isNoneBlank(judgeItem.getTag())&&StringUtils.isNoneBlank(strategy.getTag())){
                Map<String,String> metricMap= MapUtil.convertStr2Map(judgeItem.getTag(),MapUtil.COMMA_SPLIT,MapUtil.EQUAL_SPLIT);
                Map<String,String> strategyMap=MapUtil.convertStr2Map(strategy.getTag(),MapUtil.COMMA_SPLIT,MapUtil.EQUAL_SPLIT);

                for(Map.Entry<String,String> entry:strategyMap.entrySet()){
                    if(!entry.getValue().equals(metricMap.get(entry.getKey()))||!metricMap.containsKey(entry.getKey())){
                       related=false;
                       break;
                    }
                }
            }
            if(!related){
                continue;
            }

            //根据策略对于监控信息判断
            judgeItemWithStrategy(strategy,judgeItemQueue,judgeItem);
        }

    }

    /**
     * 检测全局策略
     * @param judgeItem
     */
    private void checkExpression(JudgeItemQueue judgeItemQueue,JudgeItem judgeItem){

        if(StringUtils.isBlank(judgeItem.getTag())){
            return;
        }

        List<String> keys=buildKeysFromMetricAndTags(judgeItem);

        Map<String,List<Expression>> expressionsMap=CacheConfig.expressionMetricCache.getIfPresent(CacheConfig.CACHE_EXPRESSION_METRIC);

        if(expressionsMap==null){
            LOG.warn("expressionMap is null~~~");
            return;
        }

        //avoid deal expression many times
        Map<Long,Expression> dealedExpressions=new HashMap<>();

        for(String key:keys){
            if(!expressionsMap.containsKey(key)){
                continue;
            }

            List<Expression> expressions=filterRelatedExpressions(expressionsMap.get(key),judgeItem);

            for(Expression expression:expressions){
                if(dealedExpressions.containsKey(expression.getId())){
                    continue;
                }
                dealedExpressions.put(expression.getId(),expression);

                judgeItemWithExpression(expression,judgeItemQueue,judgeItem);

            }
        }

    }

    /**
     *构建key
     * @param judgeItem
     * @return
     */
    private List<String> buildKeysFromMetricAndTags(JudgeItem judgeItem){
        String tags=judgeItem.getTag();

        String[] tagsArr=tags.split(",");

        List<String> keyList=new ArrayList<>();

        for(String tag:tagsArr){
            String key=tag.split("=")[0];
            String value=tag.split("=")[1];
            String realKey=String.format("%s/%s=%s",judgeItem.getMetric(),key,value);
            keyList.add(realKey);
        }

        keyList.add(String.format("%s/endpoint=%s",judgeItem.getMetric(),judgeItem.getEndPoint()));

        return keyList;
    }

    private List<Expression> filterRelatedExpressions(List<Expression> expressions,JudgeItem judgeItem){
        int size=expressions.size();

        if(size==0){
            return new ArrayList<>();
        }

        List<Expression> relateExpressions=new ArrayList<>();

        for(Expression expression:expressions){
            boolean related=true;

            Map<String,String> itemTags=judgeItem.getTags();

            if(expression.getTags().containsKey("endpoint")){
                itemTags=copyItemTags(judgeItem);
            }

            for(Map.Entry<String,String> expEntry:expression.getTags().entrySet()){
                if(!itemTags.containsKey(expEntry.getKey())||itemTags.get(expEntry.getKey())!=expEntry.getValue()){
                    related=false;
                    break;
                }
            }

            if(!related){
                continue;
            }
            relateExpressions.add(expression);
        }

        return relateExpressions;
    }

    private Map<String,String> copyItemTags(JudgeItem judgeItem){
        Map<String,String> map=new HashMap<>();

        map.put("endpoint",judgeItem.getEndPoint());

        if(judgeItem.getTags()!=null&&judgeItem.getTags().size()>0){
            for(Map.Entry<String,String> entry:judgeItem.getTags().entrySet()){
                map.put(entry.getKey(),entry.getValue());
            }
        }

        return map;
    }

    /**
     * 根据策略判断是否生产事件
     * @param strategy
     * @param judgeItem
     */
    private void judgeItemWithStrategy(Strategy strategy,JudgeItemQueue judgeItemQueue,JudgeItem judgeItem){
        Map<String,Object> map=parseFunc.parseFuncFromString(judgeItemQueue,strategy);

        if(map==null){
            return;
        }

        Boolean isTriggered=(Boolean) map.get("isTriggered");
        if(!isTriggered){
            return;
        }

        Float leftValue = (Float) map.get("leftValue");
        List<JudgeItem> judgeItemList = (List<JudgeItem>) map.get("latestJudgeItem");

        //生产事件
        Event event=createEventOfStrategy(strategy,judgeItem,leftValue);

        //检测是否发送
        checkSendEvent(judgeItemList,event,isTriggered,strategy.getMaxStep());
    }

    /**
     * 根据策略判断是否生产事件
     * @param expression
     * @param judgeItemQueue
     * @param judgeItem
     */
    private void judgeItemWithExpression(Expression expression,JudgeItemQueue judgeItemQueue,JudgeItem judgeItem){
        Map<String,Object> map=parseFunc.parseFuncFromString(judgeItemQueue,expression);

        if(map==null){
            return;
        }

        Boolean isTriggered=(Boolean) map.get("isTriggered");
        if(!isTriggered){
            return;
        }

        Float leftValue = (Float) map.get("leftValue");
        List<JudgeItem> judgeItemList = (List<JudgeItem>) map.get("latestJudgeItem");

        //生产事件
        Event event=createEventOfExpression(expression,judgeItem,leftValue);

        //检测是否发送
        checkSendEvent(judgeItemList,event,isTriggered,expression.getMaxStep());
    }

    /**
     * 生成事件
     * @param strategy
     * @param judgeItem
     * @return
     */
    private Event createEventOfStrategy(Strategy strategy,JudgeItem judgeItem,Float leftValue){

        Event event=Event.builder()
                .id(Event.genId(strategy.getId(),judgeItem.getEndPoint(),judgeItem.getMetric(),judgeItem.getTag()))
                .strategyId(strategy.getId())
                .endPoint(judgeItem.getEndPoint())
                .leftValue(leftValue)
                .eventTime(judgeItem.getTimestamp())
                .pushedTags(judgeItem.getTag())
                .priority(strategy.getPriority())
                .actionId(strategy.getActionId())
                .build();

        return event;
    }

    /**
     * 生成事件
     * @param expression
     * @param judgeItem
     * @return
     */
    private Event createEventOfExpression(Expression expression,JudgeItem judgeItem,Float leftValue){

        Event event=Event.builder()
                .id(Event.genId(expression.getId(),judgeItem.getEndPoint(),judgeItem.getMetric(),judgeItem.getTag()))
                .strategyId(expression.getId())
                .endPoint(judgeItem.getEndPoint())
                .leftValue(leftValue)
                .eventTime(judgeItem.getTimestamp())
                .pushedTags(judgeItem.getTag())
                .priority(expression.getPriority())
                .actionId(expression.getActionId())
                .build();

        return event;
    }

    /**
     * 检测是否需要发送事件
     * @param judgeItems
     * @param event
     * @param isTriggered
     * @param maxStep
     */
    private void checkSendEvent(List<JudgeItem> judgeItems,Event event,Boolean isTriggered,Integer maxStep){

        //获取上次最后发送事件
        Long len=eventRedisVistor.hlen(event.getId());

        Event lastEvent=null;
        if(len!=null&&len!=0){
            lastEvent=eventRedisVistor.hgetAll(new Event(),event.getId());
        }

        if(isTriggered){
            event.setStatus(EventConfig.PROBLEM);

            //本次触发,之前没有发送过,产生Event
            if(lastEvent==null||lastEvent.getStatus().equals(EventConfig.OK)){
                event.setCurrentStep(EventConfig.FIRST_STEP);

                if(maxStep==0){
                    return;
                }
                sendEvent(event);
                return;
            }


            // 逻辑走到这里，说明之前Event是PROBLEM状态
            // 报警次数已经足够多，到达了最多报警次数了，不再报警
            if(lastEvent.getCurrentStep()>=maxStep){
                LOG.warn("alarm currentStep:{} maxStep:{} has enough",event.getCurrentStep(),maxStep);
                return;
            }

            // 产生过报警的点，就不能再使用来判断了，否则容易出现一分钟报一次的情况
            // 只需要拿最后一个historyData来做判断即可，因为它的时间最老
            if(judgeItems.get(judgeItems.size()-1).getTimestamp()<=lastEvent.getEventTime()){
                LOG.warn("judgetItem:{},lastEvent:{} judgeItem timestamp < lastEvent timestamp",judgeItems.toString(),event.toString());
                return;
            }

            // 报警不能太频繁，两次报警之间至少要间隔MinInterval秒，否则就不能报警
            if(System.currentTimeMillis()/1000-lastEvent.getEventTime()<judgeConfig.getMinInterval()){
                LOG.warn("alarm too much~~~~~");
                return;
            }

            event.setCurrentStep(event.getCurrentStep()+1);
            sendEvent(event);
        }else {
            // 如果LastEvent是Problem，报OK，否则啥都不做
            if(event!=null&&event.getStatus().equals(EventConfig.PROBLEM)){
                event.setStatus(EventConfig.OK);
                event.setCurrentStep(EventConfig.FIRST_STEP);
                sendEvent(event);
            }
        }
    }

    /**
     * 存储事件
     * @param event
     */
    private void  sendEvent(Event event){
        //设置最后事件
        eventRedisVistor.hmset(event);

        //发送到redis, 用于alarm告警
        try {
            eventRedisVistor.lpush(event);
        } catch (MissAnnotationException e) {
            LOG.error(e.getMessage(),e);
        }
    }
}
