package com.lingchaomin.falcon.web.service.impl;


import com.lingchaomin.falcon.common.entity.Expression;
import com.lingchaomin.falcon.web.dao.ExpressionDao;
import com.lingchaomin.falcon.web.dao.TemplateDao;
import com.lingchaomin.falcon.web.dto.OperErrorCode;
import com.lingchaomin.falcon.web.dto.OperateResultDto;
import com.lingchaomin.falcon.web.dto.ReqResultFormatter;
import com.lingchaomin.falcon.web.service.IExpressionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 下午4:30
 * @description
 */
@Component
public class ExpressionService implements IExpressionService {

    private static Logger LOG= LoggerFactory.getLogger(ExpressionService.class);

    @Autowired
    private ExpressionDao expressionDao;

    @Autowired
    private TemplateDao templateDao;


    /**
     * 添加
     */
    @Override
    public OperateResultDto add(String express, String tag, String func, String operator, Integer priority, String note, Float rightValue, Integer maxStep, Long actionId) {

        Expression expression=Expression.builder()
                .actionId(actionId)
                .func(func)
                .gmtCreate(new Date())
                .gmtModified(new Date())
                .maxStep(maxStep)
                .expression(express)
                .note(note)
                .operator(operator)
                .priority(priority)
                .build();

        long ret=expressionDao.insert(expression);

        if(ret>0){
            return ReqResultFormatter.formatOperSuccessDto(ret);
        }
        return ReqResultFormatter.formatFailDto(OperErrorCode.FAIL);
    }

    /**
     * 修改
     */
    @Override
    public OperateResultDto modify(Long id, String express, String tag, String func, String operator, Integer priority, String note, Float rightValue, Integer maxStep,Long actionId) {
        Expression expression=Expression.builder()
                .actionId(actionId)
                .func(func)
                .maxStep(maxStep)
                .expression(express)
                .note(note)
                .operator(operator)
                .priority(priority)
                .id(id)
                .build();

        long ret=expressionDao.updateById(expression);

        if(ret>0){
            return ReqResultFormatter.formatOperSuccessDto(ret);
        }
        return ReqResultFormatter.formatFailDto(OperErrorCode.FAIL);
    }

    /**
     * 删除
     */
    @Override
    public OperateResultDto delete(Long id) {
        long ret=expressionDao.deleteById(id);

        if(ret>0){
            return ReqResultFormatter.formatOperSuccessDto(ret);
        }
        return ReqResultFormatter.formatFailDto(OperErrorCode.FAIL);
    }

    /**
     * 列表
     */
    @Override
    public OperateResultDto list() {
        List<Expression> expressions=expressionDao.selectAll();

        return ReqResultFormatter.formatOperSuccessDto(expressions);
    }
}
