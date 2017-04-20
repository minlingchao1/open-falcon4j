package com.lingchaomin.falcon.common.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/10 下午3:56
 * @description 事件处理action
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Action implements Serializable {

    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    /**
     * 名称
     */
    private String name;

    /**
     * 用户组id
     */
    private Long userGrpId;

    /**
     * 发送对象(phone,phone|email,email)
     */
    private String  uic;

    /**
     * 回调地址
     */
    private String url;

    /**
     * 是否进行回调
     */
    private Boolean callback;

    /**
     * 是否在回调之前发送短信
     */
    private Boolean beforeCallbackSms;

    /**
     * 是否在回调之前发送邮件
     */
    private Boolean beforeCallbackMail;

    /**
     * 是否在回调之后发送短信
     */
    private Boolean afterCallbackSms;

    /**
     * 是否在回调之后发送邮件
     */
    private Boolean afterCallbackmail;


}
