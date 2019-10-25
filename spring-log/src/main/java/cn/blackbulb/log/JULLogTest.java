package cn.blackbulb.log;

import java.util.logging.Logger;

/**
 * @author wangcan
 */
public class JULLogTest {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("julLogTest");
        logger.info("julLog test .....");
    }
}
