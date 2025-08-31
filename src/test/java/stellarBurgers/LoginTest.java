package stellarBurgers;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import stellarBurgers.api.UserClient;
import stellarBurgers.api.UserRandom;
import stellarBurgers.ui.LoginPage;
import stellarBurgers.ui.MainPage;
import stellarBurgers.ui.RegistrationPage;
import stellarBurgers.ui.RecoveryPage;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static java.time.Duration.ofSeconds;

public class LoginTest {

    @RegisterExtension
    private DriverExtension extension = new DriverExtension();

    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegistrationPage registrationPage;
    private RecoveryPage recoveryPage;
    private UserClient userClient = new UserClient();
    private String accessToken;
    private UserRandom user;

    @BeforeEach
    @Step("Подготовка тестовых данных и открытие главной страницы")
    public void setUp() {
        driver = extension.getDriver();

        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        registrationPage = new RegistrationPage(driver);
        recoveryPage = new RecoveryPage(driver);

        mainPage.openMainPage();

        // Создаем случайного пользователя
        user = new UserRandom();
        Response response = userClient.registerUser(user);
        accessToken = userClient.getAccessToken(response.then());

    }

    @AfterEach
    @Step("Очистка тестовых данных")
    public void tearDown() {
        userClient.deletingUsersAfterTests(accessToken);
        driver.quit();
    }

    @Test
    @DisplayName("Вход через кнопку 'Войти в аккаунт' на главной странице")
    @Description("Проверка входа через кнопку 'Войти в аккаунт' на главной странице")
    public void testLoginFromMainPage() {

        mainPage.clickOpenAccount();

        new WebDriverWait(driver, ofSeconds(5))
                .until(ExpectedConditions.urlContains("login"));

        loginPage.login(user.getEmail(), user.getPassword());
        loginPage.clickEnterButton();

        new WebDriverWait(driver, ofSeconds(5))
                .until(ExpectedConditions.urlToBe(MainPage.MAIN_PAGE_URL));

        assertTrue(driver.getCurrentUrl().contains(MainPage.MAIN_PAGE_URL),
                "После входа URL не соответствует ожидаемому");
        assertTrue(mainPage.getOrderButtonText().contains("Оформить заказ"),
                "Кнопка оформления заказа не найдена на странице");
    }

    @Test
    @DisplayName("Вход через кнопку 'Личный Кабинет'")
    @Description("Проверка входа через кнопку 'Личный Кабинет'")
    public void testLoginFromPersonalAccount() {

        mainPage.clickPersonal();

        new WebDriverWait(driver, ofSeconds(5))
                .until(ExpectedConditions.urlContains("login"));

        loginPage.login(user.getEmail(), user.getPassword());
        loginPage.clickEnterButton();

        new WebDriverWait(driver, ofSeconds(5))
                .until(ExpectedConditions.urlToBe(MainPage.MAIN_PAGE_URL));

        assertTrue(driver.getCurrentUrl().contains(MainPage.MAIN_PAGE_URL),
                "После входа URL не соответствует ожидаемому");
        assertTrue(mainPage.getOrderButtonText().contains("Оформить заказ"),
                "Кнопка оформления заказа не найдена на странице");
    }

    @Test
    @DisplayName("Вход через форму регистрации")
    @Description("Проверка входа через форму регистрации")
    public void testLoginFromRegistrationForm() {

        registrationPage.openRegistrationPage();
        registrationPage.clickEnterButton();

        new WebDriverWait(driver, ofSeconds(5))
                .until(ExpectedConditions.urlContains("login"));

        loginPage.login(user.getEmail(), user.getPassword());
        loginPage.clickEnterButton();

        new WebDriverWait(driver, ofSeconds(5))
                .until(ExpectedConditions.urlToBe(MainPage.MAIN_PAGE_URL));

        assertTrue(driver.getCurrentUrl().contains(MainPage.MAIN_PAGE_URL),
                "После входа URL не соответствует ожидаемому");
        assertTrue(mainPage.getOrderButtonText().contains("Оформить заказ"),
                "Кнопка оформления заказа не найдена на странице");
    }

    @Test
    @DisplayName("Вход через форму восстановления пароля")
    @Description("Проверка входа через форму восстановления пароля")
    public void testLoginFromRecoveryForm() {

        mainPage.clickPersonal();

        // Открываем форму восстановления пароля
        loginPage.clickRecoveryPassword();
        // Ждем открытия страницы восстановления пароля
        recoveryPage.waitForRecoveryPage();

        recoveryPage.clickLoginButton();

        new WebDriverWait(driver, ofSeconds(5))
                .until(ExpectedConditions.urlContains("login"));

        loginPage.login(user.getEmail(), user.getPassword());
        loginPage.clickEnterButton();

        new WebDriverWait(driver, ofSeconds(5))
                .until(ExpectedConditions.urlToBe(MainPage.MAIN_PAGE_URL));

        assertTrue(driver.getCurrentUrl().contains(MainPage.MAIN_PAGE_URL),
                "После входа URL не соответствует ожидаемому");
        assertTrue(mainPage.getOrderButtonText().contains("Оформить заказ"),
                "Кнопка оформления заказа не найдена на странице");
    }

}


