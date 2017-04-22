package com.lingchaomin.falcon.web.dao;

import com.lingchaomin.falcon.common.dao.IDao;
import com.lingchaomin.falcon.web.entity.UserGroup;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 下午4:34
 * @description
 */
@Repository
public interface UserGroupDao extends IDao<UserGroup> {
    List<UserGroup> selectAll();
}
