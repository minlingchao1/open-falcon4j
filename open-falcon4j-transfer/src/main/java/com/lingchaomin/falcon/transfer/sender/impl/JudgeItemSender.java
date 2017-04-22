package com.lingchaomin.falcon.transfer.sender.impl;

import com.lingchaomin.falcon.common.util.TagUtil;
import com.lingchaomin.falcon.transfer.constant.NodeRings;
import com.lingchaomin.falcon.transfer.constant.TransferQueueConfig;
import com.lingchaomin.falcon.transfer.sender.IJudgeItemSender;
import com.lingchaomin.falcon.common.entity.JudgeItem;
import com.lingchaomin.falcon.common.entity.MetricValue;
import com.lingchaomin.falcon.common.util.PKUtil;
import com.lingchaomin.falcon.common.util.hash.Node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/21 上午12:20
 * @description 告警数据判断队列发送
 */
@Component
public class JudgeItemSender implements IJudgeItemSender {

    private static final Logger LOG = LoggerFactory.getLogger(JudgeItemSender.class);

    /**
     * 添加到告警队列
     */
    @Override
    public void push2JudgeSendQueue(MetricValue metricValue) {

        String pk = PKUtil.pk(metricValue.getEndPoint(), metricValue.getMetric(), metricValue.getTag());

        Node node = NodeRings.judgeNoedeRing.findNodeByKey(pk);

        if (node == null) {
            LOG.warn("no server node find");
            return;
        }

        JudgeItem judgeItem=JudgeItem.builder()
                .endPoint(metricValue.getEndPoint())
                .metric(metricValue.getMetric())
                .tag(metricValue.getTag())
                .timestamp(metricValue.getTimestamp())
                .value(Float.valueOf(metricValue.getValue()))
                .tags(TagUtil.getTagMap(metricValue.getTag()))
                .build();

        String key=node.getName()+":"+node.getIp();
        ConcurrentLinkedQueue<JudgeItem> judgeItems= TransferQueueConfig.judgeQueues.get(key);
        judgeItems.offer(judgeItem);
    }
}
