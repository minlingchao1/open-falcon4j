package com.lingchaomin.falcon.web.service;


import com.lingchaomin.falcon.web.dto.OperateResultDto;
import com.lingchaomin.falcon.common.entity.Action;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 上午10:35
 * @description
 */
public interface IActionService {

    /**
     * 获取action
     * @param Id
     * @return
     */
    Action getActionById(Long Id);

    /**
     * 添加
     * @param name
     * @param userGrpId
     * @param url
     * @param callback
     * @param beforeCallbackMail
     * @param beforeCallbackSms
     * @param afterCallbackmail
     * @param afterCallbackSms
     * @return
     */
    OperateResultDto add(String name, Long userGrpId, String url, Boolean callback, Boolean beforeCallbackMail, Boolean beforeCallbackSms, Boolean afterCallbackmail, Boolean afterCallbackSms);

    /**
     * 修改
     * @param id
     * @param name
     * @param userGrpId
     * @param url
     * @param callback
     * @param beforeCallbackMail
     * @param beforeCallbackSms
     * @param afterCallbackmail
     * @param afterCallbackSms
     * @return
     */
    OperateResultDto modify(Long id, String name, Long userGrpId, String url, Boolean callback, Boolean beforeCallbackMail, Boolean beforeCallbackSms, Boolean afterCallbackmail, Boolean afterCallbackSms);

    /**
     * 删除
     * @param id
     * @return
     */
    OperateResultDto delete(Long id);

    /**
     * 列表
     * @return
     */
    OperateResultDto list();
}
