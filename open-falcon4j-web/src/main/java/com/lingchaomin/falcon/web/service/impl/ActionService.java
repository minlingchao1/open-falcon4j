package com.lingchaomin.falcon.web.service.impl;

import com.lingchaomin.falcon.web.dto.OperErrorCode;
import com.lingchaomin.falcon.web.dto.OperateResultDto;
import com.lingchaomin.falcon.common.entity.Action;
import com.lingchaomin.falcon.common.entity.GroupUser;
import com.lingchaomin.falcon.web.dao.ActionDao;
import com.lingchaomin.falcon.web.dao.GroupUserDao;
import com.lingchaomin.falcon.web.dto.ReqResultFormatter;
import com.lingchaomin.falcon.web.service.IActionService;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 上午10:35
 * @description  告警触发事件
 */
@Component
public class ActionService implements IActionService {

    @Autowired
    private GroupUserDao groupUserDao;

    @Autowired
    private ActionDao actionDao;

    /**
     * 获取action
     */
    public Action getActionById(Long id) {
        return actionDao.selectById(id);
    }

    /**
     * 添加
     */
    @Override
    public OperateResultDto add(String name, Long userGrpId, String url, Boolean callback, Boolean beforeCallbackMail, Boolean beforeCallbackSms, Boolean afterCallbackmail, Boolean afterCallbackSms) {

        List<GroupUser> groupUserList=groupUserDao.selectByGrpId(userGrpId);

        List<String> phones=new ArrayList<String>();
        List<String> mails=new ArrayList<String>();

        for(GroupUser groupUser:groupUserList){
            phones.add(groupUser.getPhone());
            mails.add(groupUser.getEmail());
        }

        String uic= StringUtils.join(phones,",")+"|"+StringUtils.join(mails,",");

        Action action=Action.builder()
                .userGrpId(userGrpId)
                .uic(uic)
                .afterCallbackmail(afterCallbackmail)
                .afterCallbackSms(afterCallbackSms)
                .beforeCallbackMail(beforeCallbackMail)
                .beforeCallbackSms(beforeCallbackSms)
                .callback(callback)
                .url(url)
                .name(name)
                .gmtCreate(new Date())
                .gmtModified(new Date())
                .build();


        long ret=actionDao.insert(action);

        if(ret>0){
            return ReqResultFormatter.formatOperSuccessDto(ret);
        }

        return ReqResultFormatter.formatFailDto(OperErrorCode.FAIL);
    }

    /**
     * 修改
     */
    @Override
    public OperateResultDto modify(Long id, String name, Long userGrpId, String url, Boolean callback, Boolean beforeCallbackMail, Boolean beforeCallbackSms, Boolean afterCallbackmail, Boolean afterCallbackSms) {

        List<GroupUser> groupUserList=groupUserDao.selectByGrpId(userGrpId);

        List<String> phones=new ArrayList<String>();
        List<String> mails=new ArrayList<String>();

        for(GroupUser groupUser:groupUserList){
            phones.add(groupUser.getPhone());
            mails.add(groupUser.getEmail());
        }

        String uic= StringUtils.join(phones,",")+"|"+StringUtils.join(mails,",");

        Action action=Action.builder()
                .uic(uic)
                .userGrpId(userGrpId)
                .afterCallbackmail(afterCallbackmail)
                .afterCallbackSms(afterCallbackSms)
                .beforeCallbackMail(beforeCallbackMail)
                .beforeCallbackSms(beforeCallbackSms)
                .callback(callback)
                .url(url)
                .name(name)
                .id(id)
                .build();

        long ret=actionDao.updateById(action);


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

        long ret=actionDao.deleteById(id);

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

        List<Action> actions=actionDao.selectAll();
        return ReqResultFormatter.formatOperSuccessDto(actions);
    }
}
