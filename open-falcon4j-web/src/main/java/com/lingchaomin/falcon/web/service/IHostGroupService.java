package com.lingchaomin.falcon.web.service;


import com.lingchaomin.falcon.web.dto.OperateResultDto;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 下午3:45
 * @description
 */
public interface IHostGroupService {

    /**
     * 添加
     * @param grpName
     * @param note
     * @return
     */
    OperateResultDto add(String grpName, String note);

    /**
     * 修改
     * @param grpName
     * @param note
     * @return
     */
    OperateResultDto modify(Long id, String grpName, String note);

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

    /**
     * 绑定host
     * @param hostIds
     * @param hostGrpId
     * @return
     */
    OperateResultDto bindHost(String hostIds, Long hostGrpId);

    /**
     * 绑定模版
     * @param tplId
     * @param hostGrpId
     * @return
     */
    OperateResultDto bindTpl(Long tplId, Long hostGrpId);
}
