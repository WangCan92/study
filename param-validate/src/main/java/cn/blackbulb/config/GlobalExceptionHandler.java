package cn.blackbulb.config;

import cn.blackbulb.pojo.common.BaseResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private Map<String,Object> getValid(BindingResult bindingResult){
        Map<String,Object> data = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error->{
            data.put(error.getField(),error.getDefaultMessage());
        });
        return data;
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public BaseResult<Map<String,Object>> validateParamException(HttpServletResponse response, MethodArgumentNotValidException exception, HandlerMethod handlerMethod){
        BaseResult<Map<String,Object>> result = new BaseResult<>();
        result.setCode(11111);
        result.setResult(this.getValid(exception.getBindingResult()));
        return result;
    }
}
