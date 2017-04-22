package com.lingchaomin.falcon.judge.his;

import com.lingchaomin.falcon.common.entity.JudgeItem;
import com.lingchaomin.falcon.judge.constant.CheckIsJudgeProperty;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/18 下午9:45
 * @description 用来存储需要判断的数据
 */
public class JudgeItemStore {

    public static Map<String, JudgeItemMap> judgeItemMapMap = new ConcurrentHashMap<>();

    /**
     * list最大存储个数
     */
    public static final Integer MAX_SAVE_COUNT = 11;

    /**
     * 初始化map,取md5值的前两位组成map,便于分散数据
     */
    public static void initJudgeItemMap() {

        String[] arr = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                judgeItemMapMap.put(arr[i] + arr[j], new JudgeItemMap());
            }
        }
    }

    /**
     * 获取key值对应的值
     * @param key
     * @return
     */
    public static JudgeItemQueue get(String key) {
        String pk=key.substring(0,2);
        JudgeItemMap judgeItemMap = judgeItemMapMap.get(pk);
        JudgeItemQueue judgeItemList = judgeItemMap.getMap().get(key);

        return judgeItemList;
    }

    /**
     * 设置值
     * @param key
     * @param judgeItemQueue
     */
    public static void set(String key,JudgeItemQueue judgeItemQueue){
        JudgeItemMap judgeItemMap = judgeItemMapMap.get(key.substring(0, 2));
        Map<String,JudgeItemQueue> map=judgeItemMap.getMap();
        map.put(key,judgeItemQueue);
    }

    /*
     *清除过期数据
     */
    public static void cleanStale(Long timestamp) {

        Set<String> keys = new HashSet<String>();

        //只能通过迭代器删除，新的for循环无法修改map内容，因为不通过迭代器
        Iterator<Map.Entry<String, JudgeItemMap>> it = judgeItemMapMap.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, JudgeItemMap> entry = it.next();

            JudgeItemMap judgeItemMap = entry.getValue();

            Iterator<Map.Entry<String, JudgeItemQueue>> jit = judgeItemMap.getMap().entrySet().iterator();

            while (jit.hasNext()) {
                Map.Entry<String, JudgeItemQueue> jentry = jit.next();

                JudgeItem judgeItem = jentry.getValue().front();

                if (judgeItem.getTimestamp() < timestamp) {
                    jit.remove();
                }

            }
        }
    }

    /**
     * 判断是否进行告警判断并放入list
     */
    public static EnumMap<CheckIsJudgeProperty,Object> pushFrontAndMaintain(JudgeItem judgeItem) {

        EnumMap<CheckIsJudgeProperty,Object> retMap = new EnumMap<>(CheckIsJudgeProperty.class);

        String pk = judgeItem.getId();

        JudgeItemQueue judgeItemQueue=get(pk);

        if(judgeItemQueue!=null){

            boolean needJudge=judgeItemQueue.pushFrontAndMaintain(judgeItem,MAX_SAVE_COUNT);

            retMap.put(CheckIsJudgeProperty.IS_JUDGE,needJudge);
            retMap.put(CheckIsJudgeProperty.JUDGE_ITEM_QUEUE,judgeItemQueue);

            return retMap;

        }else{
            judgeItemQueue=new JudgeItemQueue();
            judgeItemQueue.pushFront(judgeItem);
            set(pk,judgeItemQueue);

            retMap.put(CheckIsJudgeProperty.IS_JUDGE,true);
            retMap.put(CheckIsJudgeProperty.JUDGE_ITEM_QUEUE,judgeItemQueue);
            return retMap;
        }
    }

    /**
     * 用于存储采集指标对应的list
     */
    public static class JudgeItemMap {

        //用于存储需要判断告警的数据
        private Map<String, JudgeItemQueue> map = new ConcurrentHashMap<>();

        public Map<String, JudgeItemQueue> getMap() {
            return map;
        }

        public void setMap(Map<String, JudgeItemQueue> map) {
            this.map = map;
        }
    }
}
