package com.lingchaomin.falcon.web.service;


import com.lingchaomin.falcon.common.entity.Strategy;
import com.lingchaomin.falcon.web.dto.OperateResultDto;

import java.util.List;
import java.util.Map;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/10 下午9:41
 * @description
 */
public interface IStrategyService {



    /**
     * 添加
     * @param metric
     * @param tag
     * @param func
     * @param operator
     * @param priority
     * @param note
     * @param rightValue
     * @param maxStep
     * @param tplId
     * @return
     */
    OperateResultDto add(String metric, String tag, String func, String operator, Integer priority, String note, Float rightValue, Integer maxStep, Long tplId);

    /**
     * 修改
     * @param id
     * @param metric
     * @param tag
     * @param func
     * @param operator
     * @param priority
     * @param note
     * @param rightValue
     * @param maxStep
     * @param tplId
     * @return
     */
    OperateResultDto modify(Long id, String metric, String tag, String func, String operator, Integer priority, String note, Float rightValue, Integer maxStep, Long tplId, Long actionId);

    /**
     * 删除
     * @param id
     * @return
     */
    OperateResultDto delete(Long id);

    /**
     * 列表
     * @return
     */
    OperateResultDto list();
}
