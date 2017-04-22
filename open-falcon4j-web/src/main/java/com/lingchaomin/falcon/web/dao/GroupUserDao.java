package com.lingchaomin.falcon.web.dao;

import com.lingchaomin.falcon.common.dao.IDao;
import com.lingchaomin.falcon.common.entity.GroupUser;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 下午4:34
 * @description
 */
@Repository
public interface GroupUserDao extends IDao<GroupUser> {

    /**
     * 根据用户组id查找
     * @param userGrpId
     * @return
     */
    List<GroupUser> selectByGrpId(Long userGrpId);

    /**
     * 查找全部
     * @return
     */
    List<GroupUser> selectAll();
}
