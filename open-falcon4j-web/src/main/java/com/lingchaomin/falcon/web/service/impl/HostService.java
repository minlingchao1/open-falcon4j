package com.lingchaomin.falcon.web.service.impl;

import com.lingchaomin.falcon.web.cache.CacheConfig;
import com.lingchaomin.falcon.web.dao.HostDao;
import com.lingchaomin.falcon.web.dao.HostGroupRefDao;
import com.lingchaomin.falcon.web.dto.OperErrorCode;
import com.lingchaomin.falcon.web.dto.OperateResultDto;
import com.lingchaomin.falcon.web.dto.ReqResultFormatter;
import com.lingchaomin.falcon.web.entity.Host;
import com.lingchaomin.falcon.web.service.IHostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/10 下午10:29
 * @description
 */
@Component
public class HostService implements IHostService {

    @Autowired
    private HostDao hostDao;

    @Autowired
    private HostGroupRefDao hostGroupRefDao;

    /**
     * 获取所有host
     */
    public Map<Long, Host> getAllHost() {
        Map<Long, Host> hostsMap = CacheConfig.hostCache.getIfPresent(CacheConfig.CACHE_HOST);

        if (hostsMap == null) {
            List<Host> hosts = hostDao.selectAll();

            hostsMap = new HashMap<Long, Host>();

            for (Host host : hosts) {
                hostsMap.put(host.getId(), host);
            }

            CacheConfig.hostCache.put(CacheConfig.CACHE_HOST, hostsMap);

        }

        return hostsMap;
    }

    /**
     * 添加
     */
    public OperateResultDto add(String hostName, String note) {

        Host host = Host.builder()
                .hostName(hostName)
                .gmtCreate(new Date())
                .gmtModified(new Date())
                .build();

        long ret = hostDao.insert(host);

        if (ret > 0) {
            return ReqResultFormatter.formatOperSuccessDto(ret);
        }
        return ReqResultFormatter.formatFailDto(OperErrorCode.FAIL);
    }

    /**
     * 修改
     */
    public OperateResultDto modify(Long id, String hostName, String note) {

        Host host = Host.builder()
                .hostName(hostName)
                .gmtCreate(new Date())
                .gmtModified(new Date())
                .id(id)
                .build();

        long ret = hostDao.updateById(host);

        if (ret > 0) {
            return ReqResultFormatter.formatOperSuccessDto(ret);
        }
        return ReqResultFormatter.formatFailDto(OperErrorCode.FAIL);
    }

    /**
     * 删除
     */
    public OperateResultDto delete(Long id) {
        long ret = hostDao.deleteById(id);

        if (ret > 0) {
            hostGroupRefDao.deleteByHostId(id);
            return ReqResultFormatter.formatOperSuccessDto(ret);
        }
        return ReqResultFormatter.formatFailDto(OperErrorCode.FAIL);
    }

    /**
     * 获取列表
     */
    public OperateResultDto list() {
        List<Host> hosts = hostDao.selectAll();

        return ReqResultFormatter.formatOperSuccessDto(hosts);
    }


}
