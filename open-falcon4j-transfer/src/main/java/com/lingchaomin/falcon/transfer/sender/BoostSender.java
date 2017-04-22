package com.lingchaomin.falcon.transfer.sender;

import com.lingchaomin.falcon.common.util.hash.ConsistantHash;
import com.lingchaomin.falcon.transfer.constant.TransferConfig;
import com.lingchaomin.falcon.transfer.constant.NodeRings;
import com.lingchaomin.falcon.transfer.constant.TransferQueueConfig;
import com.lingchaomin.falcon.common.entity.JudgeItem;
import com.lingchaomin.falcon.common.util.hash.Node;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/21 上午12:00
 * @description 初始化数据
 */
@Component
public class BoostSender implements ApplicationListener {

    private Logger LOG= LoggerFactory.getLogger(BoostSender.class);

    @Autowired
    private TransferConfig judgeConfig;

    private boolean isStart;


    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {

        if(!isStart){
            initJudgeItemQueue();

            initJudgeNodeRings();

            isStart=true;
        }
    }

    /**
     * 初始化告警数据队列
     */
    private void initJudgeItemQueue(){
        String clusters=judgeConfig.getCluster();

        if(StringUtils.isBlank(clusters)){
            LOG.warn("judge cluster is null~~~~");
            return;
        }

        String[] clusterArr=clusters.split(",");

        for(String cluster:clusterArr){

            if(cluster.split(":").length!=2){
                continue;
            }
            TransferQueueConfig.judgeQueues.put(cluster,new ConcurrentLinkedQueue<JudgeItem>());
        }
    }

    /**
     * 初始化一致性hash表
     */
    private void initJudgeNodeRings(){

        String clusters=judgeConfig.getCluster();

        Set<Node> nodes=keysOfNode(clusters);

        NodeRings.judgeNoedeRing.setNodeList(nodes);

        NodeRings.judgeNoedeRing.buildHashCycle();
    }

    /**
     * 获取服务器节点
     * @param clusters
     * @return
     */
    private Set<Node> keysOfNode(String clusters){
        String[] clusterArr=clusters.split(",");

        Set<Node> nodes=new HashSet<>();

        for(String cluster:clusterArr){

            String[] kv=cluster.split(":");
            if(kv.length!=2){
                continue;
            }

            Node node=new Node(kv[0],kv[1]);
            nodes.add(node);
        }

        return nodes;
    }


    public static void main(String[] args) {
        ConsistantHash judgeNoedeRing=new ConsistantHash();
        Set<Node> nodes=new HashSet<Node>();

        Node node1=new Node("dingdong07","10.12.13.14");
        //Node node2=new Node("dingdong08","10.12.13.15");
        Node node3=new Node("dingdong09","10.12.13.16");

        nodes.add(node1);
       // nodes.add(node2);
        nodes.add(node3);

        judgeNoedeRing.setNodeList(nodes);

        judgeNoedeRing.buildHashCycle();;

        Node node=judgeNoedeRing.findNodeByKey("dingdong07/cpu/project=falcon,module=judge");

        System.out.println(node.toString());
    }
}
