package cn.blackbulb.service;

import org.junit.Test;
import org.spring.util.BeanFactory;

/**
 * @author wangcan
 */

public class ServiceTest {
    @Test
    public void beanFactoryTest(){
        BeanFactory beanFactory = new BeanFactory("spring.xml");
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.find();
    }
}
