package com.lingchaomin.falcon.web.dao;


import com.lingchaomin.falcon.common.dao.IDao;
import com.lingchaomin.falcon.web.entity.HostGroup;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 下午4:37
 * @description
 */
@Repository
public interface HostGroupDao extends IDao<HostGroup> {

    /**
     * 查找全部
     * @return
     */
    List<HostGroup> selectAll();
}
