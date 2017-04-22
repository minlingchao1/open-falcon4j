package com.lingchaomin.falcon.alarm.logic;


import com.lingchaomin.falcon.common.entity.Action;
import com.lingchaomin.falcon.common.entity.Event;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 上午10:57
 * @description
 */
public interface ISmsAndMailLogic {

    /**
     * 创建短信
     */
    void createSms(String phones, String content);

    /**
     * 创建邮件
     */
    void createMail(String mails, String smsContent, String mailContent);

    /**
     * 创建用户-sms关联用于告警合并
     * @param event
     * @param action
     */
    void createUserSms(Event event, Action action);

    /**
     * 创建用户-邮件用于告警合并
     * @param event
     * @param action
     */
    void createUserMail(Event event, Action action);

    /**
     * 生成短信内容
     */
    String generateSmsContent(Event event);

    /**
     * 生产邮件内容
     */
    String generateMailContent(Event event);
}
