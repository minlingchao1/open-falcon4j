package com.lingchaomin.falcon.transfer.sender.task;

import com.lingchaomin.falcon.transfer.constant.JudgeConfig;
import com.lingchaomin.falcon.transfer.constant.TransferQueueConfig;
import com.lingchaomin.falcon.common.api.IJudgeItemService;
import com.lingchaomin.falcon.common.dto.FalconOperResp;
import com.lingchaomin.falcon.common.entity.JudgeItem;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/21 上午1:33
 * @description 监控数据发送任务
 */
@Component
public class SendTask {

    private static final Logger LOG=LoggerFactory.getLogger(SendTask.class);

    @Autowired
    private JudgeConfig judgeConfig;

    @Autowired
    private IJudgeItemService judgeItemService;

    /**
     * 发送到judge模块
     */
    public void send2Judge(){

        String clusters=judgeConfig.getCluster();

        if(StringUtils.isBlank(clusters)){
            LOG.warn("judge clusters is null~~~~");
            return;
        }

        String[] clustersArr=clusters.split(",");

        for(String c:clustersArr){
            ConcurrentLinkedQueue<JudgeItem> judgeItemQueue= TransferQueueConfig.judgeQueues.get(c);

            if(judgeItemQueue.isEmpty()){
                LOG.warn("judge queue key:{} is empty",c);
                continue;
            }

            forward2JudgeTask(judgeItemQueue,c.split(":")[1]);
        }

    }

    /**
     * 处理queue
     * @param judgeItemQueue
     * @param ip
     */
    private void forward2JudgeTask(ConcurrentLinkedQueue<JudgeItem> judgeItemQueue,String ip){

        //每批最大发送量
        int batch=judgeConfig.getBatch();

        List<JudgeItem> judgeItems=new ArrayList<>();

        int i=0;
        while (i<batch){

            if(judgeItemQueue.isEmpty()){
                break;
            }
            JudgeItem judgeItem=judgeItemQueue.poll();
            judgeItems.add(judgeItem);
        }

        //todo 用于统计发送情况 便于监控 后期做
        FalconOperResp resp=judgeItemService.send(judgeItems);
    }
}
