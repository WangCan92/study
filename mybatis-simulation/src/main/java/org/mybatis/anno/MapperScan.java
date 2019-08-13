package org.mybatis.anno;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author wangcan
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface MapperScan {
    String value();
}
