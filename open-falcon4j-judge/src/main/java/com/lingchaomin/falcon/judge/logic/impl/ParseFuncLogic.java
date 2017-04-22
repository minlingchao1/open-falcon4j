package com.lingchaomin.falcon.judge.logic.impl;



import com.lingchaomin.falcon.common.entity.Expression;
import com.lingchaomin.falcon.common.entity.JudgeItem;
import com.lingchaomin.falcon.common.entity.Strategy;
import com.lingchaomin.falcon.judge.constant.GetHisDataProperty;
import com.lingchaomin.falcon.judge.his.JudgeItemQueue;
import com.lingchaomin.falcon.judge.logic.IParseFuncLogic;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/8 下午10:33
 * @description 策略解析
 */
@Component
public class ParseFuncLogic implements IParseFuncLogic {


    public static final String POUND = "#";

    public static final float TRIGGER_VALUE = 0.0001f;

    /**
     * 解析主机策略生成
     */
    public Map<String, Object> parseFuncFromString(JudgeItemQueue judgeItemQueue, Strategy strategy) {

        String func = strategy.getFunc();

        int idx = func.indexOf(POUND);

        //限制次数
        int limit = Integer.valueOf(func.substring(func.indexOf(POUND) + 1, func.indexOf(POUND) + 2));

        //函数名
        String funcName = func.substring(0, func.indexOf(POUND) - 1);

        if (funcName.equals("max")) {
            return maxFunction(judgeItemQueue, limit, strategy.getRightValue(), strategy.getOperator());
        } else if (funcName.equals("min")) {
            return minFunction(judgeItemQueue, limit, strategy.getRightValue(), strategy.getOperator());
        } else if (funcName.equals("all")) {
            return allFunction(judgeItemQueue, limit, strategy.getRightValue(), strategy.getOperator());
        } else if (funcName.equals("sum")) {
            return sumFunction(judgeItemQueue, limit, strategy.getRightValue(), strategy.getOperator());
        } else if (func.equals("avg")) {
            return avgFunction(judgeItemQueue, limit, strategy.getRightValue(), strategy.getOperator());
        } else if (funcName.equals("diff")) {
            return diffFunction(judgeItemQueue, limit, strategy.getRightValue(), strategy.getOperator());
        } else if (funcName.equals("pdiff")) {
            return pdiffFunction(judgeItemQueue, limit, strategy.getRightValue(), strategy.getOperator());
        }

        return null;
    }

    /**
     * 解析策略生成
     */
    @Override
    public Map<String, Object> parseFuncFromString(JudgeItemQueue judgeItemQueue, Expression expression) {
        String func = expression.getFunc();

        int idx = func.indexOf(POUND);

        //限制次数
        int limit = Integer.valueOf(func.substring(func.indexOf(POUND) + 1, func.indexOf(POUND) + 2));

        //函数名
        String funcName = func.substring(0, func.indexOf(POUND) - 1);

        if (funcName.equals("max")) {
            return maxFunction(judgeItemQueue, limit, expression.getRightValue(), expression.getOperator());
        } else if (funcName.equals("min")) {
            return minFunction(judgeItemQueue, limit, expression.getRightValue(), expression.getOperator());
        } else if (funcName.equals("all")) {
            return allFunction(judgeItemQueue, limit, expression.getRightValue(), expression.getOperator());
        } else if (funcName.equals("sum")) {
            return sumFunction(judgeItemQueue, limit, expression.getRightValue(), expression.getOperator());
        } else if (func.equals("avg")) {
            return avgFunction(judgeItemQueue, limit, expression.getRightValue(), expression.getOperator());
        } else if (funcName.equals("diff")) {
            return diffFunction(judgeItemQueue, limit, expression.getRightValue(), expression.getOperator());
        } else if (funcName.equals("pdiff")) {
            return pdiffFunction(judgeItemQueue, limit, expression.getRightValue(), expression.getOperator());
        }

        return null;
    }

