package com.lingchaomin.falcon.web.service;


import com.lingchaomin.falcon.web.dto.OperateResultDto;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 下午3:56
 * @description
 */
public interface IGroupUserService {

    /**
     * 列表
     * @return
     */
    OperateResultDto list();

    /**
     * 删除
     * @param id
     * @return
     */
    OperateResultDto delete(Long id);

    /**
     * 修改
     * @param id
     * @param name
     * @param phone
     * @param email
     * @return
     */
    OperateResultDto modify(Long id, String name, String phone, String email);

    /**
     * 添加
     * @param name
     * @param phone
     * @param email
     * @return
     */
    OperateResultDto add(String name, String phone, String email);
}
