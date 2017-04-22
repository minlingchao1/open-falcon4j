package com.lingchaomin.falcon.web.dao;


import com.lingchaomin.falcon.common.dao.IDao;
import com.lingchaomin.falcon.common.entity.Strategy;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/10 下午10:04
 * @description 策略dao
 */
@Repository
public interface StrategyDao extends IDao<Strategy> {
    List<Strategy> selectAll();

    /**
     * 根据模版id删除
     * @param id
     */
    void deleteByTplId(Long id);
}
