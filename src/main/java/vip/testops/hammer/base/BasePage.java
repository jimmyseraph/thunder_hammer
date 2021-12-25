package vip.testops.hammer.base;

import org.openqa.selenium.WebDriver;

public class BasePage{
    private String url;
    private WebDriver driver;

    public <T extends BasePage> T open() {
        driver.get(url);
        return (T) this;
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
