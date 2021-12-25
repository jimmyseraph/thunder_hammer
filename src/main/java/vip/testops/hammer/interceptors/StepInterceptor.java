package vip.testops.hammer.interceptors;

import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.testops.hammer.annotations.Step;
import vip.testops.hammer.base.BasePage;

import java.lang.reflect.Method;

public class StepInterceptor implements MethodInterceptor {
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        if (method.getDeclaringClass() != Object.class && method.getDeclaringClass() != BasePage.class) {
            Class<?> clazz = method.getDeclaringClass();
            Step annotation = method.getAnnotation(Step.class);
            if (annotation != null) {
                Logger logger = LoggerFactory.getLogger(clazz);
                String stepName = annotation.value();
                logger.info("--> 开始执行Step: {}", stepName);
                Object result = methodProxy.invokeSuper(o, args);
                logger.info("--> Step:{} 执行完成", stepName);
                return result;
            } else {
                return methodProxy.invokeSuper(o, args);
            }
        }
        return methodProxy.invokeSuper(o, args);
    }

}
