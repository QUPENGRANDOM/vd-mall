package org.xmcxh.vd.mall.sso.aspect;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogIgnore {
    boolean input() default true;
    boolean output() default true;
}
