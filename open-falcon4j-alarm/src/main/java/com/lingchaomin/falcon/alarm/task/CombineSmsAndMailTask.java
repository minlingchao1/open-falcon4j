package com.lingchaomin.falcon.alarm.task;



import com.lingchaomin.falcon.alarm.dto.UserMailDto;
import com.lingchaomin.falcon.alarm.dto.UserSmsDto;
import com.lingchaomin.falcon.alarm.logic.ILink2SmsLogic;
import com.lingchaomin.falcon.alarm.logic.ISmsAndMailLogic;
import com.lingchaomin.falcon.alarm.redis.UserMailRedisVistor;
import com.lingchaomin.falcon.alarm.redis.UserSmsRedisVistor;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 上午11:36
 * @description 合并短信和邮件
 */
@Component
public class CombineSmsAndMailTask  {

    @Autowired
    private UserSmsRedisVistor userSmsRedisVistor;

    @Autowired
    private ISmsAndMailLogic smsAndMailLogic;

    @Autowired
    private ILink2SmsLogic link2SmsLogic;

    @Autowired
    private UserMailRedisVistor userMailRedisVistor;

    private static final String LINK="";

    /**
     * 合并短信
     */
    public void combineSms(){

        List<UserSmsDto> userSmsDtos=popAllSmsDto();

        if(CollectionUtils.isEmpty(userSmsDtos)){
            return;
        }

        Map<String,List<UserSmsDto>> map=new HashMap<String, List<UserSmsDto>>();

        for(UserSmsDto userSmsDto:userSmsDtos){
            String key=String.format("%d%s%s%s",userSmsDto.getPriority(),userSmsDto.getStatus(),userSmsDto.getPhone(),userSmsDto.getMetric());

            if(map.containsKey(key)){
                map.get(key).add(userSmsDto);
            }else {
                List<UserSmsDto> list=new ArrayList<UserSmsDto>();
                list.add(userSmsDto);
                map.put(key,list);
            }
        }


        for(Map.Entry<String,List<UserSmsDto>> entry:map.entrySet()){

            List<UserSmsDto> dto=entry.getValue();

            //只有一条直接写入
            if(dto.size()==1){
                smsAndMailLogic.createSms(dto.get(0).getPhone(),dto.get(0).getContent());
            }

            List<String> contentList=new ArrayList<String>();

            for(UserSmsDto userSmsDto:dto){
                contentList.add(userSmsDto.getContent());
            }

            String first=contentList.get(0);

            String contents= StringUtils.join(contentList,",");

            String url=link2SmsLogic.createLink2Sms(contents);

            if(StringUtils.isEmpty(url)){
                return;
            }

            String sms=String.format("P[%d][%s] %d %s  %s/%s",userSmsDtos.get(0).getPriority(),
                    userSmsDtos.get(0).getStatus(),userSmsDtos.size(),userSmsDtos.get(0).getMetric(),
                    LINK,url);

            smsAndMailLogic.createSms(userSmsDtos.get(0).getPhone(),sms);
        }
    }

    /**
     * 合并邮件
     */
    public void combineEmail(){

    }

    private List<UserSmsDto> popAllSmsDto(){

        Long len=userMailRedisVistor.llen(userMailRedisVistor.getBaseKey());

        if(len==null||len==0){
            return new ArrayList<UserSmsDto>();
        }

        List<UserSmsDto>  userSmsDtos= userSmsRedisVistor.lrange(new UserSmsDto(),null,0,-1);
        userSmsRedisVistor.ltrim(null,0,-1);

        return userSmsDtos;
    }

    private List<UserMailDto> popAllMailDto(){

        return null;
    }
}
