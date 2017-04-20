package com.lingchaomin.falcon.common.entity;


import com.lingchaomin.falcon.common.redis.annotation.Hash;
import com.lingchaomin.falcon.common.redis.annotation.HashColumn;
import com.lingchaomin.falcon.common.redis.annotation.List;
import com.lingchaomin.falcon.common.redis.annotation.ListColumn;
import com.lingchaomin.falcon.common.util.Md5Util;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/8 下午7:12
 * @description 告警事件
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@List(key = "event:{priority}")
@Hash(key = "event:{id}")
public class Event implements Serializable{

    /**
     * id
     */
    @HashColumn(name = "id")
    private String id;

    /**
     * 主机关联策略id
     */
    @HashColumn(name = "strategyId")
    @ListColumn
    private Long strategyId;

    /**
     * 全局策略id
     */
    @HashColumn(name = "expressionId")
    @ListColumn
    private Long expressionId;

    /**
     * 状态
     */
    @HashColumn(name = "status")
    @ListColumn
    private String status;

    /**
     * hostname or projectname
     */
    @HashColumn(name = "endPoint")
    @ListColumn
    private String endPoint;

    /**
     * 值
     */
    @HashColumn(name = "leftValue")
    @ListColumn
    private float leftValue;

    /**
     * 当前次数
     */
    @HashColumn(name = "currentStep")
    @ListColumn
    private Integer currentStep;

    /**
     * 事件时间
     */
    @HashColumn(name = "eventTime")
    @ListColumn
    private Long eventTime;

    /**
     * 推送标签
     */
    @HashColumn(name = "pushedTags")
    @ListColumn
    private String pushedTags;

    /**
     * 优先级
     */
    @HashColumn(name = "priority")
    @ListColumn
    private int priority;

    /**
     * 执行事件id
     */
    @HashColumn(name = "actionId")
    @ListColumn
    private Long actionId;

    /**
     * 备注
     */
    @HashColumn(name = "note")
    @ListColumn
    private String note;

    /**
     * 告警条件
     */
    @HashColumn(name = "func")
    @ListColumn
    private String func;

    /**
     * 阀值
     */
    @HashColumn(name = "rightValue")
    @ListColumn
    private String rightValue;

    /**
     * 指标
     */
    @HashColumn(name = "metric")
    @ListColumn
    private String metric;


    /**
     * 生产eventId
     * @param strategyId
     * @param endPoint
     * @param metric
     * @param tags
     * @return
     */
    public static String genId(Long strategyId,String endPoint,String metric,String tags){

        String pk=StringUtils.EMPTY;
        if(StringUtils.isEmpty(tags)){
             pk=endPoint+"/"+metric+"/"+tags;
        }else {
            pk=endPoint+"/"+metric;
        }

        return strategyId+"_"+ Md5Util.MD5(pk);
    }

}
