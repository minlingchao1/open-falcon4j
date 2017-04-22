package com.lingchaomin.falcon.web.service.impl;

import com.lingchaomin.falcon.web.dao.UserGroupDao;
import com.lingchaomin.falcon.web.dao.UserGroupRefDao;
import com.lingchaomin.falcon.web.dto.OperErrorCode;
import com.lingchaomin.falcon.web.dto.OperateResultDto;
import com.lingchaomin.falcon.web.dto.ReqResultFormatter;
import com.lingchaomin.falcon.web.entity.UserGroup;
import com.lingchaomin.falcon.web.entity.UserGroupRef;
import com.lingchaomin.falcon.web.service.IUserGroupService;


import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 下午4:02
 * @description
 */
@Component
public class UserGroupService implements IUserGroupService {

    @Autowired
    private UserGroupDao userGroupDao;

    @Autowired
    private UserGroupRefDao userGroupRefDao;
    /**
     * 添加
     */
    public OperateResultDto add(String name, String note) {

        UserGroup userGroup=UserGroup.builder()
                .gmtCreate(new Date())
                .gmtModified(new Date())
                .grpName(name)
                .note(note)
                .build();

        long ret=userGroupDao.insert(userGroup);

        if(ret>0){
            return ReqResultFormatter.formatOperSuccessDto(ret);
        }
        return ReqResultFormatter.formatFailDto(OperErrorCode.FAIL);
    }

    /**
     * 修改
     */
    public OperateResultDto modify(Long id, String name, String note) {

        UserGroup userGroup=UserGroup.builder()
                .gmtCreate(new Date())
                .gmtModified(new Date())
                .grpName(name)
                .note(note)
                .id(id)
                .build();

        long ret=userGroupDao.updateById(userGroup);

        if(ret>0){
            return ReqResultFormatter.formatOperSuccessDto(ret);
        }
        return ReqResultFormatter.formatFailDto(OperErrorCode.FAIL);
    }

    /**
     * 删除
     */
    public OperateResultDto delete(Long id) {
        long ret=userGroupDao.deleteById(id);

        if(ret>0){
            return ReqResultFormatter.formatOperSuccessDto(ret);
        }
        return ReqResultFormatter.formatFailDto(OperErrorCode.FAIL);
    }

    /**
     * 列表
     */
    public OperateResultDto list() {
        List<UserGroup> ret=userGroupDao.selectAll();

        return ReqResultFormatter.formatOperSuccessDto(ret);
    }

    /**
     * 绑定用户
     */
    public OperateResultDto bindUser(String userIds, Long userGrpId) {

        List<String> olduserIds=userGroupRefDao.selectByGrpId(userGrpId);

        if(CollectionUtils.isNotEmpty(olduserIds)){
            for(String old:olduserIds){
                if(!userIds.contains(old)){
                    userGroupRefDao.deleteById(Long.valueOf(old));
                }
            }
        }

        List<UserGroupRef> userGroupRefs=new ArrayList<UserGroupRef>();

        for(String s:userIds.split(",")){

            UserGroupRef userGroupRef=UserGroupRef.builder()
                    .grpUserId(Long.valueOf(s))
                    .gmtCreate(new Date())
                    .gmtModified(new Date())
                    .grpId(userGrpId)
                    .build();
            userGroupRefs.add(userGroupRef);
        }


        long ret=userGroupRefDao.insertBatch(userGroupRefs);

        if(ret>0){
            return ReqResultFormatter.formatOperSuccessDto(ret);
        }
        return ReqResultFormatter.formatFailDto(OperErrorCode.FAIL);
    }
}
