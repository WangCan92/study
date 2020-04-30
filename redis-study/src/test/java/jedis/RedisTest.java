package jedis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.*;
import redis.clients.jedis.params.SetParams;

/**
 * @author wangcan
 */
public class RedisTest {
    private Jedis jedis;
    private JedisPool jp;

    @Before
    public void before() {
        jedis = new Jedis("192.168.0.109", 8000);
        jedis.auth("123456");


    }

    @Test
    public void testKey() {
        //        System.out.println(jedis.keys("*"));
        //        System.out.println(jedis.exists("k1"));
        //
        //        jedis.expire("k1",60);//设置逾期时间
        //        jedis.ttl("k1");//还剩多久过期

        /**
         * 分布式锁
         */
        System.out.println(jedis.incr("count"));
        SetParams setParams = new SetParams();
        setParams.nx();
        setParams.ex(100);
        jedis.set("kkk", "value", setParams);



    }

    @Test
    public void keyScan(){
        this.methodScan("0");
    }

    public void methodScan(String cursor){
        ScanParams scanParams = new ScanParams();
        scanParams.count(3);
        scanParams.match("k*");
        ScanResult<String> scan = jedis.scan(cursor, scanParams);
        System.out.println(scan.getCursor());
        scan.getResult().forEach(System.out::println);

    }
}