    /**
     * 对于最新的limit个点，其最大值满足阈值条件则报警
     */
    private Map<String, Object> maxFunction(JudgeItemQueue judgeItemQueue, int limit, float rightValue, String operator) {

        List<JudgeItem> judgeItemList = getByLimit(judgeItemQueue, limit);

        Map<String, Object> map = new HashMap<String, Object>();

        boolean isTriggered = false;

        if (CollectionUtils.isEmpty(judgeItemList)) {
            map.put("isTriggered", isTriggered);
            return map;
        }

        float maxValue = judgeItemList.get(0).getValue();

        for (JudgeItem j : judgeItemList) {
            if (j.getValue() > maxValue) {
                maxValue = j.getValue();
            }
        }

        float leftValue = maxValue;

        isTriggered = checkIsTriggered(isTriggered, leftValue, operator, rightValue);


        map.put("leftValue", leftValue);
        map.put("isTriggered", isTriggered);
        map.put("latestJudgeItem", judgeItemList);

        return map;
    }

    /**
     * 对于最新的limit个点，其最小值满足阈值条件则报警
     */
    private Map<String, Object> minFunction(JudgeItemQueue judgeItemQueue, int limit, float rightValue, String operator) {
        List<JudgeItem> judgeItemList = getByLimit(judgeItemQueue, limit);

        Map<String, Object> map = new HashMap<String, Object>();

        boolean isTriggered = false;

        if (CollectionUtils.isEmpty(judgeItemList)) {
            map.put("isTriggered", isTriggered);
            return map;
        }

        float minValue = judgeItemList.get(0).getValue();

        for (JudgeItem j : judgeItemList) {
            if (j.getValue() < minValue) {
                minValue = j.getValue();
            }
        }

        float leftValue = minValue;

        isTriggered = checkIsTriggered(isTriggered, leftValue, operator, rightValue);


        map.put("leftValue", leftValue);
        map.put("isTriggered", isTriggered);
        map.put("latestJudgeItem", judgeItemList);
        return map;
    }


    /**
     * 对于最新的limit个点，全都满足则报警
     */
    private Map<String, Object> allFunction(JudgeItemQueue judgeItemQueue, int limit, float rightValue, String operator) {
        List<JudgeItem> judgeItemList = getByLimit(judgeItemQueue, limit);
        Map<String, Object> map = new HashMap<String, Object>();

        boolean isTriggered = false;

        if (CollectionUtils.isEmpty(judgeItemList)) {
            map.put("isTriggered", isTriggered);
            return map;
        }

        isTriggered = true;

        for (JudgeItem j : judgeItemList) {
            isTriggered = checkIsTriggered(isTriggered, j.getValue(), operator, rightValue);

            if (!isTriggered) {
                break;
            }
        }

        float leftValue = judgeItemList.get(0).getValue();


        map.put("leftValue", leftValue);
        map.put("isTriggered", isTriggered);
        map.put("latestJudgeItem", judgeItemList);
        return map;
    }

    /**
     * 对于最新的limit个点，总和满足则报警
     */
    private Map<String, Object> sumFunction(JudgeItemQueue judgeItemQueue, int limit, float rightValue, String operator) {

        List<JudgeItem> judgeItemList = getByLimit(judgeItemQueue, limit);

        Map<String, Object> map = new HashMap<String, Object>();

        boolean isTriggered = false;

        if (CollectionUtils.isEmpty(judgeItemList)) {
            map.put("isTriggered",isTriggered);
            return map;
        }

        float sum = 0.0f;

        for (JudgeItem j : judgeItemList) {
            sum += j.getValue();
        }

        float leftValue = sum;

        isTriggered = checkIsTriggered(isTriggered, leftValue, operator, rightValue);

        map.put("leftValue", leftValue);
        map.put("isTriggered", isTriggered);
        map.put("latestJudgeItem", judgeItemList);
        return map;
    }

    /**
     * 对于最新的limit个点，平均值满足则报警
     */
    private Map<String, Object> avgFunction(JudgeItemQueue judgeItemQueue, int limit, float rightValue, String operator) {

        List<JudgeItem> judgeItemList = getByLimit(judgeItemQueue, limit);

        Map<String, Object> map = new HashMap<String, Object>();


        boolean isTriggered = false;

        if (CollectionUtils.isEmpty(judgeItemList)) {
            map.put("isTriggered",isTriggered);
            return map;
        }

        float sum = 0.0f;

        for (JudgeItem j : judgeItemList) {
            sum += j.getValue();
        }

        float leftValue = sum / (float) judgeItemList.size();

        isTriggered = checkIsTriggered(isTriggered, leftValue, operator, rightValue);

        map.put("leftValue", leftValue);
        map.put("isTriggered", isTriggered);
        map.put("latestJudgeItem", judgeItemList);
        return map;
    }

