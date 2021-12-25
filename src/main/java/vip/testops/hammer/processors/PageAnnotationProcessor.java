package vip.testops.hammer.processors;

import net.sf.cglib.proxy.Enhancer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import vip.testops.hammer.annotations.Page;
import vip.testops.hammer.base.BasePage;
import vip.testops.hammer.interceptors.StepInterceptor;


public class PageAnnotationProcessor {

    public static Object processPage(Page annotation, Class<?> type, WebDriver driver) {
        StepInterceptor stepInterceptor = new StepInterceptor();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(type);
        enhancer.setCallback(stepInterceptor);
        Object po = enhancer.create();
        ((BasePage)po).setDriver(driver);
        if(!"".equals(annotation.url())) {
            ((BasePage)po).setUrl(annotation.url());
        }
        PageFactory.initElements(driver, po);
        return po;
    }
}
