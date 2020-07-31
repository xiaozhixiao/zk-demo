package com.xyy;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.IOException;
import java.util.List;

public class TestClient {

    private static  final String connectString = "hadoop102:2181,hadoop103:2181,hadoop104:2181";

    private int sessionTimeOut = 2000;
    private ZooKeeper zk;

    @Before
    public void init() throws IOException {

        zk = new ZooKeeper(connectString, sessionTimeOut, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println(watchedEvent.getType()+"==="+watchedEvent.getPath());
                List<String> nodes = null;
                try {
                    nodes = zk.getChildren("/", true);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (String child: nodes){
                    System.out.println(child);
                }
            }
        });
    }
    @Test
    public void create() throws Exception {

        // 参数1：要创建的节点的路径； 参数2：节点数据 ； 参数3：节点权限 ；参数4：节点的类型
        String nodeCreated = zk.create("/xyy", "liutong".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }



    @Test
    public void getChild() throws Exception {
        List<String> nodes = zk.getChildren("/", true);
        for (String child: nodes){
            System.out.println(child);
        }

        Thread.sleep(Long.MAX_VALUE);
    }

    @Test
    public  void testExists() throws KeeperException, InterruptedException {
        Stat stat = zk.exists("/sanguo", false);
        System.out.println(stat == null ?" no exists": "exists");
    }


}
