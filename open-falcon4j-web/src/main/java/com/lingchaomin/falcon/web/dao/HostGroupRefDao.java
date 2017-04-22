package com.lingchaomin.falcon.web.dao;

import com.lingchaomin.falcon.common.dao.IDao;
import com.lingchaomin.falcon.web.dto.HostTplDto;
import com.lingchaomin.falcon.web.entity.HostGroupRef;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 下午4:38
 * @description
 */
@Repository
public interface HostGroupRefDao extends IDao<HostGroupRef> {

    /**
     * 获取关联
     * @param hostGrpId
     * @return
     */
    List<String> selectByGrpId(Long hostGrpId);

    /**
     * 根据组id删除
     * @param grpId
     * @return
     */
    long deleteByGrpId(Long grpId);

    /**
     * 查找host和tplId
     * @return
     */
    List<HostTplDto> selectHostTplDto();

    /**
     * 根据host删除
     * @param id
     */
    void deleteByHostId(Long id);
}
