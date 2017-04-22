package com.lingchaomin.falcon.web.service;


import com.lingchaomin.falcon.common.entity.Expression;
import com.lingchaomin.falcon.web.dto.OperateResultDto;

import java.util.List;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 下午4:29
 * @description
 */
public interface IExpressionService {



    /**
     * 添加
     * @param express
     * @param tag
     * @param func
     * @param operator
     * @param priority
     * @param note
     * @param rightValue
     * @param maxStep

     * @return
     */
    OperateResultDto add(String express, String tag, String func, String operator, Integer priority, String note, Float rightValue, Integer maxStep, Long actionId);

    /**
     * 修改
     * @param id
     * @param express
     * @param tag
     * @param func
     * @param operator
     * @param priority
     * @param note
     * @param rightValue
     * @param maxStep
     * @return
     */
    OperateResultDto modify(Long id, String express, String tag, String func, String operator, Integer priority, String note, Float rightValue, Integer maxStep, Long actionId);

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
