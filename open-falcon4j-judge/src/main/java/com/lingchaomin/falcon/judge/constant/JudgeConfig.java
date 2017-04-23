package com.lingchaomin.falcon.judge.constant;

import lombok.Data;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/22 下午11:26
 * @description 告警配置
 */
@Data
public class JudgeConfig {

    /**
     * 是否开启告警判断
     */
    private boolean enabled;

    /**
     * 最小发送间隔
     */
    private Integer minInterval;

}
