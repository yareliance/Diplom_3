package stellarBurgers;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import stellarBurgers.api.UserClient;
import stellarBurgers.api.UserRandom;
import stellarBurgers.ui.LoginPage;
import stellarBurgers.ui.MainPage;
import stellarBurgers.ui.PersonalAccountPage;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static java.time.Duration.ofSeconds;

public class PersonalAccountTest {

    @RegisterExtension
    private DriverExtension extension = new DriverExtension();

    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private PersonalAccountPage personalPage;
    private UserClient userClient = new UserClient();
    private String accessToken;
    private UserRandom user;

    @BeforeEach
    public void setUp() {
        driver = extension.getDriver();

        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        personalPage = new PersonalAccountPage(driver);

        mainPage.openMainPage();

        // Создаем случайного пользователя
        user = new UserRandom();
        Response response = userClient.registerUser(user);
        accessToken = userClient.getAccessToken(response.then());

        // Выполняем вход
        mainPage.clickOpenAccount();
        loginPage.login(user.getEmail(), user.getPassword());
        loginPage.clickEnterButton();
        new WebDriverWait(driver, ofSeconds(5))
                .until(ExpectedConditions.urlToBe(MainPage.MAIN_PAGE_URL));
    }

    @AfterEach
    @Step("Очистка тестовых данных")
    public void tearDown() {
        userClient.deletingUsersAfterTests(accessToken);
        driver.quit();
    }

    @Test
    @DisplayName("Переход в личный кабинет")
    @Description("Проверка перехода по клику на 'Личный кабинет'")
    public void testNavigateToPersonalAccount() {
        mainPage.clickPersonal();
        new WebDriverWait(driver, ofSeconds(5))
                .until(ExpectedConditions.urlToBe(PersonalAccountPage.PROFILE_PAGE_URL));

        assertTrue(driver.getCurrentUrl().contains(PersonalAccountPage.PROFILE_PAGE_URL),
                "Страница личного кабинета не открыта");
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор")
    @Description("Проверка перехода из ЛК по клику на 'Конструктор'")
    public void testNavigateToConstructor() {
        mainPage.clickPersonal();
        personalPage.clickConstructor();
        new WebDriverWait(driver, ofSeconds(5))
                .until(ExpectedConditions.urlToBe(MainPage.MAIN_PAGE_URL));

        assertTrue(driver.getCurrentUrl().contains(MainPage.MAIN_PAGE_URL),
                "Не произошел переход в конструктор - главная страница не открылась");
    }

    @Test
    @DisplayName("Переход из личного кабинета на главную через логотип")
    @Description("Проверка перехода по клику на логотип Stellar Burgers")
    public void testNavigateToMainViaLogo() {
        mainPage.clickPersonal();
        personalPage.clickLogo();
        new WebDriverWait(driver, ofSeconds(5))
                .until(ExpectedConditions.urlToBe(MainPage.MAIN_PAGE_URL));

        assertTrue(driver.getCurrentUrl().contains(MainPage.MAIN_PAGE_URL),
                "Не произошел переход на главную страницу через логотип");
    }

    @Test
    @DisplayName("Выход из аккаунта")
    @Description("Проверка выхода по кнопке 'Выйти' в личном кабинете")
    public void testLogout() {
        mainPage.clickPersonal();
        personalPage.clickLogout();
        new WebDriverWait(driver, ofSeconds(5))
                .until(ExpectedConditions.urlContains("login"));

        assertTrue(driver.getCurrentUrl().contains("login"),
                "Не произошел выход из аккаунта");
    }

}
