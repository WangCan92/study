package cn.blackbulb.service;

import cn.blackbulb.dao.OrderDao;
import cn.blackbulb.dao.UserDao;
import org.spring.annotations.MyAutowired;

/**
 * @author wangcan
 */
public class UserService {
    @MyAutowired
    private UserDao userDao;
    @MyAutowired
    private OrderDao orderDao;

    public void find() {
        System.out.println("service find ");
        userDao.query();
        orderDao.query();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserService() {
    }
}
