package cn.blackbulb.service;

import cn.blackbulb.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author wangcan
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void listAllUser(){
        List<Map<String,Object>> result = userMapper.list();
        System.out.println(result);
    }
}
