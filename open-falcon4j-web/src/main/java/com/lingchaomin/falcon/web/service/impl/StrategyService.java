package com.lingchaomin.falcon.web.service.impl;


import com.lingchaomin.falcon.common.entity.Strategy;
import com.lingchaomin.falcon.web.dao.StrategyDao;
import com.lingchaomin.falcon.web.dao.TemplateDao;
import com.lingchaomin.falcon.web.dto.OperErrorCode;
import com.lingchaomin.falcon.web.dto.OperateResultDto;
import com.lingchaomin.falcon.web.dto.ReqResultFormatter;
import com.lingchaomin.falcon.web.entity.Template;
import com.lingchaomin.falcon.web.service.IGroupHostService;
import com.lingchaomin.falcon.web.service.IHostService;
import com.lingchaomin.falcon.web.service.IStrategyService;
import com.lingchaomin.falcon.web.service.ITemplateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/10 下午9:41
 * @description 策略相关
 */
@Component
public class StrategyService implements IStrategyService{

    @Autowired
    private StrategyDao strategyDao;

    @Autowired
    private TemplateDao templateDao;

    /**
     * 添加
     */
    public OperateResultDto add(String metric, String tag, String func, String operator, Integer priority, String note, Float rightValue, Integer maxStep, Long tplId) {
        Template template=templateDao.selectById(tplId);
        Strategy strategy=Strategy.builder()
                .actionId(template.getActionId())
                .func(func)
                .gmtCreate(new Date())
                .gmtModified(new Date())
                .maxStep(maxStep)
                .metric(metric)
                .note(note)
                .operator(operator)
                .priority(priority)
                .rightValue(rightValue)
                .tag(tag)
                .tplId(tplId)
                .build();

        long ret=strategyDao.insert(strategy);

        if(ret>0){
            return ReqResultFormatter.formatOperSuccessDto(ret);
        }

        return ReqResultFormatter.formatFailDto(OperErrorCode.FAIL);
    }

    /**
     * 修改
     */
    public OperateResultDto modify(Long id, String metric, String tag, String func, String operator, Integer priority, String note, Float rightValue, Integer maxStep, Long tplId,Long actionId) {
        Template template=templateDao.selectById(tplId);
        Strategy strategy=Strategy.builder()
                .actionId(template.getActionId())
                .func(func)
                .maxStep(maxStep)
                .metric(metric)
                .note(note)
                .operator(operator)
                .priority(priority)
                .rightValue(rightValue)
                .tag(tag)
                .tplId(tplId)
                .id(id)
                .build();

        long ret=strategyDao.updateById(strategy);

        if(ret>0){
            return ReqResultFormatter.formatOperSuccessDto(ret);
        }

        return ReqResultFormatter.formatFailDto(OperErrorCode.FAIL);
    }

    /**
     * 删除
     */
    public OperateResultDto delete(Long id) {
        long ret=strategyDao.deleteById(id);

        if(ret>0){
            return ReqResultFormatter.formatOperSuccessDto(ret);
        }

        return ReqResultFormatter.formatFailDto(OperErrorCode.FAIL);
    }

    /**
     * 列表
     */
    public OperateResultDto list() {
        List<Strategy> strategies=strategyDao.selectAll();

        return ReqResultFormatter.formatOperSuccessDto(strategies);
    }


}
