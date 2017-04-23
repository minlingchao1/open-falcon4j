package com.lingchaomin.falcon.transfer.sender.task;

import com.lingchaomin.falcon.transfer.constant.TransferConfig;
import com.lingchaomin.falcon.transfer.constant.TransferQueueConfig;
import com.lingchaomin.falcon.common.api.IJudgeItemDubboApi;
import com.lingchaomin.falcon.common.dto.FalconOperResp;
import com.lingchaomin.falcon.common.entity.JudgeItem;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
    private TransferConfig transferConfig;

    @Autowired
    private IJudgeItemDubboApi judgeItemService;

    /**
     * 发送到judge模块
     */
    public void send2Judge(){

        Set<String> keys=TransferQueueConfig.judgeQueues.keySet();

        for(String key:keys){
            ConcurrentLinkedQueue<JudgeItem> judgeItemQueue= TransferQueueConfig.judgeQueues.get(key);

            if(judgeItemQueue.isEmpty()){
                LOG.warn("judge queue key:{} is empty",key);
                continue;
            }

            forward2JudgeTask(judgeItemQueue,key);
        }

    }

    /**
     * 处理queue
     * @param judgeItemQueue
     * @param key
     */
    private void forward2JudgeTask(ConcurrentLinkedQueue<JudgeItem> judgeItemQueue,String key){

        //每批最大发送量
        int batch= transferConfig.getBatch();

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
        FalconOperResp resp=judgeItemService.send(key,judgeItems);
    }
}
