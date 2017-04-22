package com.lingchaomin.falcon.web.controller;


import com.lingchaomin.falcon.common.entity.Action;
import com.lingchaomin.falcon.web.dto.OperErrorCode;
import com.lingchaomin.falcon.web.dto.OperateResultDto;
import com.lingchaomin.falcon.web.dto.ReqResultFormatter;
import com.lingchaomin.falcon.web.dto.SelectDto;
import com.lingchaomin.falcon.web.service.IActionService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 下午4:13
 * @description
 */
@Controller
@RequestMapping("action")
public class ActionCtrl {

    @Autowired
    private IActionService actionService;

    @RequestMapping(value = "add",method = RequestMethod.POST)
    @ResponseBody
    public Object add(String name,Long userGrpId,String url,Boolean callback,Boolean beforeCallbackSms,Boolean beforeCallbackMail,Boolean afterCallbackSms,Boolean afterCallbackmail){


        if (StringUtils.isBlank(name)) {
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        if(userGrpId==null||userGrpId==0){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        if(callback&&StringUtils.isBlank(url)){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }


        OperateResultDto ret=actionService.add(name,userGrpId,url,callback,beforeCallbackMail,beforeCallbackSms,afterCallbackmail,afterCallbackSms);
        return ret;
    }

    @RequestMapping(value = "modify",method = RequestMethod.POST)
    @ResponseBody
    public Object modify(Long id,String name,Long userGrpId,String url,Boolean callback,Boolean beforeCallbackSms,Boolean beforeCallbackMail,Boolean afterCallbackSms,Boolean afterCallbackmail){

        if(id==null||id==0){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        if (StringUtils.isBlank(name)) {
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        if(userGrpId==null||userGrpId==0){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        if(callback&&StringUtils.isBlank(url)){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        OperateResultDto ret=actionService.modify(id,name,userGrpId,url,callback,beforeCallbackMail,beforeCallbackSms,afterCallbackmail,afterCallbackSms);
        return ret;
    }

    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @ResponseBody
    public Object delete(Long id){

        if(id==null||id==0){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }
        OperateResultDto ret=actionService.delete(id);
        return ret;
    }

    @RequestMapping(value = "list",method = RequestMethod.GET)
    public Object list(Model model){
        OperateResultDto ret=actionService.list();
        model.addAttribute("actions",ret.getResult());
        return "monitor/action";
    }

    @RequestMapping(value = "getAll",method = RequestMethod.GET)
    @ResponseBody
    public Object getAll(){
        OperateResultDto ret=actionService.list();
        List<Action> actionList=(List<Action>)ret.getResult();
        List<SelectDto> selectDtos=new ArrayList<SelectDto>();

        for(Action action:actionList){
            SelectDto selectDto=new SelectDto();
            selectDto.setId(action.getId());
            selectDto.setText(action.getName());
            selectDtos.add(selectDto);
        }
        ret.setResult(selectDtos);
        return ret;
    }


}
