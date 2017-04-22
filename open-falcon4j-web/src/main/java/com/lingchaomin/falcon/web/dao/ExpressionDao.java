package com.lingchaomin.falcon.web.dao;


import com.lingchaomin.falcon.common.dao.IDao;
import com.lingchaomin.falcon.common.entity.Expression;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 下午4:36
 * @description
 */
@Repository
public interface ExpressionDao extends IDao<Expression> {

    /**
     * 查找全部
     * @return
     */
    List<Expression> selectAll();

}
