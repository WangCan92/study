package cn.blackbulb.log;

import org.apache.log4j.Logger;

/**
 * @author wangcan
 */
public class Log4jTest {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Log4jTest.class);
        logger.info("log4j........");
    }
}
