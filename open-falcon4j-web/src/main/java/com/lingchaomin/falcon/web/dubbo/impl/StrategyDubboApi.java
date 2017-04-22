package com.lingchaomin.falcon.web.dubbo.impl;

import com.lingchaomin.falcon.common.api.IStrategyDubboApi;
import com.lingchaomin.falcon.common.entity.Strategy;
import com.lingchaomin.falcon.web.cache.CacheConfig;
import com.lingchaomin.falcon.web.dao.StrategyDao;
import com.lingchaomin.falcon.web.dto.TplDto;
import com.lingchaomin.falcon.web.entity.Host;
import com.lingchaomin.falcon.web.service.IGroupHostService;
import com.lingchaomin.falcon.web.service.IHostService;
import com.lingchaomin.falcon.web.service.ITemplateService;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/21 上午10:52
 * @description
 */
@Service
public class StrategyDubboApi implements IStrategyDubboApi {


    @Autowired
    private ITemplateService templateService;

    @Autowired
    private IHostService hostService;

    @Autowired
    private IGroupHostService groupHostService;

    @Autowired
    private StrategyDao strategyDao;


    /**
     * 获取所有策略
     */
    public Map<String, List<Strategy>> getAllStrategy() {

        Map<String,List<Strategy>> hostStras= CacheConfig.hostStrasCache.getIfPresent(CacheConfig.CACHE_HOST_STRAS);

        if(hostStras!=null){
            return hostStras;
        }

        //所有策略都基于模版
        Map<Long,TplDto> templates=templateService.getAllTemplate();

        if(templates==null){
            return hostStras;
        }

        Map<Long,Host> hosts=hostService.getAllHost();

        if(hosts==null){
            return hostStras;
        }


        Map<Long,Strategy> strategies=getStrategyFromCache();

        if(strategies==null){
            return hostStras;
        }

        //获取模版，策略关联
        Map<Long,List<Strategy>> tpl2Strategy=tpl2Strategy(templates,strategies);

        //获取host／tpl关联
        Map<Long,List<Long>> hostTplMap=groupHostService.getHostTplIds();

        if(hostTplMap.size()==0||hostTplMap==null){
            return hostStras;
        }

        hostStras=new HashMap<String, List<Strategy>>();

        for(Map.Entry<Long,List<Long>> entry:hostTplMap.entrySet()){
            if(!hosts.containsKey(entry.getKey())){
                continue;
            }

            // 计算当前host配置了哪些监控策略
            List<Strategy> list= calInheritStrategies(templates,entry.getValue(),tpl2Strategy);

            if (CollectionUtils.isEmpty(list)) {
                continue;
            }

            hostStras.put(hosts.get(entry.getKey()).getHostName(),list);

        }

        //获取所有模版
        return hostStras;
    }

    /**
     * 缓存获取
     * @return
     */
    private Map<Long,Strategy> getStrategyFromCache(){
        Map<Long,Strategy> strategiesMap=CacheConfig.strategyCache.getIfPresent(CacheConfig.CACHE_STRATEGY);

        if(strategiesMap==null){
            List<Strategy> strategies=strategyDao.selectAll();

            if(CollectionUtils.isEmpty(strategies)){
                return strategiesMap;
            }
            strategiesMap=new HashMap<Long, Strategy>();

            for(Strategy strategy:strategies){
                strategiesMap.put(strategy.getId(),strategy);
            }
        }

        return strategiesMap;
    }

    /**
     * 模版策略关联
     * @param templates
     * @param strategies
     * @return
     */
    private Map<Long,List<Strategy>> tpl2Strategy(Map<Long,TplDto> templates,Map<Long,Strategy> strategies){

        Map<Long,List<Strategy>> tpl2StrategyMap=new HashMap<Long, List<Strategy>>();
        Set<Long> templateIds=templates.keySet();

        for(Map.Entry<Long,Strategy> entry:strategies.entrySet()){

            Long tplId=entry.getValue().getTplId();
            if(tpl2StrategyMap.containsKey(tplId)){
                tpl2StrategyMap.get(tplId).add(entry.getValue());
            }else {
                List<Strategy> strategyList=new ArrayList<Strategy>();
                strategyList.add(entry.getValue());
                tpl2StrategyMap.put(tplId,strategyList);
            }

        }

        return tpl2StrategyMap;
    }

