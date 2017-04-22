package com.lingchaomin.falcon.web.service;



import com.lingchaomin.falcon.web.dto.OperateResultDto;
import com.lingchaomin.falcon.web.dto.TplDto;

import java.util.List;
import java.util.Map;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/10 下午9:46
 * @description
 */
public interface ITemplateService {


    /**
     * 获取所有Tmpl
     */
    Map<Long,TplDto> getAllTemplate();

    /**
     * 过去一个tpl的所有父id
     * @param templateMap
     * @param tplId
     * @return
     */
    List<Long> getParentIds(Map<Long, TplDto> templateMap, List<Long> parentIds, Long tplId);

    /**
     * 添加
     * @param tplName
     * @param parentId
     * @param actionId
     * @return
     */
    OperateResultDto add(String tplName, Long parentId, Long actionId);

    /**
     * 修改
     * @param id
     * @param tplName
     * @param parentId
     * @param actionId
     * @return
     */
    OperateResultDto modify(Long id, String tplName, Long parentId, Long actionId);

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
