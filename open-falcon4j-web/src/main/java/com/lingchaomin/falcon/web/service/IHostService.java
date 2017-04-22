package com.lingchaomin.falcon.web.service;


import com.lingchaomin.falcon.web.dto.OperateResultDto;
import com.lingchaomin.falcon.web.entity.Host;

import java.util.Map;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/10 下午10:27
 * @description host相关
 */
public interface IHostService {

    /**
     * 获取所有host
     * @return
     */
    Map<Long,Host> getAllHost();


    /**
     * 添加
     * @param hostName
     * @param note
     */
    OperateResultDto add(String hostName, String note);

    /**
     * 修改
     * @param id
     * @param hostName
     * @param note
     * @return
     */
    OperateResultDto modify(Long id, String hostName, String note);

    /**
     * 删除
     * @param id
     * @return
     */
    OperateResultDto delete(Long id);

    /**
     * 获取列表
     * @return
     */
    OperateResultDto list();
}
