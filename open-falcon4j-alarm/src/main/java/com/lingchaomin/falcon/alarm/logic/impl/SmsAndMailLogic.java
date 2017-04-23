package com.lingchaomin.falcon.alarm.logic.impl;

import com.lingchaomin.falcon.common.dto.MailDto;
import com.lingchaomin.falcon.common.dto.SmsDto;
import com.lingchaomin.falcon.alarm.dto.UserMailDto;
import com.lingchaomin.falcon.alarm.dto.UserSmsDto;
import com.lingchaomin.falcon.alarm.logic.ISmsAndMailLogic;
import com.lingchaomin.falcon.alarm.redis.MailRedisVistor;
import com.lingchaomin.falcon.alarm.redis.SmsRedisVistor;
import com.lingchaomin.falcon.alarm.redis.UserMailRedisVistor;
import com.lingchaomin.falcon.alarm.redis.UserSmsRedisVistor;
import com.lingchaomin.falcon.common.entity.Action;
import com.lingchaomin.falcon.common.entity.Event;
import com.lingchaomin.falcon.common.redis.exception.MissAnnotationException;
import com.lingchaomin.falcon.common.util.DateUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 上午10:57
 * @description 生产告警内容
 */
@Component
public class SmsAndMailLogic implements ISmsAndMailLogic {

    private static final Logger LOG = LoggerFactory.getLogger(SmsAndMailLogic.class);

    @Autowired
    private SmsRedisVistor smsRedisVistor;

    @Autowired
    private MailRedisVistor mailRedisVistor;

    @Autowired
    private UserMailRedisVistor userMailRedisVistor;

    @Autowired
    private UserSmsRedisVistor userSmsRedisVistor;

    /**
     * 创建短信
     */
    public void createSms(String phones, String content) {


        if (StringUtils.isBlank(phones)) {
            return;
        }

        SmsDto sms = SmsDto.builder()
                .content(content)
                .to(phones)
                .build();
        try {
            smsRedisVistor.rpush(sms);
        } catch (MissAnnotationException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * 创建邮件
     */
    public void createMail(String mails, String smsContent, String mailContent) {

        if (StringUtils.isBlank(mails)) {
            return;
        }

        MailDto mailDto = MailDto.builder()
                .content(mailContent)
                .to(mails)
                .subject(smsContent)
                .build();
        try {
            mailRedisVistor.rpush(mailDto);
        } catch (MissAnnotationException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * 创建用户-sms关联用于告警合并
     */
    public void createUserSms(Event event, Action action) {
        String uic = action.getUic();

        if (StringUtils.isBlank(uic)) {
            return;
        }

        String[] uicArr = uic.split("|");

        String[] phonesArr = uicArr[0].split(",");
        for (String phone : phonesArr) {
            UserSmsDto userSmsDto = UserSmsDto.builder()
                    .content(generateSmsContent(event))
                    .priority(event.getPriority())
                    .metric(event.getMetric())
                    .phone(phone)
                    .status(event.getStatus())
                    .build();
            try {
                userSmsRedisVistor.rpush(userSmsDto);
            } catch (MissAnnotationException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 创建用户-邮件用于告警合并
     */
    public void createUserMail(Event event, Action action) {

        String uic = action.getUic();

        if (StringUtils.isBlank(uic)) {
            return;
        }

        String[] uicArr = uic.split("|");

        String[] mailArr = uicArr[1].split(",");

        for (String mail : mailArr) {
            UserMailDto userMailDto = UserMailDto.builder()
                    .content(generateMailContent(event))
                    .priority(event.getPriority())
                    .metric(event.getMetric())
                    .subject(generateSmsContent(event))
                    .email(mail)
                    .status(event.getStatus())
                    .build();
            try {
                userMailRedisVistor.rpush(userMailDto);
            } catch (MissAnnotationException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }


    /**
     * 生成短信内容
     */
    public String generateSmsContent(Event event) {

        StringBuilder sb = new StringBuilder();

        sb.append("优先级");
        sb.append("[" + event.getPriority() + "]");
        sb.append("状态");
        sb.append("[" + event.getStatus() + "]");
        sb.append("服务器");
        sb.append("[" + event.getEndPoint() + "]");
        sb.append("指标");
        sb.append("[" + event.getMetric() + "]");
        sb.append("判断函数");
        sb.append("[" + event.getFunc() + "]");
        sb.append("标签");
        sb.append("[" + event.getPushedTags() + "]");
        sb.append("当前值");
        sb.append("[" + event.getLeftValue() + "]");
        sb.append("阀值");
        sb.append("[" + event.getRightValue() + "]");
        sb.append("时间");
        sb.append(DateUtil.formDateForYMDHMS(event.getEventTime()));
        return sb.toString();
    }

    /**
     * 生产邮件内容
     */
    public String generateMailContent(Event event) {
        return null;
    }
}
