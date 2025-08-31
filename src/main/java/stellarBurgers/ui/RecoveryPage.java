package stellarBurgers.ui;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

public class RecoveryPage {

    private WebDriver driver;
    public static final Duration longWait = Duration.ofSeconds(3);
    public static final String RECOVERY_PASSWORD_URL = "https://stellarburgers.nomoreparties.site/forgot-password";

    //Локатор для "Войти"
    private By loginButton = By.xpath(".//a[text()='Войти']");

    public RecoveryPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Проверка открытия страницы восстановления пароля")
    public void waitForRecoveryPage() {
        new WebDriverWait(driver, longWait)
                .until(driver -> Objects.equals(driver.getCurrentUrl(), RECOVERY_PASSWORD_URL));
    }

    @Step("Нажатие кнопки 'Войти' на странице восстановления пароля")
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

}
