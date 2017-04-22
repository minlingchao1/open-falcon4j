package com.lingchaomin.falcon.judge.his;

import com.lingchaomin.falcon.common.entity.JudgeItem;
import com.lingchaomin.falcon.judge.constant.GetHisDataProperty;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/18 下午10:50
 * @description 告警判断数据队列
 */
public class JudgeItemQueue {

    private ConcurrentLinkedDeque<JudgeItem> judgeItems = new ConcurrentLinkedDeque<>();

    public JudgeItem front() {
        return judgeItems.getFirst();
    }

    public void pushFront(JudgeItem judgeItem) {
        judgeItems.addFirst(judgeItem);
    }

    public void removeLast(){
        judgeItems.removeLast();
    }

    public int len(){
       return judgeItems.size();
    }

    /**
     * 获取历史数据
     * @param limit
     * @return
     */
    public EnumMap<GetHisDataProperty,Object> getHistoryData(Integer limit){
        EnumMap<GetHisDataProperty,Object> retMap = new EnumMap<>(GetHisDataProperty.class);

        if(limit<1){
            retMap.put(GetHisDataProperty.JUDGE_ITEM_LIST,new ArrayList<>());
            retMap.put(GetHisDataProperty.HAS_EXCEPTON,true);
        }

        int size=len();

        if(size==0){
            retMap.put(GetHisDataProperty.JUDGE_ITEM_LIST,new ArrayList<>());
            retMap.put(GetHisDataProperty.HAS_EXCEPTON,true);
            return retMap;
        }

        //不够返回
        if(size<limit){
            retMap.put(GetHisDataProperty.JUDGE_ITEM_LIST,new ArrayList<>());
            retMap.put(GetHisDataProperty.HAS_EXCEPTON,true);
            return retMap;
        }

        List<JudgeItem> his=new ArrayList<>();

        Iterator<JudgeItem> it=judgeItems.iterator();

        int i=0;
        while (it.hasNext()&&i<limit){
            his.add(it.next());
            i++;
        }

        retMap.put(GetHisDataProperty.JUDGE_ITEM_LIST,his);
        retMap.put(GetHisDataProperty.HAS_EXCEPTON,false);

        return retMap;
    }

    /**
     * 判断是否告警
     * @param judgeItem
     * @param maxCount
     * @return
     */
    public boolean pushFrontAndMaintain(JudgeItem judgeItem, Integer maxCount) {
        int size = len();

        if (size > 0) {
            // 新push上来的数据有可能重复了，或者timestamp不对，这种数据要丢掉
            if (judgeItem.getTimestamp() < front().getTimestamp() || judgeItem.getTimestamp() <= 0) {
                return false;
            }
        }

        pushFront(judgeItem);

        size++;

        if (size <= maxCount) {
            return true;
        }

        int delCount=size-maxCount;

        //后面删除
        for(int i=0;i<delCount;i++){
            removeLast();
        }

        return true;
    }
}
