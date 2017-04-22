package com.lingchaomin.falcon.common.api;

import com.lingchaomin.falcon.common.entity.Expression;

import java.util.List;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/21 上午10:56
 * @description 全局侧策略api
 */
public interface IExpressionDubboApi {

    /**
     *  获取全局策略
     * @return
     */
    List<Expression> getAllExpressions();
}
