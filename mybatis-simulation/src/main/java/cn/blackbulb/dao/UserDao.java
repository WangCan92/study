package cn.blackbulb.dao;

import org.mybatis.anno.SelectSql;

/**
 * @author wangcan
 */
public interface UserDao {
    @SelectSql("select * from user where user name = ")
    void queryUser(String name);
}
