package stellarBurgers.ui;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PersonalAccountPage {

    private final WebDriver driver;

    public static final String PROFILE_PAGE_URL = "https://stellarburgers.nomoreparties.site/account/profile";

    //Локатор кнопки "Выход"
    private final By logoutButton = By.xpath("//button[text()='Выход']");
    //Локатор кнопки "Конструктор"
    private final By сonstructorButton = By.xpath("//p[contains(text(),'Конструктор')]");
    //Локатор изображения логотипа
    private final By logoName = By.xpath(".//div[contains(@class,'logo')]/a");

    public PersonalAccountPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Нажать кнопку 'Выход'")
    public void clickLogout() {
        driver.findElement(logoutButton).click();
    }

    @Step("Нажать кнопку Конструктор")
    public void clickConstructor() {
        driver.findElement(сonstructorButton).click();
    }

    @Step("Нажать на изображение логотипа")
    public void clickLogo() {
        driver.findElement(logoName).click();
    }

}
