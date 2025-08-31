package stellarBurgers.ui;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class RegistrationPage {

    private final WebDriver driver;
    public static final Duration longWait = Duration.ofSeconds(3);

    public static final String REGISTRATION_PAGE_URL = "https://stellarburgers.nomoreparties.site/register";

    //Локатор поля "Имя"
    private final By nameField = By.xpath("//label[text()='Имя']/following-sibling::input[@name='name']");
    //Локатор поля "Email"
    private final By emailField = By.xpath("//label[text()='Email']/following-sibling::input[@name='name']");
    //Локатор поля "Пароль"
    private final By passwordField = By.xpath("//input[@name='Пароль' and @type='password']");
    //Локатор кнопки "Зарегистрироваться"
    private final By registrationButton = By.xpath("//button[text()='Зарегистрироваться']");
    //Локатор уведомления "Некорректный пароль"
    private final By registrationError = By.xpath("//p[contains(text(),'Некорректный пароль')]");
    //Локатор кнопки "Войти"
    private final By enterButton = By.xpath("//a[contains(text(),'Войти')]");

    // Конструктор
    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Открыть страницу регистрации")
    public void openRegistrationPage() {
        driver.manage().timeouts().implicitlyWait(longWait);
        driver.get(REGISTRATION_PAGE_URL);
    }

    @Step("Нажать Зарегистрироваться")
    public void clickRegistrationButton() {
        driver.findElement(registrationButton).click();
    }

    @Step("Нажать Войти")
    public void clickEnterButton() {
        driver.findElement(enterButton).click();
    }

    @Step("Проверка отображения сообщения 'Некорректный пароль'")
    public boolean errorTextDisplayed() {
        return driver.findElement(registrationError).isDisplayed();
    }

    @Step("Регистрация-ввод данных")
    public void registration(String email, String password, String name) {
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(nameField).sendKeys(name);
    }


}
