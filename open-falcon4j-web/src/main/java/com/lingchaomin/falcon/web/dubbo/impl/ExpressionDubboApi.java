package com.lingchaomin.falcon.web.dubbo.impl;

import com.lingchaomin.falcon.common.api.IExpressionDubboApi;
import com.lingchaomin.falcon.common.entity.Expression;
import com.lingchaomin.falcon.web.dao.ExpressionDao;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/21 上午10:56
 * @description
 */
@Service
public class ExpressionDubboApi implements IExpressionDubboApi {

    private static Logger LOG= LoggerFactory.getLogger(ExpressionDubboApi.class);


    @Autowired
    private ExpressionDao expressionDao;

    /**
     * 获取全局策略
     */
    @Override
    public List<Expression> getAllExpressions() {
        List<Expression> expressions=expressionDao.selectAll();

        if(CollectionUtils.isEmpty(expressions)){
            return expressions;
        }

        for(Expression expression:expressions){
            try {
                parseExpression(expression);
            }catch (Exception e){
                LOG.error(e.getMessage(),e);
            }
        }

        return expressions;
    }

    private void parseExpression(Expression expression) throws Exception{
        String exp=expression.getExpression();

        int left=exp.indexOf("(");
        int right=exp.indexOf(")");

        String tagsStr=exp.substring(left+1,right);

        String[] tagsArr=tagsStr.split(",");

        Map<String,String> tagsMap=new HashMap<>();

        if(tagsArr.length<2){
            throw new Exception("tags len not enough,expression:"+exp);
        }

        for(String tag:tagsArr){
            String[] item=tag.split("=");

            if(item.length!=2){
                continue;
            }
            String key=item[0].trim();
            String value=item[1].trim();

            tagsMap.put(key,value);
        }

        if(!tagsMap.containsKey("metric")){
            throw new Exception("no metric give of "+exp);
        }

        String metric=tagsMap.get("metric");
        tagsMap.remove("metric");

        expression.setMetric(metric);
        expression.setTags(tagsMap);
    }
}
