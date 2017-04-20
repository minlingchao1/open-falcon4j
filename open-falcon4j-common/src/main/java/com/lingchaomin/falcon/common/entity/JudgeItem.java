package com.lingchaomin.falcon.common.entity;


import com.lingchaomin.falcon.common.util.Md5Util;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/8 下午11:36
 * @description 用于判断的数据
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JudgeItem  implements Serializable{

    private String id;

    /**
     * host or project
     */
    private String endPoint;

    /**
     * 采集指标
     */
    private String metric;

    /**
     * 采集值
     */
    private Float value;

    /**
     * 时间戳
     */
    private Long timestamp;

    /**
     * 标签
     */
    private String tag;

    private Map<String,String> tags;

    /**
     * 生产eventId
     * @return
     */
    public static String genId(String endPoint,String metric,String tag){

        String pk= StringUtils.EMPTY;
        if(StringUtils.isEmpty(tag)){
            pk=endPoint+"/"+metric+"/"+tag;
        }else {
            pk=endPoint+"/"+metric;
        }

        return Md5Util.MD5(pk);
    }



}
