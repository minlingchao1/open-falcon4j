package com.lingchaomin.falcon.web.service;

import java.util.List;
import java.util.Map;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/10 下午11:33
 * @description
 */
public interface IGroupHostService {

    /**
     * 获取模版id和hostid关联
     * @return
     */
    Map<Long,List<Long>> getHostTplIds();
}
