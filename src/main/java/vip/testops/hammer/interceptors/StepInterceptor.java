package vip.testops.hammer.interceptors;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.testops.hammer.annotations.Step;
import vip.testops.hammer.base.BasePage;

import java.lang.reflect.Method;

/**
 * 方法拦截器，用于拦截被@Step注解申明的方法
 * @author Louis
 */
public class StepInterceptor implements MethodInterceptor {
    /**
     * 实现MethodInterceptor接口的拦截方法
     * @param o 被拦截的方法所在的对象
     * @param method 被拦截的方法
     * @param args  传入该方法的参数
     * @param methodProxy   CGLib的方法代理器
     * @return  执行方法后的返回值
     * @throws Throwable    任何错误
     */
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        if (method.getDeclaringClass() != Object.class && method.getDeclaringClass() != BasePage.class) { // 首先过滤掉Object类和BasePage类中的方法
            Class<?> clazz = method.getDeclaringClass();
            Step annotation = method.getAnnotation(Step.class);
            if (annotation != null) { // 判断该方法是否存在@Step注解
                Logger logger = LoggerFactory.getLogger(clazz);
                String stepName = annotation.value(); // 获取step的名称
                logger.info("--> 开始执行Step: {}", stepName);  // 方法执行前的日志
                Object result = methodProxy.invokeSuper(o, args);   // 执行方法
                logger.info("--> Step:{} 执行完成", stepName); // 方法执行后的日志
                return result;
            } else {
                return methodProxy.invokeSuper(o, args); // 非@Step注解的方法直接放行
            }
        }
        return methodProxy.invokeSuper(o, args);
    }

}
