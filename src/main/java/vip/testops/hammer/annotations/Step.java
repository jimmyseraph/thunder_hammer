package vip.testops.hammer.annotations;

import java.lang.annotation.*;

/**
 * Step注解用于在PO中申明操作步骤，可用于方法被调用前后的日志注入<br />
 * 属性value不能为空，用于命名步骤名称
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Step {
    String value();
}
