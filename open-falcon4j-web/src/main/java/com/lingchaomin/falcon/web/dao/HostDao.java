package com.lingchaomin.falcon.web.dao;


import com.lingchaomin.falcon.common.dao.IDao;
import com.lingchaomin.falcon.web.entity.Host;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/10 下午10:30
 * @description
 */
@Repository
public interface HostDao extends IDao<Host> {
    List<Host> selectAll();

    List<String> selectByGrpId(Long grpId);
}
