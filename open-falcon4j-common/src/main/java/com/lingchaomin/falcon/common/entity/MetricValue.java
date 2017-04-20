package com.lingchaomin.falcon.common.entity;


import com.lingchaomin.falcon.common.redis.annotation.List;
import com.lingchaomin.falcon.common.redis.annotation.ListColumn;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/8 下午6:54
 * @description 监控数据采集
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@List(key = "metric")
public class MetricValue implements Serializable {

    /**
     * 时间戳
     */
    @ListColumn
    private Long timestamp;

    /**
     * 采集指标
     */
    @ListColumn
    private String metric;

    /**
     * hostname or projetName
     */
    @ListColumn
    private String endPoint;

    /**
     * 标签
     */
    @ListColumn
    private String tag;

    /**
     * 采集值
     */
    @ListColumn
    private String value;

    /**
     * 采集步长
     */
    @ListColumn
    private int step;
}
