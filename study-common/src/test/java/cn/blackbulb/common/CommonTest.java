package cn.blackbulb.common;

import org.junit.Test;

/**
 * @author wangcan
 */
public class CommonTest {
    @Test
    public void returnTest(){
        System.out.println(increase(1));
    }

    public static int increase(int i){
        System.out.println("increase base:"+i);
        return i++;
    }
}
