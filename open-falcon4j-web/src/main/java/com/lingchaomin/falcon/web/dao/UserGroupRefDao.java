package com.lingchaomin.falcon.web.dao;


import com.lingchaomin.falcon.common.dao.IDao;
import com.lingchaomin.falcon.web.entity.UserGroupRef;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 下午4:37
 * @description
 */
@Repository
public interface UserGroupRefDao extends IDao<UserGroupRef> {

    /**
     * 根据组id查找
     * @param userGrpId
     * @return
     */
    List<String> selectByGrpId(Long userGrpId);

    /**
     * 根据userId删除
     * @param userId
     * @return
     */
    long deleteByUserId(Long userId);
}
