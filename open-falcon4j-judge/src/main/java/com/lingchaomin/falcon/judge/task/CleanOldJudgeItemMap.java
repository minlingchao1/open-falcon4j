package com.lingchaomin.falcon.judge.task;



import com.lingchaomin.falcon.judge.his.JudgeItemStore;

import org.springframework.stereotype.Component;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/18 下午10:04
 * @description 清除旧的map
 */
@Component
public class CleanOldJudgeItemMap {

    public static final Long BEFORE=24*60*60l;

    public void clean(){
        JudgeItemStore.cleanStale(BEFORE);
    }

}
