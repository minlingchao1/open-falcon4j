package com.lingchaomin.falcon.transfer.constant;

import lombok.Data;
import lombok.Getter;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/21 上午12:09
 * @description 告警数据判断配置
 */
@Data
public class JudgeConfig {

    /**
     * 是否开启
     */
    private Boolean enable;

    /**
     * 批量发送数量
     */
    private Integer batch;

    /**
     * 接收服务器配置
     */
    private String cluster;
}
