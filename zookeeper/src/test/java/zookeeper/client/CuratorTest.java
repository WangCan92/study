package zookeeper.client;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.Before;
import org.junit.Test;

public class CuratorTest {
    CuratorFramework client;

    @Before
    public void before() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.builder().connectString("192.168.0.114:2181").sessionTimeoutMs(30000).connectionTimeoutMs(30000).retryPolicy(retryPolicy).build();
        client.start();
    }

    @Test
    public void creat()throws Exception{
        String test = client.create().withMode(CreateMode.PERSISTENT).forPath("/test", "sdf".getBytes());
        System.out.println(test);
    }

    @Test
    public void pathWatch(){

    }

}
