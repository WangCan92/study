package cn.blackbulb.pojo.bo;

import java.io.Serializable;

/**
 * @author wangcan
 */
public class BaseResult<T> implements Serializable {
    private String code;
    private String message;
    private T result;

    public static final String SUCCESS_CODE = "00000000";

    public boolean isSuccess() {
        return this.code != null && this.code.length() > 0 && this.code.equals(SUCCESS_CODE);
    }

    public static <T> BaseResult getScuccessResult(T t) {
        BaseResult result = new BaseResult<T>();
        result.setCode(SUCCESS_CODE);
        result.setMessage("成功");
        result.setResult(t);
        return result;
    }

    public static <T> BaseResult getFailedResult(T t) {
        BaseResult<T> result = new BaseResult<T>();
        result.setCode("11111111");
        result.setMessage("失败");
        result.setResult(t);
        return result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
