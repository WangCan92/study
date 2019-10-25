package cn.blackbulb.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author wangcan
 */
public interface UserMapper {
    @Select("select * from user")
    List<Map<String,Object>> list();
}
