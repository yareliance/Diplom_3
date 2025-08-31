package stellarBurgers;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.*;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static java.time.Duration.ofSeconds;
import stellarBurgers.api.UserClient;
import stellarBurgers.api.UserRandom;
import stellarBurgers.ui.RegistrationPage;

public class RegistrationTest {

    @RegisterExtension
    private DriverExtension extension = new DriverExtension();

    private WebDriver driver;
    private RegistrationPage registrationPage;
    private UserClient userClient = new UserClient();
    private String accessToken;
    private UserRandom user;

    @BeforeEach
    @Step("Подготовка тестовых данных и открытие страницы регистрации")
    public void setUp() {
        driver = extension.getDriver();
        registrationPage = new RegistrationPage(driver);
        registrationPage.openRegistrationPage();

        user = new UserRandom();

    }

    @AfterEach
    @Step("Очистка тестовых данных")
    public void tearDown() {
        userClient.deletingUsersAfterTests(accessToken);
        driver.quit();
    }

    @Test
    @DisplayName("Успешная регистрация")
    @Description("Проверка успешной регистрации пользователя")
    public void testRegistrationSuccess() {
        registrationPage.registration(user.getEmail(), user.getPassword(), user.getName());
        registrationPage.clickRegistrationButton();

        new WebDriverWait(driver, ofSeconds(5))
                .until(ExpectedConditions.urlContains("login"));

        assertTrue(driver.getCurrentUrl().contains("login"));

        // Получаем токен через API
        Response response = userClient.loginUser(user);
        accessToken = userClient.getAccessToken(response.then());
    }

    @Test
    @DisplayName("Регистрация с некорректным паролем")
    @Description("Проверка регистрации с некорректным паролем")
    public void testRegistrationWithInvalidPassword() {
        // Используем короткий пароль
        String invalidPassword = RandomStringUtils.randomAlphabetic(5);

        registrationPage.registration(user.getEmail(), invalidPassword, user.getName());
        registrationPage.clickRegistrationButton();

        new WebDriverWait(driver, ofSeconds(5))
                .until(driver -> registrationPage.errorTextDisplayed());

        assertTrue(registrationPage.errorTextDisplayed(), "Сообщение об ошибке не отобразилось");
    }

}
