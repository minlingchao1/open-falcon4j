package com.lingchaomin.falcon.judge.his;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/18 下午10:01
 * @description
 */
@Component
public class BoostData implements ApplicationListener<ApplicationEvent> {

    private static final Logger LOG= LoggerFactory.getLogger(BoostData.class);

    private static boolean isStart=false;

    public void onApplicationEvent(ApplicationEvent applicationEvent) {

        //避免启动两次
        if(!isStart){
            LOG.warn("初始化数据～～～～～～～～～～～～");
            isStart=true;
            //初始化map
            JudgeItemStore.initJudgeItemMap();
        }

    }
}
