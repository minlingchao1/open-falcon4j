package com.lingchaomin.falcon.common.api;

import com.lingchaomin.falcon.common.dto.FalconOperResp;
import com.lingchaomin.falcon.common.entity.JudgeItem;

import java.util.List;


/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/21 上午1:47
 * @description judgeitem相关api
 */
public interface IJudgeItemService {

    /**
     * 发送告警信息
     * @param judgeItemList
     * @return
     */
    FalconOperResp send(List<JudgeItem> judgeItemList);
}
