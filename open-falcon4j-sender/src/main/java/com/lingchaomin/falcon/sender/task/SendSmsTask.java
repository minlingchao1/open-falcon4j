package com.lingchaomin.falcon.sender.task;


import com.lingchaomin.falcon.common.dto.SmsDto;
import com.lingchaomin.falcon.sender.logic.ISmsAndMailSendLogic;
import com.lingchaomin.falcon.sender.redis.SmsRedisVistor;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 下午3:00
 * @description 发送短信
 */
@Component
public class SendSmsTask {

    private static final Logger LOG= LoggerFactory.getLogger(SendSmsTask.class);

    @Autowired
    private SmsRedisVistor smsRedisVistor;

    @Autowired
    private ISmsAndMailSendLogic smsAndMailSendLogic;


    public static final Integer LIMIT=1000;

    public void sendSms(){

        Long len=smsRedisVistor.llen(smsRedisVistor.getBaseKey());

        if(len==null||len==0){
            LOG.warn("get sms send is null~~~");
            return;
        }

        List<SmsDto> smsDtos=smsRedisVistor.lrange(new SmsDto(),null,0,LIMIT);

        LOG.warn("sendSmsTask start~~ getSmsdto size:{}",smsDtos.size());

        if(CollectionUtils.isEmpty(smsDtos)){
            return;
        }

        for(SmsDto smsDto:smsDtos){
            smsAndMailSendLogic.sendSms(smsDto);
        }

        smsRedisVistor.ltrim(null,0,LIMIT);
    }

}
