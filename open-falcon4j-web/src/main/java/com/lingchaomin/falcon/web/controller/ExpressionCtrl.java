package com.lingchaomin.falcon.web.controller;

import com.lingchaomin.falcon.web.dto.OperErrorCode;
import com.lingchaomin.falcon.web.dto.OperateResultDto;
import com.lingchaomin.falcon.web.dto.ReqResultFormatter;
import com.lingchaomin.falcon.web.service.IExpressionService;

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
 * @date 2017/4/11 下午4:28
 * @description
 */
@Controller
@RequestMapping("expression")
public class ExpressionCtrl {

    @Autowired
    private IExpressionService expressionService;

    @RequestMapping(value = "add",method = RequestMethod.POST)
    @ResponseBody
    public Object add(String express,String tag,String func,String operator,Integer priority,String note,Float rightValue,Integer maxStep,Long actionId){

        if(StringUtils.isBlank(express)){
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

        if(actionId==null||actionId==0){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        OperateResultDto ret=expressionService.add(express,tag,func,operator,priority,note,rightValue,maxStep,actionId);
        return ret;
    }

    @RequestMapping(value = "modify",method = RequestMethod.POST)
    @ResponseBody
    public Object modify(Long id, String express, String tag, String func, String operator, Integer priority, String note, Float rightValue, Integer maxStep,Long actionId){

        if(id==null||id==0){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        if(StringUtils.isBlank(express)){
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

        if(actionId==null||actionId==0){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        OperateResultDto ret=expressionService.modify(id,express,tag,func,operator,priority,note,rightValue,maxStep,actionId);
        return ret;
    }

    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @ResponseBody
    public Object delete(Long id){

        if(id==null||id==0){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        OperateResultDto ret=expressionService.delete(id);
        return ret;
    }

    @RequestMapping(value = "list",method = RequestMethod.GET)
    public Object list(Model model){
        OperateResultDto ret=expressionService.list();
        model.addAttribute("expressions",ret.getResult());
        return "monitor/expression";
    }
}
