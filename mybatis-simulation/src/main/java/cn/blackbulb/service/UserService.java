package cn.blackbulb.service;

import cn.blackbulb.dao.OrderDao;
import cn.blackbulb.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangcan
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private OrderDao orderDao;

    public void queryUserInfo(String name){
        userDao.queryUser(name);
        System.out.println("orderDaoSql:"+orderDao.queryUser(name));
    }
}
