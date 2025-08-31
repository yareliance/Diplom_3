package stellarBurgers.api;

import org.apache.commons.lang3.RandomStringUtils;

public class UserRandom {
    private String email;
    private String password;
    private String name;

    // Добавляем конструктор без параметров
    public UserRandom() {
        this.email = RandomStringUtils.randomAlphanumeric(8) + "@example.com";
        this.password = RandomStringUtils.randomAlphanumeric(6);
        this.name = RandomStringUtils.randomAlphabetic(10);
    }

    // Сохраняем существующий конструктор для совместимости
    public UserRandom(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    // Геттеры и сеттеры
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

}