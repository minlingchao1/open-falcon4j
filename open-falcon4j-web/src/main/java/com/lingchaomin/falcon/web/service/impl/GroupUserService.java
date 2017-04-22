package com.lingchaomin.falcon.web.service.impl;

import com.lingchaomin.falcon.common.entity.GroupUser;
import com.lingchaomin.falcon.web.dao.GroupUserDao;
import com.lingchaomin.falcon.web.dao.UserGroupRefDao;
import com.lingchaomin.falcon.web.dto.OperErrorCode;
import com.lingchaomin.falcon.web.dto.OperateResultDto;
import com.lingchaomin.falcon.web.dto.ReqResultFormatter;
import com.lingchaomin.falcon.web.service.IGroupUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 下午3:56
 * @description
 */
@Component
public class GroupUserService implements IGroupUserService {

    @Autowired
    private GroupUserDao groupUserDao;

    @Autowired
    private UserGroupRefDao userGroupRefDao;

    /**
     * 列表
     */
    public OperateResultDto list() {

        List<GroupUser> groupUsers=groupUserDao.selectAll();
        return ReqResultFormatter.formatOperSuccessDto(groupUsers);
    }

    /**
     * 删除
     */
    public OperateResultDto delete(Long id) {

        long ret=groupUserDao.deleteById(id);

        if(ret>0){
            userGroupRefDao.deleteByUserId(id);
            return ReqResultFormatter.formatOperSuccessDto(ret);
        }
        return ReqResultFormatter.formatFailDto(OperErrorCode.FAIL);
    }

    /**
     * 修改
     */
    public OperateResultDto modify(Long id, String name, String phone, String email) {

        GroupUser groupUser=GroupUser.builder()
                .name(name)
                .email(email)
                .phone(phone)
                .id(id)
                .build();

        long ret=groupUserDao.updateById(groupUser);

        if(ret>0){
            return ReqResultFormatter.formatOperSuccessDto(ret);
        }
        return ReqResultFormatter.formatFailDto(OperErrorCode.FAIL);

    }

    /**
     * 添加
     */
    public OperateResultDto add(String name, String phone, String email) {
        GroupUser groupUser=GroupUser.builder()
                .gmtCreate(new Date())
                .gmtModified(new Date())
                .name(name)
                .email(email)
                .phone(phone)
                .build();

        long ret=groupUserDao.insert(groupUser);

        if(ret>0){
            return ReqResultFormatter.formatOperSuccessDto(ret);
        }
        return ReqResultFormatter.formatFailDto(OperErrorCode.FAIL);
    }
}
