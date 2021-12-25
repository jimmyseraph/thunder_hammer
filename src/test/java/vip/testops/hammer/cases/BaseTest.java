package vip.testops.hammer.cases;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import vip.testops.hammer.annotations.DriverContext;
import vip.testops.hammer.extensions.HammerExtension;

import java.io.File;

@ExtendWith(HammerExtension.class)
public class BaseTest {

    private static ChromeDriverService service;

    @DriverContext
    private static WebDriver driver;

    @BeforeAll
    public static void initDriver(){
        service = new ChromeDriverService.Builder()
                .usingPort(2222)
                .usingDriverExecutable(new File("./driver/chromedriver"))
                .build();
        driver = new ChromeDriver(service);
    }

    @AfterAll
    public static void closeDriver() {
        if(driver != null) {
            driver.quit();
        }
        if(service.isRunning()) {
            service.close();
        }
    }
}
