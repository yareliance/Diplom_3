package stellarBurgers;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import stellarBurgers.ui.MainPage;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConstructorBurgersTest {

    @RegisterExtension
    private DriverExtension extension = new DriverExtension();

    private WebDriver driver;
    private MainPage mainPage;

    @BeforeEach
    @Step("Открыть браузер на главной странице")
    public void setUp() {
        driver = extension.getDriver();
        mainPage = new MainPage(driver);
        mainPage.openMainPage();
    }

    @AfterEach
    @Step("Закрыть браузер")
    public void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Проверка перехода в раздел 'Булки'")
    @Description("Проверка корректной работы перехода в раздел булок в конструкторе")
    public void testBunsSection() {
        // Сначала переходим в другой раздел, чтобы проверить переключение на раздел булок
        mainPage.clickSauces();

        // Возвращаемся в раздел булок
        mainPage.clickBuns();

        // Добавляем ожидание появления раздела булок
        new WebDriverWait(driver, MainPage.longWait)
                .until(ExpectedConditions.visibilityOfElementLocated(mainPage.bunsName));

        // Проверяем, что активное название раздела соответствует разделу булок
        assertEquals("Булки", mainPage.getActiveTabText(),
                "Не удалось перейти в раздел 'Булки'");
    }

    @Test
    @DisplayName("Проверка перехода в раздел 'Соусы'")
    @Description("Проверка корректной работы перехода в раздел соусов в конструкторе")
    public void testSaucesSection() {
        mainPage.clickSauces();

        assertEquals("Соусы", mainPage.getActiveTabText(),
                "Не удалось перейти в раздел 'Соусы'");
    }

    @Test
    @DisplayName("Проверка перехода в раздел 'Начинки'")
    @Description("Проверка корректной работы перехода в раздел начинок в конструкторе")
    public void testFillingSection() {
        mainPage.clickFillings();

        assertEquals("Начинки", mainPage.getActiveTabText(),
                "Не удалось перейти в раздел 'Начинки'");
    }

    @Test
    @DisplayName("Проверка начального состояния конструктора")
    @Description("Проверка того, что при открытии страницы конструктор находится в разделе 'Булки'")
    public void testInitialState() {
        // При открытии страницы конструктор должен быть в разделе булок
        assertEquals("Булки", mainPage.getActiveTabText(),
                "При открытии страницы конструктор не находится в разделе 'Булки'");
    }


}
