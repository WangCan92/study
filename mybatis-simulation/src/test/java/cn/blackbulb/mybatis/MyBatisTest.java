package cn.blackbulb.mybatis;

import cn.blackbulb.config.AppConfig;
import cn.blackbulb.service.UserService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wangcan
 */
public class MyBatisTest {

    @Test
    public void mybatisTest() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        UserService u = applicationContext.getBean(UserService.class);
        u.queryUserInfo("123123");
    }
}
