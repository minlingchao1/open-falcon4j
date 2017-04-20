package com.lingchaomin.falcon.common.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/8 下午7:08
 * @description 全局策略
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Expression implements Serializable {

    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    /**
     * 表达式
     */
    private String expression;

    /**
     * 判断函数
     */
    private String func;

    /**
     * 操作符
     */
    private String operator;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 备注
     */
    private String note;

    /**
     * 阀值
     */
    private float rightValue;


    /**
     * 最大发送次数
     */
    private Integer maxStep;

    /**
     * actionId
     */
    private Long actionId;

    /**
     * 回调事件名称
     */
    private String actionName;

    /**
     * 标签
     */
    private Map<String,String> tags;

    /**
     * 指标
     */
    private String metric;

}
