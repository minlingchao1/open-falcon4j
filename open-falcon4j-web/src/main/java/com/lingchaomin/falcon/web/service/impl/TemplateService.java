package com.lingchaomin.falcon.web.service.impl;


import com.lingchaomin.falcon.web.cache.CacheConfig;
import com.lingchaomin.falcon.web.dao.StrategyDao;
import com.lingchaomin.falcon.web.dao.TemplateDao;
import com.lingchaomin.falcon.web.dto.OperErrorCode;
import com.lingchaomin.falcon.web.dto.OperateResultDto;
import com.lingchaomin.falcon.web.dto.ReqResultFormatter;
import com.lingchaomin.falcon.web.dto.TplDto;
import com.lingchaomin.falcon.web.entity.Template;
import com.lingchaomin.falcon.web.service.ITemplateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/10 下午9:47
 * @description 模版相关逻辑
 */
@Component
public class TemplateService implements ITemplateService {

    @Autowired
    private TemplateDao templateDao;

    @Autowired
    private StrategyDao strategyDao;

    /**
     * 获取所有Tmpl
     */
    @Override
    public Map<Long,TplDto>  getAllTemplate() {

        Map<Long,TplDto> templatesMap= CacheConfig.templateCache.getIfPresent(CacheConfig.CACHE_TEMPLTE);

        if(templatesMap==null){
             List<TplDto> templates=templateDao.selectAll();
             templatesMap=new HashMap<Long, TplDto>();

             for(TplDto template:templates){
                 templatesMap.put(template.getId(),template);
             }

            CacheConfig.templateCache.put(CacheConfig.CACHE_TEMPLTE,templatesMap);
        }
        return templatesMap;
    }

    /**
     * 过去一个tpl的所有父id
     */
    @Override
    public List<Long> getParentIds(Map<Long, TplDto> templateMap, List<Long> parentIds,Long tplId) {

        //System.out.println(tplId);
        if(tplId==null||tplId<=0){

            return parentIds;
        }
        for(Map.Entry<Long,TplDto> entry:templateMap.entrySet()){

            if(entry.getKey()==tplId){
                parentIds.add(tplId);

                tplId=entry.getValue().getParentId();
            }
        }
        return  getParentIds(templateMap,parentIds,tplId);

    }

    /**
     * 添加
     */
    public OperateResultDto add(String tplName, Long parentId, Long actionId) {

        Template template=Template.builder()
                .tplName(tplName)
                .parentId(parentId)
                .actionId(actionId)
                .gmtCreate(new Date())
                .gmtModified(new Date())
                .build();

        long ret=templateDao.insert(template);

        if(ret>0){

            return ReqResultFormatter.formatOperSuccessDto(ret);
        }
        return ReqResultFormatter.formatFailDto(OperErrorCode.FAIL);
    }

    /**
     * 修改
     */
    public OperateResultDto modify(Long id, String tplName, Long parentId, Long actionId) {

        if(parentId==id){
            return ReqResultFormatter.formatFailDto(OperErrorCode.FAIL);
        }
        Template template=Template.builder()
                .tplName(tplName)
                .parentId(parentId)
                .actionId(actionId)
                .gmtCreate(new Date())
                .gmtModified(new Date())
                .id(id)
                .build();

        long ret=templateDao.updateById(template);

        if(ret>0){
            return ReqResultFormatter.formatOperSuccessDto(ret);
        }
        return ReqResultFormatter.formatFailDto(OperErrorCode.FAIL);
    }

    /**
     * 删除
     */
    public OperateResultDto delete(Long id) {
        long ret=templateDao.deleteById(id);

        if(ret>0){
            strategyDao.deleteByTplId(id);
            return ReqResultFormatter.formatOperSuccessDto(ret);
        }
        return ReqResultFormatter.formatFailDto(OperErrorCode.FAIL);
    }

    /**
     * 列表
     */
    public OperateResultDto list() {
        List<TplDto> templates=templateDao.selectAll();
        return ReqResultFormatter.formatOperSuccessDto(templates);
    }
}
