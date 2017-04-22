package com.lingchaomin.falcon.alarm.service.impl;



import com.lingchaomin.falcon.alarm.logic.ISmsAndMailLogic;
import com.lingchaomin.falcon.alarm.service.IAlarmService;
import com.lingchaomin.falcon.common.api.IActionDubboApi;
import com.lingchaomin.falcon.common.entity.Action;
import com.lingchaomin.falcon.common.entity.Event;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/10 下午3:27
 * @description 告警事件处理
 */
@Service
public class AlarmService implements IAlarmService {

    @Autowired
    private IActionDubboApi actionDubboApi;

    @Autowired
    private ISmsAndMailLogic smsAndMailLogic;


    /**
     * 事件消费
     */
    public void consumeEvent(Event event, Boolean isHighPrority) {

        if(event.getActionId()<=0){
            return;
        }

        Action action=actionDubboApi.getActionById(event.getActionId());

        if(action==null){
            return;
        }

        if(action.getCallback()){
            handleCallback(event,action);
            return;
        }

        if(isHighPrority){
            consumeHighEvents(event,action);
        }else {
            consumerLowEvents(event,action);
        }
    }

    /**
     * 处理低等级事件
     * @param event
     * @param action
     */
    private void consumerLowEvents(Event event, Action action) {

        if(StringUtils.isBlank(action.getUic())){
            return;
        }

        if(event.getPriority()<3){
             smsAndMailLogic.createUserSms(event,action);
        }

        smsAndMailLogic.createUserMail(event,action);
    }

    /**
     * 处理高等级事件 高优先级的不做报警合并
     * @param event
     * @param action
     */
    private void consumeHighEvents(Event event, Action action) {

        if(StringUtils.isBlank(action.getUic())){
            return;
        }

        String[] uicArr=action.getUic().split("|");


        String smsContent=smsAndMailLogic.generateSmsContent(event);
        String mailContent=smsAndMailLogic.generateMailContent(event);
        if(event.getPriority()<3){
            smsAndMailLogic.createSms(uicArr[0],smsContent);
        }

        smsAndMailLogic.createMail(uicArr[1],smsContent,mailContent);
    }

    /**
     * 处理回调事件
     * @param event
     * @param action
     */
    private void handleCallback(Event event,Action action){

        String uic=action.getUic();

        String phones=StringUtils.EMPTY;

        String emails=StringUtils.EMPTY;

        if(StringUtils.isNoneBlank(uic)){
             phones=uic.split("|")[0];
             emails=uic.split("|")[1];
        }


        String smsContent=smsAndMailLogic.generateSmsContent(event);
        String emailContent=smsAndMailLogic.generateSmsContent(event);


        if(action.getBeforeCallbackSms()){
            String content=smsAndMailLogic.generateSmsContent(event);
            smsAndMailLogic.createSms(phones,smsContent);
        }

        if(action.getBeforeCallbackMail()){
            String content=smsAndMailLogic.generateMailContent(event);
            smsAndMailLogic.createMail(emails,smsContent,emailContent);
        }

        String message=callback(event,action);

        if(action.getAfterCallbackSms()){
            smsAndMailLogic.createSms(phones,message);
        }

        if(action.getAfterCallbackmail()){
            smsAndMailLogic.createMail(emails,message,message);
        }
    }

    private String callback(Event event,Action action){
        if(StringUtils.isBlank(action.getUrl())){
            return "call back url is null";
        }

        List<Map<String,String>> tags=new ArrayList<Map<String, String>>();

        //转换标签
        if(StringUtils.isNoneBlank(event.getPushedTags())){
            String[] tagsArr=event.getPushedTags().split(",");

            for(String s:tagsArr){
                Map<String,String> map=new HashMap<String, String>();
                map.put(s.split("=")[0],s.split("=")[1]);
                tags.add(map);
            }
        }

        String tagsStr=StringUtils.join(tags,",");

        //todo 回调函数地址处理

//        NameValuePair[] nameValuePairs=new NameValuePair[]{
//                new NameValuePair("endpoint",event.getEndPoint()),
//                new NameValuePair("metric",event.getMetric()),
//
//        }

        return null;
    }
}
