package com.lingchaomin.falcon.common.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/8 下午12:52
 * @description 关联主机的判断条件
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Strategy implements Serializable {

    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    /**
     * 采集指标
     */
    private String metric;

    /**
     * 标签
     */
    private String tag;

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
     * hostname or server name
     */
    private String endPoint;

    /**
     * 最大发送次数
     */
    private Integer maxStep;

    /**
     * 模版id
     */
    private Long tplId;

    /**
     * actionId
     */
    private Long actionId;

    private String tplName;

    private String actionName;


}
