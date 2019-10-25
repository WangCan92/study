package cn.blackbulb.annos;

import cn.blackbulb.utils.EmailsValidators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wangcan
 */
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailsValidators.class)
@Target(ElementType.FIELD)
public @interface Emails {
    String message() default "邮件格式不正确";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
