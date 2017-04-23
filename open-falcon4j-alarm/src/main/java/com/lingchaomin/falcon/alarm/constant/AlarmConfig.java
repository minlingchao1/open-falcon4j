package com.lingchaomin.falcon.alarm.constant;

import lombok.Data;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 上午10:28
 * @description 告警配置
 */
@Data
public class AlarmConfig {

    /**
     * 高优先级队列
     */
    private String highQueue;

    /**
     * 低优先级队列
     */
    private String lowQueue;
}
