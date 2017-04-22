package com.lingchaomin.falcon.web.dao;


import com.lingchaomin.falcon.common.dao.IDao;
import com.lingchaomin.falcon.common.entity.Action;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 下午4:35
 * @description
 */
@Repository
public interface ActionDao extends IDao<Action> {

    /**
     * 查找全部
     * @return
     */
    List<Action> selectAll();
}
