package com.lingchaomin.falcon.web.controller;

import com.lingchaomin.falcon.web.dto.OperErrorCode;
import com.lingchaomin.falcon.web.dto.OperateResultDto;
import com.lingchaomin.falcon.web.dto.ReqResultFormatter;
import com.lingchaomin.falcon.web.dto.SelectDto;
import com.lingchaomin.falcon.web.dto.TplDto;
import com.lingchaomin.falcon.web.service.ITemplateService;


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
 * @date 2017/4/11 下午4:08
 * @description
 */
@Controller
@RequestMapping("tpl")
public class TemplateCtrl {

    @Autowired
    private ITemplateService templateService;

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public Object add(String tplName, Long parentId, Long actionId) {

        if (StringUtils.isBlank(tplName)) {
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        if (actionId == null || actionId == 0) {
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }
        OperateResultDto ret = templateService.add(tplName, parentId, actionId);
        return ret;
    }

    @RequestMapping(value = "modify", method = RequestMethod.POST)
    @ResponseBody
    public Object modify(Long id, String tplName, Long parentId, Long actionId) {

        if (id == null || id == 0) {
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        if (id == parentId) {
            return ReqResultFormatter.formatFailDto(OperErrorCode.TPL_PARENT_SAME);
        }

        if (actionId == null || actionId == 0) {
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        OperateResultDto ret = templateService.modify(id, tplName, parentId, actionId);
        return ret;
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public Object delete(Long id) {

        if (id == null || id == 0) {
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        OperateResultDto ret = templateService.delete(id);
        return ret;
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)

    public Object list(Model model) {
        OperateResultDto ret = templateService.list();
        model.addAttribute("templates", ret.getResult());
        return "monitor/template";
    }

    @RequestMapping(value = "getAll", method = RequestMethod.GET)
    @ResponseBody
    public Object getAll() {
        OperateResultDto ret = templateService.list();

        List<TplDto> tplDtoList = (List<TplDto>) ret.getResult();

        List<SelectDto> selectDtos = new ArrayList<>();
        for (TplDto tplDto : tplDtoList) {
            SelectDto selectDto = new SelectDto();
            selectDto.setId(tplDto.getId());
            selectDto.setText(tplDto.getTplName());
            selectDtos.add(selectDto);
        }
        ret.setResult(selectDtos);
        return ret;
    }
}