    /**
     * 计算host关联的策略
     * @param tplIds
     */
    private List<Strategy> calInheritStrategies(Map<Long,TplDto> templateMap,List<Long> tplIds,Map<Long,List<Strategy>> strategyMap){


        // 根据模板的继承关系，找到每个机器对应的模板全量
        /**
         * host_id =>
         * |a |d |a |a |a |
         * |  |  |b |b |f |
         * |  |  |  |c |  |
         * |  |  |  |  |  |
         */
        List<List<Long>> tplBuckets=new ArrayList<List<Long>>();
        for(Long tplId:tplIds){
            List<Long> parentIds=templateService.getParentIds(templateMap,new ArrayList<Long>(),tplId);

            if(CollectionUtils.isNotEmpty(parentIds)){
                tplBuckets.add(parentIds);
            }
        }

        // 每个host 关联的模板，有继承关系的放到同一个bucket中，其他的放在各自单独的bucket中
        /**
         * host_id =>
         * |a |d |a |
         * |b |  |f |
         * |c |  |  |
         * |  |  |  |
         */

        List<List<Long>> uniqueBuckets=new ArrayList<List<Long>>();

        for(int i=0;i<tplBuckets.size();i++){
            Boolean valid=true;

            for(int j=0;j<tplBuckets.size();j++){
                if(i==j){
                    continue;
                }

                //相同位置元素相等
                if(sliceIntEq(tplBuckets.get(i),tplBuckets.get(j))){
                    break;
                }


                //相同元素的索引是否相等
                if(sliceIntLt(tplBuckets.get(i),tplBuckets.get(j))){
                    valid=false;
                    break;
                }

            }

            if(valid){
                uniqueBuckets.add(tplBuckets.get(i));
            }
        }

        // 继承覆盖父模板策略，得到每个模板聚合后的策略列表
        List<Strategy> strategies=new ArrayList<Strategy>();


        for(List<Long> uniqueBucket:uniqueBuckets){

            // 开始计算一个桶，先计算老的tid，再计算新的，所以可以覆盖
            // 该桶最终结果

            Map<String,List<Strategy>> bucketStras=new HashMap<String, List<Strategy>>();

            for(int i=uniqueBucket.size()-1;i>=0;i--){

                if(!strategyMap.containsKey(uniqueBucket.get(i))){
                    continue;
                }

                Map<String,List<Strategy>> tidStras=new HashMap<String, List<Strategy>>();

                for(Strategy strategy:strategyMap.get(uniqueBucket.get(i))){
                    String uuid=String.format("metric:%s/tags:%s",strategy.getMetric(),strategy.getTag());

                    if(tidStras.containsKey(uuid)){
                        tidStras.get(uuid).add(strategy);
                    }else {
                        List<Strategy> s=new ArrayList<Strategy>();
                        s.add(strategy);
                        tidStras.put(uuid,s);
                    }
                }

                // 覆盖父模板
                for(Map.Entry<String,List<Strategy>> entry:tidStras.entrySet()){
                    bucketStras.put(entry.getKey(),entry.getValue());
                }

            }

            Long lastTid=uniqueBucket.get(0);

            // 替换所有策略的模板为最年轻的模板
            for(Map.Entry<String,List<Strategy>> entry:bucketStras.entrySet()){
                for(Strategy s:entry.getValue()){

                    Strategy strategy=s;
                    if(strategy.getTplId()!=lastTid){
                        strategy.setTplId(lastTid);
                    }

                    strategies.add(strategy);
                }
            }
        }

        return  strategies;
    }

    /**
     * 相同元素是否相等
     * @param a
     * @param b
     * @return
     */
    private boolean sliceIntEq(List<Long> a,List<Long> b){
        if(a.size()!=b.size()){
            return false;
        }

        for(int i=0;i<a.size();i++){
            if(a.get(i)!=b.get(i)){
                return false;
            }
        }

        return true;
    }

    private boolean sliceIntLt(List<Long>a,List<Long>b){

        for(int i=0;i<a.size();i++){
            if(!sliceIntContains(b,i)){
                return false;
            }
        }

        return true;
    }


    /**
     * 相同元素的索引是否相等
     * @param list
     * @param target
     * @return
     */
    private boolean sliceIntContains(List<Long> list,int target){

        for(int i=0;i<list.size();i++){
            if(i==target){
                return true;
            }
        }

        return false;
    }


}
