package vip.testops.hammer.processors;

import net.sf.cglib.proxy.Enhancer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import vip.testops.hammer.annotations.Page;
import vip.testops.hammer.base.BasePage;
import vip.testops.hammer.interceptors.StepInterceptor;

/**
 * Page注解处理器
 * @author Louis
 */
public class PageAnnotationProcessor {

    /**
     * 用于处理@Page注解的参数的静态方法
     * @param annotation    Page注解对象
     * @param type  被注解的参数的类型
     * @param driver    需要注入的WebDriver对象
     * @return  处理后的PageObject对象
     */
    public static Object processPage(Page annotation, Class<?> type, WebDriver driver) {
        StepInterceptor stepInterceptor = new StepInterceptor(); // 实例化拦截器
        Enhancer enhancer = new Enhancer(); // 实例化CGLib增强器
        enhancer.setSuperclass(type);   // 设置拦截的类，CGLib将拦截的类作为父类，动态创建一个子类进行实际操作，防止破坏原来的类
        enhancer.setCallback(stepInterceptor);  // 加载拦截器
        Object po = enhancer.create();  // 创建代理对象
        ((BasePage)po).setDriver(driver);   // 注入WebDriver对象
        if(!"".equals(annotation.url())) {  // 当Page注解中设置了url属性时，将URL的值注入
            ((BasePage)po).setUrl(annotation.url());
        }
        PageFactory.initElements(driver, po); // 调用Selenium的PageFactory初始化PageObject
        return po; // 将PO对象返回
    }
}
