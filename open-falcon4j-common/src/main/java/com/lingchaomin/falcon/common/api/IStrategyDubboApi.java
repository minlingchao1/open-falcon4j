package com.lingchaomin.falcon.common.api;

import com.lingchaomin.falcon.common.entity.Strategy;

import java.util.List;
import java.util.Map;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/21 上午10:52
 * @description 获取策略api
 */
public interface IStrategyDubboApi {
    /**
     * 获取所有策略
     * @return
     */
    Map<String,List<Strategy>> getAllStrategy();
}
