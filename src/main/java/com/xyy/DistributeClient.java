package com.xyy;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.MAX_VALUE;

public class DistributeClient {
    private static  final String connectString = "hadoop102:2181,hadoop103:2181,hadoop104:2181";

    private int sessionTimeOut = 2000;
    private ZooKeeper zk;
    public static void main(String[] args) throws Exception {


        DistributeClient client = new DistributeClient();
        client.getConnection();

        client.getServerList();

        client.business();
    }

    private void getServerList() throws KeeperException, InterruptedException {
        List<String> children = zk.getChildren("/servers", true);
        List<String> list = new ArrayList<>();

        for(String child : children){
            byte[] data = zk.getData("/servers/" + child, false, null);
            list.add(new String(data));
        }

        System.out.println(list);
    }

    private void getConnection() throws Exception{
        zk = new ZooKeeper(connectString, sessionTimeOut, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                try {
                    getServerList();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void business() throws InterruptedException {
        System.out.println("client is working");
        Thread.sleep(MAX_VALUE);
    }
}
