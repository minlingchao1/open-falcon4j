package com.lingchaomin.falcon.judge.dubbo.impl;

import com.lingchaomin.falcon.common.api.IJudgeItemDubboApi;
import com.lingchaomin.falcon.common.dto.FalconOperResp;
import com.lingchaomin.falcon.common.entity.JudgeItem;
import com.lingchaomin.falcon.judge.constant.CheckIsJudgeProperty;
import com.lingchaomin.falcon.judge.his.JudgeItemQueue;
import com.lingchaomin.falcon.judge.his.JudgeItemStore;
import com.lingchaomin.falcon.judge.service.IJudgeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/21 下午3:30
 * @description 接收监控数据
 */
@Service
public class JudgeItemDubboApi implements IJudgeItemDubboApi {

    @Autowired
    private IJudgeService judgeService;
    /**
     * 发送告警信息
     */
    @Override
    public FalconOperResp send(List<JudgeItem> judgeItemList) {

        for (JudgeItem judgeItem:judgeItemList){
            //放入到总队列中
            EnumMap<CheckIsJudgeProperty, Object> retMap=checkNeedJudge(judgeItem);
            boolean isJudge=(boolean)retMap.get(CheckIsJudgeProperty.IS_JUDGE);
            if(isJudge){
                JudgeItemQueue judgeItemQueue=(JudgeItemQueue)retMap.get(CheckIsJudgeProperty.JUDGE_ITEM_QUEUE);
                judgeService.judge(judgeItemQueue,judgeItem);
            }
        }

        FalconOperResp falconOperResp=FalconOperResp.builder()
                .msg("success")
                .isSuccess(true)
                .build();

        return falconOperResp;
    }

    /**
     * 检测是否需要告警判断
     */
    private EnumMap<CheckIsJudgeProperty, Object> checkNeedJudge(JudgeItem judgeItem) {
        return JudgeItemStore.pushFrontAndMaintain(judgeItem);
    }
}
