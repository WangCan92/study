package cn.blackbulb.service;

import cn.blackbulb.config.AppConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wangcan
 */
public class UserServiceTest {
    private AnnotationConfigApplicationContext ac;
    @Before
    public void init(){
        ac = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @Test
    public void listAllUserTest(){
        ac.getBean(UserService.class).listAllUser();
    }
}
