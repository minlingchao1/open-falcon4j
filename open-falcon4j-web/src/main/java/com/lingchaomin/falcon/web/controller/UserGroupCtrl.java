package com.lingchaomin.falcon.web.controller;


import com.lingchaomin.falcon.web.dto.OperErrorCode;
import com.lingchaomin.falcon.web.dto.OperateResultDto;
import com.lingchaomin.falcon.web.dto.ReqResultFormatter;
import com.lingchaomin.falcon.web.dto.SelectDto;
import com.lingchaomin.falcon.web.entity.UserGroup;
import com.lingchaomin.falcon.web.service.IUserGroupService;

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
 * @date 2017/4/11 下午4:01
 * @description
 */
@Controller
@RequestMapping("usergroup")
public class UserGroupCtrl {

    @Autowired
    private IUserGroupService userGroupService;

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public Object add(String name, String note) {

        if (StringUtils.isBlank(name)) {
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }
        OperateResultDto ret = userGroupService.add(name, note);
        return ret;
    }

    @RequestMapping(value = "modify", method = RequestMethod.POST)
    @ResponseBody
    public Object modify(Long id, String name, String note) {

        if (id == null || id == 0) {
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        if (StringUtils.isBlank(name)) {
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }
        OperateResultDto ret = userGroupService.modify(id, name, note);
        return ret;
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public Object delete(Long id) {
        if (id == null || id == 0) {
            return ReqResultFormatter.formatFailDto(OperErrorCode.LACK_PARAMS);
        }

        OperateResultDto ret = userGroupService.delete(id);
        return ret;
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)

    public Object list(Model model) {
        OperateResultDto ret = userGroupService.list();
        model.addAttribute("usergroups", ret.getResult());
        return "monitor/user_group";
    }

    @RequestMapping(value = "bindUser", method = RequestMethod.POST)
    @ResponseBody
    public Object bindUser(String userIds, Long userGrpId) {
        OperateResultDto ret = userGroupService.bindUser(userIds, userGrpId);
        return ret;
    }

    @RequestMapping(value = "getAll", method = RequestMethod.GET)
    @ResponseBody
    public Object getAll() {
        OperateResultDto ret = userGroupService.list();
        List<UserGroup> userGroupList = (List<UserGroup>) ret.getResult();

        List<SelectDto> selectDtos = new ArrayList<>();

        for (UserGroup userGroup : userGroupList) {
            SelectDto selectDto = new SelectDto();
            selectDto.setId(userGroup.getId());
            selectDto.setText(userGroup.getGrpName());
            selectDtos.add(selectDto);
        }

        ret.setResult(selectDtos);
        return ret;
    }
}
