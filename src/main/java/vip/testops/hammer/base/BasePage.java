package vip.testops.hammer.base;

import org.openqa.selenium.WebDriver;

/**
 * PageObject基类，所有PageObject必须继承该类才能正常被注入。
 */
public class BasePage{
    /**
     * Page对应的url地址
     */
    private String url;
    /**
     * WebDriver对象
     */
    private WebDriver driver;

    /**
     * PageObject基本方法，当url属性不为空时才有效
     */
    public void open() {
        driver.get(url);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }
}
