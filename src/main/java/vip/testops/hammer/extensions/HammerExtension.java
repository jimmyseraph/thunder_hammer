package vip.testops.hammer.extensions;

import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.WebDriver;
import vip.testops.hammer.annotations.DriverContext;
import vip.testops.hammer.annotations.Page;
import vip.testops.hammer.cache.TestClassCache;
import vip.testops.hammer.processors.PageAnnotationProcessor;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.Objects;

/**
 * 框架核心类，JUnit5扩展类，使用@ExtendWith注解加载到测试框架中
 * @author Louis
 */
public class HammerExtension implements ParameterResolver, BeforeTestExecutionCallback {

    /**
     * 引入测试专用线程独立缓存
     */
    private final TestClassCache cache = new TestClassCache();

    /**
     * 实现ParameterResolver接口的方法，用于判断哪些参数需要被处理
     * @param parameterContext 参数上下文
     * @param extensionContext 测试容器上下文
     * @return boolean型，true表示该参数需要处理，false表示不需要处理
     * @throws ParameterResolutionException 参数处理异常
     */
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.isAnnotated(Page.class);    // 当参数存在注解Page时，返回true
    }

    /**
     * 实现ParameterResolver接口的方法，用于实际处理参数
     * @param parameterContext  参数上下文
     * @param extensionContext 测试容器上下文
     * @return  参数被处理后所赋的值
     * @throws ParameterResolutionException 参数处理异常
     */
    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        final Parameter parameter = parameterContext.getParameter();
        final WebDriver driver = (WebDriver) Objects.requireNonNull(cache.get("driver"));
        return PageAnnotationProcessor.processPage(parameterContext.findAnnotation(Page.class).get(), parameter.getType(), driver);
    }

    /**
     * 实现BeforeTestExecutionCallback接口方法，将在测试方法被执行前调用，但是在@BeforAll和@BeforeEach方法之后
     * @param context   测试容器上下文
     * @throws Exception    执行异常
     */
    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        WebDriver driver = this.retrieveDriverFromTestClasses(context); // 从测试类中取得WebDriver实例对象
        if(driver != null) {
            cache.add("driver", driver);    // 将该对象存入缓存中
        }
    }

    /**
     * 从测试类中取得WebDriver实例对象，WebDriver对象需要有@DriverContext注解申明
     * @param context 测试容器上下文
     * @return WebDriver对象
     * @throws IllegalAccessException 非法访问异常
     */
    private WebDriver retrieveDriverFromTestClasses(final ExtensionContext context) throws IllegalAccessException {
        Class<?> testClass = context.getRequiredTestClass();    // 获取测试类
        Object instance = context.getRequiredTestInstance();    // 获取测试类的实例化对象
        Field field = null;
        do { // 从当前测试类开始，循环向上（往父类方向）查找
            Field[] fields = testClass.getDeclaredFields(); // 从当前类获取全部定义的字段
            for (Field item : fields) { // 每个字段循环判断
                DriverContext annotation = item.getAnnotation(DriverContext.class); // 查找当前字段是否存在DriverContext注解
                if(annotation != null && WebDriver.class.isAssignableFrom(item.getType())){ // 如果存在，且字段的类型是继承自WebDriver，则表示找到
                    field = item;
                    break;
                }
            }
            testClass = testClass.getSuperclass(); //进入父类
        } while (field == null && !testClass.getName().equals(Object.class.getName()));

        if(field != null) {
            field.setAccessible(true);
            return (WebDriver) field.get(instance);
        }

        return null;
    }

}
