package com.lingchaomin.falcon.judge.logic;


import com.lingchaomin.falcon.common.entity.Expression;
import com.lingchaomin.falcon.common.entity.Strategy;
import com.lingchaomin.falcon.judge.his.JudgeItemQueue;

import java.util.Map;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/8 下午10:32
 * @description 解析策略
 */
public interface IParseFuncLogic {

    /**
     * 解析策略生成
     *
     */
    Map<String,Object> parseFuncFromString(JudgeItemQueue judgeItemQueue, Strategy strategy);

    /**
     * 解析策略生成
     * @param judgeItemQueue
     * @param expression
     * @return
     */
    Map<String,Object> parseFuncFromString(JudgeItemQueue judgeItemQueue, Expression expression);
}
