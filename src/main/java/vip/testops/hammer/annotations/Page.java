package vip.testops.hammer.annotations;

import java.lang.annotation.*;

/**
 * PageObject注解，用于在测试方法参数中，申明PageObject对象，该对象必须是{@link vip.testops.hammer.base.BasePage}的继承类，否则无法识别。<br />
 *
 * 属性url可为空，当非空时，将会注入到PO对象中
 *
 * @author Louis
 */
@Target({ ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Page {
    String url() default "";
}
