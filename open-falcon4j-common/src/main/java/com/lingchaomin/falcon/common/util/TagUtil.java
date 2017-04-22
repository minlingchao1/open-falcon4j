package com.lingchaomin.falcon.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/21 上午1:08
 * @description 获取标签的map
 */
public class TagUtil {

    /**
     * 获取标签map
     * @param tags
     * @return
     */
    public static  String getTagString(String tags){
        if(StringUtils.isBlank(tags)){
            return null;
        }
        Map<String,String> map=new HashMap<>();
        String[] tagsArr=tags.split(",");

        List<String> ret=new ArrayList<>();
        for(String tag:tagsArr){
            String[] tkv=tag.split("=");

            if(tkv.length!=2){
                continue;
            }
            String str=String.format("%s=%s",tkv[0],tkv[1]);
            ret.add(str);
        }

        return StringUtils.join(ret,",");
    }

    /**
     * 获取标签map
     * @param tags
     * @return
     */
    public static  Map<String,String> getTagMap(String tags){
        if(StringUtils.isBlank(tags)){
            return null;
        }
        Map<String,String> map=new HashMap<>();
        String[] tagsArr=tags.split(",");

        for(String tag:tagsArr){
            String[] tkv=tag.split("=");
            if(tkv.length!=2){
                continue;
            }
            map.put(tkv[0].trim(),tkv[1].trim());
        }

        return map;
    }
}
