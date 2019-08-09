package cn.blackbulb.pojo.common;

public class BaseResult<T> {
    private Integer code;
    private String message;
    private T result;

    public static BaseResult success(){
        BaseResult result = new BaseResult();
        result.setCode(Constant.SUCCESS);
        return result;
    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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
