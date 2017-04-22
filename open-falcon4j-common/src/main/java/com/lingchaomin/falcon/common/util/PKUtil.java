package com.lingchaomin.falcon.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/21 上午1:03
 * @description 生成key
 */
public class PKUtil {

    /**
     * 获取key
     * @param endPoint
     * @param metric
     * @param tags
     * @return
     */
    public static final String pk(String endPoint,String metric,String tags){

        if(StringUtils.isBlank(tags)){
            return String.format("%s/%s",endPoint,metric);
        }

        return String.format("%s/%s/%s",endPoint,metric,TagUtil.getTagString(tags));
    }

}
