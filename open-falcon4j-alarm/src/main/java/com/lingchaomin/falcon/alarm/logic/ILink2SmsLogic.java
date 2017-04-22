package com.lingchaomin.falcon.alarm.logic;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 下午2:47
 * @description 多条短信生产链接
 */
public interface ILink2SmsLogic {

    /**
     * 为多条短信生产链接
     * @param content
     * @return
     */
    String createLink2Sms(String content);
}
