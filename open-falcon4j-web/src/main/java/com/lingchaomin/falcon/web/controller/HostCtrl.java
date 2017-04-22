package com.lingchaomin.falcon.web.controller;

import com.lingchaomin.falcon.web.dto.OperErrorCode;
import com.lingchaomin.falcon.web.dto.OperateResultDto;
import com.lingchaomin.falcon.web.dto.ReqResultFormatter;
import com.lingchaomin.falcon.web.dto.SelectDto;
import com.lingchaomin.falcon.web.entity.Host;
import com.lingchaomin.falcon.web.service.IHostService;

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
 * @date 2017/4/11 下午3:33
 * @description HOST/PROJECT
 */
@Controller
@RequestMapping("host")
public class HostCtrl {

    @Autowired
    private IHostService hostService;

    /**
     * 添加host
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public Object add(String hostName, String note) {
        if(StringUtils.isBlank(hostName)){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }
        OperateResultDto ret = hostService.add(hostName, note);
        return ret;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "modify", method = RequestMethod.POST)
    @ResponseBody
    public Object modify(Long id, String hostName, String note) {

        if(id==null||id==0){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }


        if(StringUtils.isBlank(hostName)){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }
        OperateResultDto ret = hostService.modify(id, hostName, note);
        return ret;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public Object delete(Long id) {

        if(id==null||id==0){
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        OperateResultDto ret = hostService.delete(id);
        return ret;
    }

    /**
     * 列表
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Object list(Model model) {
        OperateResultDto ret = hostService.list();
        model.addAttribute("hosts", ret.getResult());
        return "monitor/host";
    }

    @RequestMapping(value = "getAll", method = RequestMethod.GET)
    @ResponseBody
    public Object getAll() {
        OperateResultDto ret = hostService.list();
        List<Host> hostList = (List<Host>) ret.getResult();

        List<SelectDto> selectDtos = new ArrayList<>();

        for (Host host : hostList) {
            SelectDto selectDto = new SelectDto();
            selectDto.setId(host.getId());
            selectDto.setText(host.getHostName());
            selectDtos.add(selectDto);
        }
        ret.setResult(selectDtos);
        return ret;
    }


}
