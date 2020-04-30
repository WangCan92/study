//package cn.blackbulb;
//
//import java.util.Date;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author wangcan
// */
//public class ScheduleTest {
//    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
//    private ConcurrentHashMap<Long,Integer> map = new ConcurrentHashMap<>();
//    public static void main(String[] args) {
//
//        scheduledExecutorService.scheduleAtFixedRate(()->{
//            System.out.println(new Date());
//        },0,5,TimeUnit.SECONDS);
//    }
//
//    public
//}
