package com.lingchaomin.falcon.web.controller;


import com.lingchaomin.falcon.web.dto.OperErrorCode;
import com.lingchaomin.falcon.web.dto.OperateResultDto;
import com.lingchaomin.falcon.web.dto.ReqResultFormatter;
import com.lingchaomin.falcon.web.service.IHostGroupService;
import com.lingchaomin.falcon.web.service.IHostService;

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
 * @date 2017/4/11 下午3:44
 * @description 项目组/服务器组
 */
@Controller
@RequestMapping("hostgroup")
public class HostGroupCtrl {

    @Autowired
    private IHostGroupService hostGroupService;


    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public Object add(String grpName, String note) {
        if (StringUtils.isBlank(grpName)) {
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }
        OperateResultDto ret = hostGroupService.add(grpName, note);
        return ret;
    }

    @RequestMapping(value = "modify", method = RequestMethod.POST)
    @ResponseBody
    public Object modify(Long id, String grpName, String note) {

        if(id==null||id==0){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        if (StringUtils.isBlank(grpName)) {
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }
        OperateResultDto ret = hostGroupService.modify(id, grpName, note);
        return ret;
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public Object delete(Long id) {

        if(id==null||id==0){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        OperateResultDto ret = hostGroupService.delete(id);
        return ret;
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Object list(Model model) {
        OperateResultDto ret = hostGroupService.list();
        model.addAttribute("hostGroups", ret.getResult());
        return "monitor/host_group";
    }

    @RequestMapping(value = "bindHost", method = RequestMethod.POST)
    @ResponseBody
    public Object bindHost(String hostIds, Long hostGrpId) {

        if(StringUtils.isBlank(hostIds)){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        if(hostGrpId==null||hostGrpId==0){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        OperateResultDto ret = hostGroupService.bindHost(hostIds, hostGrpId);
        return ret;
    }

    @RequestMapping(value = "bindTpl", method = RequestMethod.POST)
    @ResponseBody
    public Object bindTpl(Long tplId, Long hostGrpId) {

        if(hostGrpId==null||hostGrpId==0){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        if(tplId==null||tplId==0){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        OperateResultDto ret = hostGroupService.bindTpl(tplId, hostGrpId);
        return ret;
    }
}
