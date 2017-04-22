package com.lingchaomin.falcon.web.service;


import com.lingchaomin.falcon.web.dto.OperateResultDto;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 下午4:02
 * @description
 */
public interface IUserGroupService {
    /**
     * 添加
     * @param name
     * @param note
     * @return
     */
    OperateResultDto add(String name, String note);

    /**
     * 修改
     * @param id
     * @param name
     * @param note
     * @return
     */
    OperateResultDto modify(Long id, String name, String note);

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
     * 绑定用户
     * @param userIds
     * @param userGrpId
     * @return
     */
    OperateResultDto bindUser(String userIds, Long userGrpId);
}
