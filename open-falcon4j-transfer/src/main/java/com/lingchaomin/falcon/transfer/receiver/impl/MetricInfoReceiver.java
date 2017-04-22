package com.lingchaomin.falcon.transfer.receiver.impl;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.lingchaomin.falcon.transfer.constant.TransferConfig;
import com.lingchaomin.falcon.transfer.constant.TransferRespError;
import com.lingchaomin.falcon.transfer.dto.TransferResp;
import com.lingchaomin.falcon.transfer.receiver.IMetricInfoReceiver;
import com.lingchaomin.falcon.common.entity.MetricValue;
import com.lingchaomin.falcon.common.util.JsonUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/20 下午9:17
 * @description 用于接收监控数据
 */
@Component
@Path("metric")
@Produces({ContentType.APPLICATION_JSON_UTF_8})
public class MetricInfoReceiver implements IMetricInfoReceiver{


    @Autowired
    private TransferConfig judgeConfig;

    /**
     * 接收监控信息
     */
    @GET
    @Path("receive")
    @Override
    public TransferResp receiverMetricValues(@QueryParam("msg")String msg) {

        MetricValue metricValue= JsonUtil.fromJson(msg,MetricValue.class);

        if (StringUtils.isBlank(metricValue.getMetric()) || StringUtils.isBlank(metricValue.getEndPoint())) {
             return TransferResp.fail(TransferRespError.INVALID_METRIC_OR_ENDPOINT.getValueEn());
        }

        if(StringUtils.isBlank(metricValue.getValue())){
             return TransferResp.fail(TransferRespError.VALUE_IS_NULL.getValueEn());
        }

        if(metricValue.getMetric().length()+metricValue.getTag().length()>500){
            return TransferResp.fail(TransferRespError.METRIC_TAG_LENGTH_OUT_OF_RANGE.getValueEn());
        }

        if(metricValue.getStep()<=0){
            return TransferResp.fail(TransferRespError.STEP_INVAILD.getValueEn());
        }

        //若开启则发送到告警判断队列
        if (judgeConfig.getEnable()){

        }


        return TransferResp.success();
    }



}
