package stellarBurgers.ui;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {

    private final WebDriver driver;
    public static final Duration longWait = Duration.ofSeconds(5);
    public static final String MAIN_PAGE_URL = "https://stellarburgers.nomoreparties.site/";

    //Локатор кнопки "Личный кабинет" (в шапке)
    private final By personalAccountButton = By.xpath("//p[contains(text(),'Личный Кабинет')]");
    //Локатор кнопки "Войти в аккаунт" (под булочками, вместо кнопки "Оформить заказ" у неавторизованного пользователя)
    private final By openAccountButton = By.xpath("//button[text()='Войти в аккаунт']");
    //Локатор для "Оформить заказ" (только у авторизованного пользователя)
    private final By createdOrderButton = By.xpath("//*[contains(text(), 'Оформить заказ')]");
    //Локатор раздела булки
    private final By bunsButton = By.xpath(".//span[text()='Булки']");
    //Локатор названия булочки
    public final By bunsName = By.xpath(".//h2[text()='Булки']");
    //Локатор вида соуса
    private final By saucesButton = By.xpath("//span[contains(text(), 'Соусы')]");
    //Локатор вида начинки
    private final By fillingButton = By.xpath("//span[contains(text(), 'Начинки')]");
    //Локатор для активного раздела
    private final By activeTab = By.xpath("//div[contains(@class,'tab_tab_type_current')]");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Открыть главную страницу")
    public void openMainPage() {
        driver.manage().timeouts().implicitlyWait(longWait);
        driver.get(MAIN_PAGE_URL);
    }

    @Step("Нажать на кнопку 'Войти в аккаунт'")
    public void clickOpenAccount() {
        driver.findElement(openAccountButton).click();
    }

    @Step("Нажать на кнопку 'Личный Кабинет'")
    public void clickPersonal() {
        driver.findElement(personalAccountButton).click();
    }

    @Step("Проверить отображение кнопки 'Оформить заказ'")
    public String getOrderButtonText() {
        return driver.findElement(createdOrderButton).getText();
    }

    @Step("Нажать на раздел Булки")
    public void clickBuns() {
        driver.findElement(bunsButton).click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(bunsName));
    }

    @Step("Нажать на раздел Соусы")
    public void clickSauces() {
        driver.findElement(saucesButton).click();
    }

    @Step("Нажать на раздел Начинки")
    public void clickFillings() {
        driver.findElement(fillingButton).click();
    }

    @Step("Получить название активного (выбранного) раздела")
    public String getActiveTabText() {
        return driver.findElement(activeTab).getText();
    }
}




