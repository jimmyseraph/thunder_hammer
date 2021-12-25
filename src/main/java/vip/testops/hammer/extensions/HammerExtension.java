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

public class HammerExtension implements ParameterResolver, BeforeTestExecutionCallback {

    private final TestClassCache cache = new TestClassCache();

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.isAnnotated(Page.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        final Parameter parameter = parameterContext.getParameter();
        final WebDriver driver = (WebDriver) Objects.requireNonNull(cache.get("driver"));
        return PageAnnotationProcessor.processPage(parameterContext.findAnnotation(Page.class).get(), parameter.getType(), driver);
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        WebDriver driver = this.retrieveDriverFromTestClasses(context);
        if(driver != null) {
            cache.add("driver", driver);
        }
    }

    private WebDriver retrieveDriverFromTestClasses(final ExtensionContext context) throws IllegalAccessException {
        Class<?> testClass = context.getRequiredTestClass();
        Object instance = context.getRequiredTestInstance();
        Field field = null;
        do {
            Field[] fields = testClass.getDeclaredFields();
            for (Field item : fields) {
                DriverContext annotation = item.getAnnotation(DriverContext.class);
                if(annotation != null && item.getType().isAssignableFrom(WebDriver.class)){
                    field = item;
                    break;
                }
            }
            testClass = testClass.getSuperclass();
        } while (field == null && !testClass.getName().equals(Object.class.getName()));

        if(field != null) {
            field.setAccessible(true);
            return (WebDriver) field.get(instance);
        }

        return null;
    }

}
