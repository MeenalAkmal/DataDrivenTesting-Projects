package orangehrm.base;
 
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
 
public class BaseTest {
    public WebDriver driver;
 
    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
 
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
 
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }
 
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}



