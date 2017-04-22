package com.lingchaomin.falcon.alarm.service;


import com.lingchaomin.falcon.common.entity.Event;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/10 上午11:49
 * @description 告警服务
 */
public interface IAlarmService {

    /**
     * 事件消费
     * @param event
     * @param isHighPrority
     */
    void consumeEvent(Event event, Boolean isHighPrority);
}
