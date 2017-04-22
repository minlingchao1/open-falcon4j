package com.lingchaomin.falcon.web.service.impl;

import com.lingchaomin.falcon.web.dao.HostDao;
import com.lingchaomin.falcon.web.dao.HostGroupDao;
import com.lingchaomin.falcon.web.dao.HostGroupRefDao;
import com.lingchaomin.falcon.web.dao.HostGroupTplRefDao;
import com.lingchaomin.falcon.web.dto.OperErrorCode;
import com.lingchaomin.falcon.web.dto.OperateResultDto;
import com.lingchaomin.falcon.web.dto.ReqResultFormatter;
import com.lingchaomin.falcon.web.entity.HostGroup;
import com.lingchaomin.falcon.web.entity.HostGroupRef;
import com.lingchaomin.falcon.web.entity.HostGroupTplRef;
import com.lingchaomin.falcon.web.service.IHostGroupService;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 下午3:46
 * @description
 */
@Component
public class HostGroupService implements IHostGroupService {

    @Autowired
    private HostGroupDao hostGroupDao;

    @Autowired
    private HostGroupRefDao hostGroupRefDao;

    @Autowired
    private HostGroupTplRefDao hostGroupTplRefDao;



    @Autowired
    private HostDao hostDao;

    /**
     * 添加
     */
    public OperateResultDto add(String grpName, String note) {

        HostGroup hostGroup = HostGroup.builder()
                .gmtCreate(new Date())
                .gmtModified(new Date())
                .grpName(grpName)
                .note(note)
                .build();
        long ret = hostGroupDao.insert(hostGroup);


        if (ret > 0) {
            return ReqResultFormatter.formatOperSuccessDto(ret);
        }
        return ReqResultFormatter.formatFailDto(OperErrorCode.FAIL);
    }

    /**
     * 修改
     */
    public OperateResultDto modify(Long id, String grpName, String note) {
        HostGroup hostGroup = HostGroup.builder()
                .grpName(grpName)
                .note(note)
                .id(id)
                .build();

        long ret = hostGroupDao.updateById(hostGroup);


        if (ret > 0) {
            return ReqResultFormatter.formatOperSuccessDto(ret);
        }
        return ReqResultFormatter.formatFailDto(OperErrorCode.FAIL);
    }

    /**
     * 删除
     */
    public OperateResultDto delete(Long id) {
        long ret = hostGroupDao.deleteById(id);

        ret = hostGroupRefDao.deleteByGrpId(id);

        if (ret > 0) {

            return ReqResultFormatter.formatOperSuccessDto(ret);
        }
        return ReqResultFormatter.formatFailDto(OperErrorCode.FAIL);
    }

    /**
     * 列表
     */
    public OperateResultDto list() {
        List<HostGroup> hostGroups = hostGroupDao.selectAll();

        for(HostGroup hostGroup:hostGroups){
           List<String> hosts=hostDao.selectByGrpId(hostGroup.getId());

           if(CollectionUtils.isNotEmpty(hosts)){
               hostGroup.setHostNames(hosts);
           }

        }
        return ReqResultFormatter.formatOperSuccessDto(hostGroups);
    }

    /**
     * 绑定host
     */
    public OperateResultDto bindHost(String hostIds, Long hostGrpId) {

        String[] hostIdsArr = hostIds.split(",");

        List<HostGroupRef> hostGroupRefs = new ArrayList<HostGroupRef>();

        String oldHostGrpIds = StringUtils.join(hostGroupRefDao.selectByGrpId(hostGrpId), ",");

        //获取需要删除的hostId
        for (String oldId : oldHostGrpIds.split(",")) {
            if (!hostIds.contains(oldId)) {
                hostGroupRefDao.deleteById(Long.valueOf(oldId));
            }
        }

        for (String hostId : hostIdsArr) {
            HostGroupRef hostGroupRef = HostGroupRef.builder()
                    .gmtCreate(new Date())
                    .gmtModified(new Date())
                    .grpId(hostGrpId)
                    .hostId(Long.valueOf(hostId))
                    .build();
            hostGroupRefs.add(hostGroupRef);
        }

        long ret = hostGroupRefDao.insertBatch(hostGroupRefs);

        if (ret > 0) {
            return ReqResultFormatter.formatOperSuccessDto(ret);
        }
        return ReqResultFormatter.formatFailDto(OperErrorCode.FAIL);
    }

    /**
     * 绑定模版
     */
    public OperateResultDto bindTpl(Long tplId, Long hostGrpId) {
        HostGroupTplRef hostGroupTplRef = HostGroupTplRef.builder()
                .tplId(tplId)
                .gmtCreate(new Date())
                .gmtModified(new Date())
                .grpId(hostGrpId)
                .build();

        long ret = hostGroupTplRefDao.insert(hostGroupTplRef);

        if (ret > 0) {
            return ReqResultFormatter.formatOperSuccessDto(ret);
        }
        return ReqResultFormatter.formatFailDto(OperErrorCode.FAIL);
    }
}
