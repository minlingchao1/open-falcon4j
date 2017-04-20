package com.lingchaomin.falcon.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/8 下午10:16
 * @description map操作
 */
public class MapUtil {


    public static final String COMMA_SPLIT = ",";

    public static final String EQUAL_SPLIT = "=";

    /**
     * string 转 map
     */
    public static Map<String, String> convertStr2Map(String str, String arrSplit, String mapSplit) {
        String[] strArr = str.split(arrSplit);

        Map<String, String> map = new HashMap<String, String>();

        for (String s : strArr) {
            String[] ms = s.split(mapSplit);
            map.put(ms[0], ms[1]);
        }

        return map;
    }
}
