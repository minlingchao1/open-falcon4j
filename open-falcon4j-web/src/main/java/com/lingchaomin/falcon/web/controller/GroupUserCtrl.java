package com.lingchaomin.falcon.web.controller;

import com.lingchaomin.falcon.common.entity.GroupUser;
import com.lingchaomin.falcon.web.dto.OperErrorCode;
import com.lingchaomin.falcon.web.dto.OperateResultDto;
import com.lingchaomin.falcon.web.dto.ReqResultFormatter;
import com.lingchaomin.falcon.web.dto.SelectDto;
import com.lingchaomin.falcon.web.service.IGroupUserService;

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
 * @date 2017/4/11 下午3:55
 * @description 监控通知用户
 */
@Controller
@RequestMapping("monitoruser")
public class GroupUserCtrl {

    @Autowired
    private IGroupUserService groupUserService;

    @RequestMapping(value = "add",method = RequestMethod.POST)
    @ResponseBody
    public Object add(String name,String phone,String email){

        if(StringUtils.isBlank(name)){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        OperateResultDto ret=groupUserService.add(name,phone,email);
        return ret;
    }

    @RequestMapping(value = "modify",method = RequestMethod.POST)
    @ResponseBody
    public Object modify(Long id,String name,String phone,String email){

        if(id==null||id==0){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        if(StringUtils.isBlank(name)){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }
        OperateResultDto ret=groupUserService.modify(id,name,phone,email);
        return ret;
    }

    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @ResponseBody
    public Object delete(Long id){

        if(id==null||id==0){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        OperateResultDto ret=groupUserService.delete(id);
        return ret;
    }

    @RequestMapping(value = "list",method = RequestMethod.GET)
    public Object list(Model model){
        OperateResultDto ret=groupUserService.list();
        model.addAttribute("users",ret.getResult());
        return "monitor/user";
    }

    @RequestMapping(value = "selectAll",method = RequestMethod.GET)
    @ResponseBody
    public Object selectAll(){
        OperateResultDto ret=groupUserService.list();

        List<GroupUser> users=(List<GroupUser>)ret.getResult();

        List<SelectDto> selectDtos=new ArrayList<SelectDto>();

        for(GroupUser user:users){
            SelectDto selectDto=new SelectDto();
            selectDto.setId(user.getId());
            selectDto.setText(user.getName());
            selectDtos.add(selectDto);
        }

        ret.setResult(selectDtos);
        return ret;
    }

}
