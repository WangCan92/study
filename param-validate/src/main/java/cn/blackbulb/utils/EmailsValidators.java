package cn.blackbulb.utils;

import cn.blackbulb.annos.Emails;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author wangcan
 */
public class EmailsValidators implements ConstraintValidator<Emails,String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        for (String s1 : s.split(",")) {
            if(!RegexUtils.isEmail(s1)){
                return false;
            }
        }
        return true;
    }

    @Override
    public void initialize(Emails constraintAnnotation) {
        System.out.println("邮箱自定义验证啦");
    }
}
