package com.lingchaomin.falcon.common.api;

import com.lingchaomin.falcon.common.entity.Action;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/21 下午4:01
 * @description
 */
public interface IActionDubboApi {

    Action getActionById(Long id);
}
