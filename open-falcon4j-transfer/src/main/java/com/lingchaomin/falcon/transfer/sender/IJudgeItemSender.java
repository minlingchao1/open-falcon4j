package com.lingchaomin.falcon.transfer.sender;

import com.lingchaomin.falcon.common.entity.MetricValue;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/21 上午12:20
 * @description 告警数据判断队列发送
 */
public interface IJudgeItemSender {

    /**
     * 添加到告警队列
     * @param metricValue
     */
    void push2JudgeSendQueue(MetricValue metricValue);
}
