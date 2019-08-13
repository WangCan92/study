package cn.blackbulb.dao;

import org.mybatis.anno.SelectSql;

/**
 * @author wangcan
 */
public interface OrderDao {

    @SelectSql("select * from order where  id = ")
    String queryUser(String id);
}
