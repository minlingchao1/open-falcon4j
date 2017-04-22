package com.lingchaomin.falcon.transfer.receiver.impl;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.lingchaomin.falcon.common.util.WebUtil;
import com.lingchaomin.falcon.transfer.constant.TransferConfig;
import com.lingchaomin.falcon.transfer.constant.TransferRespError;
import com.lingchaomin.falcon.transfer.dto.TransferResp;
import com.lingchaomin.falcon.transfer.receiver.IMetricInfoReceiver;
import com.lingchaomin.falcon.common.entity.MetricValue;
import com.lingchaomin.falcon.common.util.JsonUtil;
import com.lingchaomin.falcon.transfer.sender.IJudgeItemSender;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpException;
import org.apache.http.NameValuePair;
import org.jboss.resteasy.annotations.interception.Precedence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/20 下午9:17
 * @description 用于接收监控数据
 */
@Component
@Path("metric")
@Produces("application/json; charset=utf-8")
public class MetricInfoReceiver implements IMetricInfoReceiver {


    @Autowired
    private TransferConfig transferConfig;

    @Autowired
    private IJudgeItemSender judgeItemSender;

    /**
     * 接收监控信息
     */
    @POST
    @Path("receive")
    @Consumes({MediaType.APPLICATION_JSON})
    @Override
    public TransferResp receiverMetricValues(MetricValue metricValue) {

      //  MetricValue metricValue = JsonUtil.fromJson(msg, MetricValue.class);

        if (StringUtils.isBlank(metricValue.getMetric()) || StringUtils.isBlank(metricValue.getEndPoint())) {
            return TransferResp.fail(TransferRespError.INVALID_METRIC_OR_ENDPOINT.getValueEn());
        }

        if (StringUtils.isBlank(metricValue.getValue())) {
            return TransferResp.fail(TransferRespError.VALUE_IS_NULL.getValueEn());
        }

        if (metricValue.getMetric().length() + metricValue.getTag().length() > 500) {
            return TransferResp.fail(TransferRespError.METRIC_TAG_LENGTH_OUT_OF_RANGE.getValueEn());
        }

        if (metricValue.getStep() <= 0) {
            return TransferResp.fail(TransferRespError.STEP_INVAILD.getValueEn());
        }

        //若开启则发送到告警判断队列
        if (transferConfig.getEnable()) {
            judgeItemSender.push2JudgeSendQueue(metricValue);
        }


        return TransferResp.success();
    }


    public static void main(String[] args) throws IOException, HttpException {
        String msg = "{\"timestamp\":\"1492393517\",\"metric\":\"LOAD_AVG\",\"endPoint\":\"dingdong07\",\"tag\":\"project=dingdong07\",\"value\":\"0.06\"}";

        org.apache.commons.httpclient.NameValuePair[] data=new org.apache.commons.httpclient.NameValuePair[]{
                new org.apache.commons.httpclient.NameValuePair("msg",msg)
        };
        WebUtil.doPost("http://localhost:8081/metric/receive",data,"utf-8" );
    }

}
