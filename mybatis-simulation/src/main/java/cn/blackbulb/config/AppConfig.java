package cn.blackbulb.config;

import org.mybatis.anno.MapperScan;
import org.mybatis.util.MyImportBeanDefinationRegistara;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * @author wangcan
 */
@ComponentScan({"cn.blackbulb","org.mybatis"})
@MapperScan("cn.blackbulb.dao")
@Import(MyImportBeanDefinationRegistara.class)
public class AppConfig {
}
