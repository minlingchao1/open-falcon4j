package com.lingchaomin.falcon.web.controller;


import com.lingchaomin.falcon.web.dto.OperErrorCode;
import com.lingchaomin.falcon.web.dto.OperateResultDto;
import com.lingchaomin.falcon.web.dto.ReqResultFormatter;
import com.lingchaomin.falcon.web.service.IStrategyService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 下午4:20
 * @description
 */
@Controller
@RequestMapping("strategy")
public class StrategyCtrl  {

    @Autowired
    private IStrategyService strategyLogic;

    @RequestMapping(value = "add",method = RequestMethod.POST)
    @ResponseBody
    public Object add(String metric,String tag,String func,String operator,Integer priority,String note,Float rightValue,Integer maxStep,Long tplId){

        if(StringUtils.isBlank(metric)){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        if(StringUtils.isBlank(func)){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        if(StringUtils.isBlank(operator)){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        if(rightValue==null){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        if(maxStep==null){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        if(tplId==null||tplId==0){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        OperateResultDto ret=strategyLogic.add(metric,tag,func,operator,priority,note,rightValue,maxStep,tplId);
        return ret;
    }

    @RequestMapping(value = "modify",method = RequestMethod.POST)
    @ResponseBody
    public Object modify(Long id, String metric, String tag, String func, String operator, Integer priority, String note, Float rightValue, Integer maxStep, Long tplId, Long actionId){

        if(id==null||id==0){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        if(StringUtils.isBlank(metric)){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        if(StringUtils.isBlank(func)){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        if(StringUtils.isBlank(operator)){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        if(rightValue==null){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        if(maxStep==null){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        if(tplId==null||tplId==0){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }


        OperateResultDto ret=strategyLogic.modify(id,metric,tag,func,operator,priority,note,rightValue,maxStep,tplId,actionId);
        return ret;
    }

    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @ResponseBody
    public Object delete(Long id){

        if(id==null||id==0){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        OperateResultDto ret=strategyLogic.delete(id);
        return ret;
    }

    @RequestMapping(value = "list",method = RequestMethod.GET)
    public Object list(Model model){
        OperateResultDto ret=strategyLogic.list();
        model.addAttribute("strategies",ret.getResult());
        return "monitor/strategy";
    }
}
