package vip.testops.hammer.cases;

import org.junit.jupiter.api.Test;
import vip.testops.hammer.annotations.Page;
import vip.testops.hammer.pages.IndexPage;


public class DemoTest extends BaseTest{

    @Test
    public void test(@Page(url = "http://www.baidu.com") IndexPage indexPage){
        indexPage.open();
        indexPage.doSearch("test");
    }
}
