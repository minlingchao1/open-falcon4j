package com.lingchaomin.falcon.transfer.receiver;

import com.lingchaomin.falcon.transfer.dto.TransferResp;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/20 下午9:20
 * @description
 */
public interface IMetricInfoReceiver {

    /**
     * 接收监控信息
     */
    TransferResp receiverMetricValues(String msg);

}
