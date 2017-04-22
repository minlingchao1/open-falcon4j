package com.lingchaomin.falcon.sender.logic;


import com.lingchaomin.falcon.common.dto.MailDto;
import com.lingchaomin.falcon.common.dto.SmsDto;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 下午3:06
 * @description
 */
public interface ISmsAndMailSendLogic {

    /**
     * 发送短信
     * @param smsDto
     */
    void sendSms(SmsDto smsDto);

    /**
     * 发送邮件
     * @param mailDto
     */
    void sendMail(MailDto mailDto);
}
