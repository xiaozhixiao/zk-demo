package com.xyy;

import org.apache.zookeeper.*;

import java.io.IOException;

import static java.lang.Long.MAX_VALUE;

public class DistributeServer {


    private static  final String connectString = "hadoop102:2181,hadoop103:2181,hadoop104:2181";

    private int sessionTimeOut = 2000;
    private ZooKeeper zk;

    public static void main(String[] args) throws Exception {
        DistributeServer server = new DistributeServer();

        /**
         * 获取zk链接
         */

        server.getConnection();

        /**
         * 注册
         */
        server.registerServer(args[0]);

        /**
         * 业务操作
         */

        server.business(args[0]);
    }

    private void business(String hostName) throws InterruptedException {
        System.out.println(hostName + " is working");
        Thread.sleep(MAX_VALUE);
    }

    public  void registerServer(String  hostName) throws KeeperException, InterruptedException {
        String s = zk.create("/servers/server", hostName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(hostName + " 上线了 "+ s);
    }

    public void  getConnection() throws IOException {

        zk = new ZooKeeper(connectString, sessionTimeOut, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }
}
