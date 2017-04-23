package com.lingchaomin.falcon.alarm.task;


import com.lingchaomin.falcon.alarm.constant.AlarmConfig;
import com.lingchaomin.falcon.alarm.redis.EventRedisVistor;
import com.lingchaomin.falcon.alarm.service.IAlarmService;
import com.lingchaomin.falcon.common.entity.Event;
import com.lingchaomin.falcon.common.util.JsonUtil;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/10 下午2:23
 * @description 告警事件处理
 */
@Component
public class HighEventAlarmTask {

    @Autowired
    private EventRedisVistor eventRedisVistor;

    @Autowired
    private IAlarmService alarmService;

    @Autowired
    private AlarmConfig alarmConfig;

    public static final Integer LIMIT=500;

    /**
     * 处理事件
     */
    public void dealEvent(){

        String[] highQueues=alarmConfig.getHighQueue().split(",");
        List<String> result=eventRedisVistor.brpop(1, highQueues);

        if(CollectionUtils.isEmpty(result)){
            return;
        }

        Event event= JsonUtil.fromJson(result.get(1),Event.class);

        alarmService.consumeEvent(event,true);

    }
}
