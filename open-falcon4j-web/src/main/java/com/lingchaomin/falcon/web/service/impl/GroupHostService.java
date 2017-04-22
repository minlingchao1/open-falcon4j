package com.lingchaomin.falcon.web.service.impl;


import com.lingchaomin.falcon.web.dao.HostGroupRefDao;
import com.lingchaomin.falcon.web.dto.HostTplDto;
import com.lingchaomin.falcon.web.service.IGroupHostService;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/10 下午11:33
 * @description
 */
@Component
public class GroupHostService implements IGroupHostService {

    @Autowired
    private HostGroupRefDao hostGroupRefDao;

    /**
     * 获取模版id和hostid关联
     */
    public Map<Long, List<Long>> getHostTplIds() {

        //获取直接关联模版
        List<HostTplDto> hostTplDtos=hostGroupRefDao.selectHostTplDto();


        Map<Long,List<Long>> map=new HashMap<Long, List<Long>>();

        if(CollectionUtils.isEmpty(hostTplDtos)){
            return map;
        }

        for(HostTplDto hostTplDto:hostTplDtos){
            if(map.containsKey(hostTplDto.getHostId())){
                map.get(hostTplDto.getHostId()).add(hostTplDto.getTplId());
            }else {
                List<Long> list=new ArrayList<Long>();
                list.add(hostTplDto.getTplId());
                map.put(hostTplDto.getHostId(),list);
            }
        }
        return map;
    }
}
