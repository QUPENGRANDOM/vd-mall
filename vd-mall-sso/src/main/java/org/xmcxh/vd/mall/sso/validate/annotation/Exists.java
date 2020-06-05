package org.xmcxh.vd.mall.sso.validate.annotation;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.xmcxh.vd.mall.sso.validate.ExistsValidated;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = ExistsValidated.class)
public @interface Exists {
    String message() default "{javax.validation.constraints.Exists.message}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default {};
    String column() default "";
    Class<? extends BaseMapper> repository();
}