    /**
     * 只要有一个点的diff触发阈值，就报警
     */
    private Map<String, Object> diffFunction(JudgeItemQueue judgeItemQueue,int limit, float rightValue, String operator) {
        List<JudgeItem> judgeItemList = getByLimit(judgeItemQueue, limit + 1);

        Map<String, Object> map = new HashMap<String, Object>();

        boolean isTriggered = false;

        if (CollectionUtils.isEmpty(judgeItemList)) {
            map.put("isTriggered",isTriggered);
            return map;
        }

        JudgeItem first = judgeItemList.get(0);

        float leftValue = 0.0f;
        for (int i = 1; i < judgeItemList.size(); i++) {
            leftValue = first.getValue() - judgeItemList.get(i).getValue();
            isTriggered = checkIsTriggered(isTriggered, leftValue, operator, rightValue);

            if (isTriggered) {
                break;
            }
        }

        map.put("leftValue", leftValue);
        map.put("isTriggered", isTriggered);
        map.put("latestJudgeItem", judgeItemList);
        return map;
    }

    /**
     * 拿最新push上来的点，与历史最新的3个点相减，得到3个差，再将3个差值分别除以减数，得到3个商值，只要有一个商值满足阈值则报警
     */
    private Map<String, Object> pdiffFunction(JudgeItemQueue judgeItemQueue, int limit, float rightValue, String operator) {
        List<JudgeItem> judgeItemList = getByLimit(judgeItemQueue, limit + 1);

        Map<String, Object> map = new HashMap<String, Object>();

        boolean isTriggered = false;

        if (CollectionUtils.isEmpty(judgeItemList)) {
            map.put("isTriggered",isTriggered);
            return map;
        }

        JudgeItem first = judgeItemList.get(0);

        float leftValue = 0.0f;
        for (int i = 1; i < judgeItemList.size(); i++) {

            if (judgeItemList.get(i).getValue() == 0) {
                continue;
            }

            leftValue = (first.getValue() - judgeItemList.get(i).getValue()) / judgeItemList.get(i).getValue() * 100.0f;

            isTriggered = checkIsTriggered(isTriggered, leftValue, operator, rightValue);
            if (isTriggered) {
                break;
            }
        }


        map.put("leftValue", leftValue);
        map.put("isTriggered", isTriggered);
        map.put("latestJudgeItem", judgeItemList);
        return map;
    }

    /**
     * 获取指定监控数据
     */
    private List<JudgeItem> getByLimit(JudgeItemQueue judgeItemQueue, int limit) {

        EnumMap<GetHisDataProperty,Object> retMap=judgeItemQueue.getHistoryData(limit);

        boolean hasException=(boolean)retMap.get(GetHisDataProperty.HAS_EXCEPTON);

        if(hasException){
            return new ArrayList<>();
        }

        List<JudgeItem> judgeItems=(List<JudgeItem>)retMap.get(GetHisDataProperty.JUDGE_ITEM_LIST);
        return judgeItems;
    }


    /**
     * 检测是否触发
     */
    private boolean checkIsTriggered(Boolean isTriggered, float leftValue, String operator, float rightValue) {

        if (operator.equals("=") || operator.equals("==")) {
            isTriggered = Math.abs(leftValue - rightValue) < TRIGGER_VALUE;
        } else if (operator.equals("!=")) {
            isTriggered = Math.abs(leftValue - rightValue) > TRIGGER_VALUE;
        } else if (operator.equals("<")) {
            isTriggered = leftValue < rightValue;
        } else if (operator.equals(">")) {
            isTriggered = leftValue > rightValue;
        } else if (operator.equals(">=")) {
            isTriggered = leftValue >= rightValue;
        }
        return isTriggered;
    }

    private String genJudgeItemKey(String endPoint, String metric) {
        return endPoint + ":" + metric;
    }

}
