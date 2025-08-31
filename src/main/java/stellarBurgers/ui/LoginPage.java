package stellarBurgers.ui;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private final WebDriver driver;

    public static final String LOGIN_PAGE_URL = "https://stellarburgers.nomoreparties.site/login";

    //Локатор поля "Пароль"
    private final By passwordField = By.xpath("//input[@name='Пароль' and @type='password']");
    //Локатор поля "Email"
    private final By emailField = By.xpath("//label[text()='Email']/following-sibling::input[@name='name']");
    //Локатор поля "Восстановить пароль"
    private final By resetPasswordButton = By.xpath("//a[contains(text(),'Восстановить пароль')]");
    //Локатор кнопки "Вход"
    private final By loginButton = By.xpath(".//button[text()='Войти']");

    //Конструктор
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Нажать кнопку 'Войти'")
    public void clickEnterButton() {
        driver.findElement(loginButton).click();
    }

    @Step("Нажать кнопку 'Восстановить пароль'")
    public void clickRecoveryPassword() {
        driver.findElement(resetPasswordButton).click();
    }

    @Step("Логин-ввод данных пользователя")
    public void login(String email, String password) {
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
    }

}
