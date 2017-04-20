package com.lingchaomin.falcon.transfer.dto;

import com.lingchaomin.falcon.transfer.constant.TransferRespError;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/20 下午9:09
 * @description 信息接收返回信息
 */
@Data
@Builder
public class TransferResp implements Serializable{

    /**
     * 信息
     */
    private String msg;

    /**
     * 是否成功
     */
    private Boolean success;


    /**
     * 失败
     * @param msg
     * @return
     */
    public static TransferResp fail(String msg){
        TransferResp transferResp=TransferResp.builder()
                .msg(msg)
                .success(false)
                .build();
        return transferResp;
    }

    public static TransferResp success(){
        TransferResp transferResp=TransferResp.builder()
                .msg(TransferRespError.SUCCESS.getValueEn())
                .success(true)
                .build();
        return transferResp;
    }
}
