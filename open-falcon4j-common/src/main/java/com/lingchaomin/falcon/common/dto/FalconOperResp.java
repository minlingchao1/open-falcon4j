package com.lingchaomin.falcon.common.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/21 上午1:49
 * @description 操作返回结果
 */
@Data
public class FalconOperResp implements Serializable {

    /**
     * 信息
     */
    private String msg;

    /**
     * 是否成功
     */
    private Boolean isSuccess;
}
