package com.lingchaomin.falcon.judge.service;


import com.lingchaomin.falcon.common.entity.JudgeItem;
import com.lingchaomin.falcon.judge.his.JudgeItemQueue;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/8 下午12:23
 * @description 告警判断
 */
public interface IJudgeService {


    /**
     * 告警判断
     * @param judgeItem
     */
    void judge(JudgeItemQueue judgeItemQueue, JudgeItem judgeItem);
}
