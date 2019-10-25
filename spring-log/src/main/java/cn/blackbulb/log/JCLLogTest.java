package cn.blackbulb.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author wangcan
 */
public class JCLLogTest {
    public static void main(String[] args) {
        Log logger = LogFactory.getLog(JCLLogTest.class);
        logger.info("jcl test ....");
    }
}
