package org.xmcxh.vd.mall.sso.exception;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExceptionMarker {
    int value() default -1000;
    String group() default "";
    String description() default "";
}
