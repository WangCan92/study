package org.spring.exception;

/**
 * @author wangcan
 */
public class MySpringException extends RuntimeException {
    private String msg;

    public MySpringException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
