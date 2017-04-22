package com.lingchaomin.falcon.web.dto;


import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

import lombok.Data;

/**
 * Author:minlingchao
 * Date: 2016/10/8 17:14
 * Description: 操作返回结果
 */
@Data
public class OperateResultDto implements Serializable {

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 错误信息
     */
    private String msg = StringUtils.EMPTY;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 结果
     */
    private Object result;

    public OperateResultDto(Boolean success) {
        this.success = success;
    }

    public OperateResultDto(Boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public OperateResultDto(Boolean success, String msg, Object results) {
        this.success = success;
        this.msg = msg;
        this.result = results;
    }


}