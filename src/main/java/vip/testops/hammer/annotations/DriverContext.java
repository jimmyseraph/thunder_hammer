package vip.testops.hammer.annotations;

import java.lang.annotation.*;

/**
 * WebDriver注解，用于在BaseTest中申明WebDriver实例。
 * @author Louis
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DriverContext {
}
