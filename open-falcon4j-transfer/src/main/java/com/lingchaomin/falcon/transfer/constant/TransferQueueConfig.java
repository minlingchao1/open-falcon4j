package com.lingchaomin.falcon.transfer.constant;

import com.lingchaomin.falcon.common.entity.JudgeItem;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/21 上午12:00
 * @description 监控信息转换配置
 */
public class TransferQueueConfig {

    public static Map<String,ConcurrentLinkedQueue<JudgeItem>> judgeQueues=new HashMap<>();
}
