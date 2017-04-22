package com.lingchaomin.falcon.sender.task;



import com.lingchaomin.falcon.common.dto.MailDto;
import com.lingchaomin.falcon.sender.logic.ISmsAndMailSendLogic;
import com.lingchaomin.falcon.sender.redis.MailRedisVistor;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 下午3:09
 * @description 发送邮件
 */
@Component
public class SendMailTask  {

    private static final Logger LOG= LoggerFactory.getLogger(SendMailTask.class);

    @Autowired
    private MailRedisVistor mailRedisVistor;

    @Autowired
    private ISmsAndMailSendLogic smsAndMailSendLogic;

    public static final Integer LIMIT=1000;

    public void sendMail(){

        Long len=mailRedisVistor.llen(mailRedisVistor.getBaseKey());

        if(len==null||len==0){
            LOG.warn("get send  mail is null");
            return;
        }

        List<MailDto> mailDtoList=mailRedisVistor.lrange(new MailDto(),null,0,LIMIT);

        LOG.warn("send  mail task get mailDto size:{}",mailDtoList.size());
        if(CollectionUtils.isEmpty(mailDtoList)){
            return;
        }

        for (MailDto mailDto:mailDtoList){
            smsAndMailSendLogic.sendMail(mailDto);
        }

        mailRedisVistor.ltrim(null,0,LIMIT);

    }
}
