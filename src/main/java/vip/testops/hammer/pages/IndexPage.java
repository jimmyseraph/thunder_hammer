package vip.testops.hammer.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import vip.testops.hammer.annotations.Step;
import vip.testops.hammer.base.BasePage;

import static org.junit.jupiter.api.Assertions.*;

public class IndexPage extends BasePage {
    @FindBy(id = "kw")
    private WebElement search_input;

    @FindBy(id = "su")
    private WebElement search_button;

    @Step("查询")
    public void doSearch(String keyword){
        search_input.sendKeys(keyword);
        search_button.click();
    }

    @Step("校验URL")
    public void assertUrl(String url){
        assertEquals(url, getDriver().getCurrentUrl(), "url校验");
    }
}
