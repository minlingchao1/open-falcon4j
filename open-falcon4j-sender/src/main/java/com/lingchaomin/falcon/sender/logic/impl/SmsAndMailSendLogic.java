package com.lingchaomin.falcon.sender.logic.impl;


import com.lingchaomin.falcon.common.dto.MailDto;
import com.lingchaomin.falcon.common.dto.SmsDto;
import com.lingchaomin.falcon.sender.logic.ISmsAndMailSendLogic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 下午3:07
 * @description
 */
@Component
public class SmsAndMailSendLogic implements ISmsAndMailSendLogic {

    private static final Logger LOG= LoggerFactory.getLogger(SmsAndMailSendLogic.class);

    /**
     * 发送短信
     */
    public void sendSms(SmsDto smsDto) {

        LOG.warn("发送短信:{}",smsDto.toString());

    }

    /**
     * 发送邮件
     */
    public void sendMail(MailDto mailDto) {
        LOG.warn("发送邮件:{}",mailDto.toString());
    }
}
