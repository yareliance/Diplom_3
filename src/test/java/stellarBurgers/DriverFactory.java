package stellarBurgers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class DriverFactory {
    WebDriver driver;
    private String browserName;

    public DriverFactory() {
        browserName = System.getProperty("browser", "chrome"); // значение по умолчанию
    }

    public void initDriver() {
        String browser = System.getProperty("browser");
        if ("yandex".equals(System.getProperty("browser"))) {
           initYandex();
        } else {
            setupChrome();
        }
    }

    public String getBrowserName() {
        return browserName;
    }

    public void setupChrome() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }

    public void initYandex() {
        WebDriverManager.chromedriver().browserVersion("138").setup();
        var options = new ChromeOptions();
        options.setBinary("C:\\Users\\user\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe");
        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

    }

    public WebDriver getDriver() {
        return driver;
    }
}
