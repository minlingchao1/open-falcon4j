package com.lingchaomin.falcon.web.dao;

import com.lingchaomin.falcon.common.dao.IDao;
import com.lingchaomin.falcon.web.dto.TplDto;
import com.lingchaomin.falcon.web.entity.Template;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/10 下午9:49
 * @description
 */
@Repository
public interface TemplateDao extends IDao<Template> {

    /**
     * 查找全部
     * @return
     */
    List<TplDto> selectAll();


}
