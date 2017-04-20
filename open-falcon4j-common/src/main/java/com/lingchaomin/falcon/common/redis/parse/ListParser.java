package com.lingchaomin.falcon.common.redis.parse;




import com.lingchaomin.falcon.common.redis.exception.InvalidParameterException;
import com.lingchaomin.falcon.common.redis.exception.MissAnnotationException;
import com.lingchaomin.falcon.common.redis.model.KeyValuePair;
import com.lingchaomin.falcon.common.redis.util.ReflectUtil;
import com.lingchaomin.falcon.common.util.JsonUtil;
import com.lingchaomin.falcon.common.redis.annotation.List;
import com.lingchaomin.falcon.common.redis.annotation.ListColumn;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * @author : admin@chenlizhong.cn
 * @version : 1.0
 * @since : 2016-10-11 11:51.
 */
public class ListParser {

    public static final Logger LOG = LoggerFactory.getLogger("redis_list_parser");

    /**
     * 将value解析转换成对象
     *
     * @param t
     * @param value
     * @param <T>
     * @return
     */
    public static <T> T string2Obj(T t, String value) {

        Class<?> clazz = t.getClass();
        Field[] fields = clazz.getDeclaredFields();

        if (ArrayUtils.isEmpty(fields)) {
            return t;
        }

        Map<String, String> params = JsonUtil.fromJson(value, Map.class);

        for (Field field : fields) {
            ListColumn listColumn = field.getAnnotation(ListColumn.class);

            if (listColumn != null) {
                String columnName = getColumnName(field, listColumn);

                if (params.containsKey(columnName)) {
                    String val = String.valueOf(params.get(columnName));
                    ReflectUtil.setFieldValue4Str(t, field, val);
                }
            }
        }

        return t;
    }

    /**
     * 将对象转为key value pair
     *
     * @param obj
     * @param <T>
     * @return
     * @throws MissAnnotationException
     */
    public static <T> KeyValuePair obj2Set(T obj) throws MissAnnotationException {

        Class<?> clazz = obj.getClass();
        List listAnnotation = clazz.getAnnotation(List.class);

        if (null == listAnnotation) {
            throw new MissAnnotationException(clazz.getCanonicalName(), List.class.getCanonicalName());
        }

        Field[] fields = clazz.getDeclaredFields();
        Map<String, String> map = new HashMap<String, String>();

        if (ArrayUtils.isNotEmpty(fields)) {
            for (Field field : fields) {
                ListColumn listColumn = field.getAnnotation(ListColumn.class);

                if (listColumn != null) {
                    String val = ReflectUtil.getFieldValue2Str(obj, field);

                    if (StringUtils.isNotEmpty(val)) {
                        map.put(getColumnName(field, listColumn), val);
                    }

                }
            }
        }

        String key = parseKey(clazz, listAnnotation.key(), map);
        String value = JsonUtil.toJson(map);

        KeyValuePair keyValuePair = new KeyValuePair();
        keyValuePair.setKey(key);
        keyValuePair.setValue(value);

        return keyValuePair;
    }

    /**
     * 将对象转为key value pair
     *
     * @param obj
     * @param <T>
     * @return
     * @throws MissAnnotationException
     */
    public static <T> String obj2Str(T obj) {

        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Map<String, String> map = new HashMap<String, String>();

        if (ArrayUtils.isNotEmpty(fields)) {
            for (Field field : fields) {
                ListColumn listColumn = field.getAnnotation(ListColumn.class);

                if (listColumn != null) {
                    String val = ReflectUtil.getFieldValue2Str(obj, field);

                    if (StringUtils.isNotEmpty(val)) {
                        map.put(getColumnName(field, listColumn), val);
                    }

                }
            }
        }

        String value = JsonUtil.toJson(map);

        return value;
    }

    /**
     * 解析hash的key
     *
     * @param clazz
     * @param annoKey
     * @param hash
     * @return
     */
    protected static String parseKey(Class<?> clazz, String annoKey, Map<String, String> hash)
            throws MissAnnotationException {

        if ("".equals(annoKey.trim())) {
            throw new MissAnnotationException(
                    "class:[" + clazz.getCanonicalName() + "] hash annotation key can not be empty");
        }

        if (annoKey.contains(HashMapParser.KEY_PARAM_PREFIX) && MapUtils.isEmpty(hash)) {
            throw new MissAnnotationException("class:[" + clazz.getCanonicalName() + "] hash annotation key:[" + annoKey
                    + "]has param key,but column annos is null");
        }

        String key = annoKey;
        // key参数
        while (key.contains(HashMapParser.KEY_PARAM_PREFIX)) {
            // 开始
            int begin = key.indexOf(HashMapParser.KEY_PARAM_PREFIX);
            // 结束
            int end = key.indexOf(HashMapParser.KEY_PARAM_SUFFIX);
            // 检测符号对
            if (end < begin) {
                // key参数符号对异常
                throw new MissAnnotationException(
                        "class:[" + clazz.getCanonicalName() + "] hash annotation key param fix error");
            }

            String oneKey = key.substring(begin + 1, end);

            String value = hash.get(oneKey);
            if (StringUtils.isEmpty(value)) {
                // value不存在则设置为
                value = "";
            }

            // 替换key参数
            key = key.replaceAll(HashMapParser.KEY_PARAM_PREFIX_REPLACE + oneKey + HashMapParser.KEY_PARAM_SUFFIX_REPLACE, value);

        }

        // LOG.info(key);
        return key;
    }

    /**
     * 获取注解列名 1.ColumnName.name未设置则获取field的名称 2.ColumnName.name设置则获取该值
     *
     * @param field
     * @param listColumn
     * @return
     */
    protected static String getColumnName(Field field, ListColumn listColumn) {
        String annoListColumn = listColumn.name();

        return StringUtils.isNotEmpty(annoListColumn) ? annoListColumn : field.getName();
    }

    public static <T> java.util.List<T> string2Obj(T t, java.util.List<String> valueList) {

        // LOG.warn("valueList:{}", valueList);
        if (CollectionUtils.isEmpty(valueList)) {
            throw new InvalidParameterException("format obj can not null");
        }

        java.util.List<T> tList = new ArrayList<T>();
        Class<?> clazz = t.getClass();

        for (String value : valueList) {
            try {
                tList.add(string2Obj((T) clazz.newInstance(), value));
            } catch (InstantiationException e) {
                LOG.error(e.getMessage(), e);
            } catch (IllegalAccessException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        // LOG.warn("tList:{}", tList);
        return tList;
    }
}
