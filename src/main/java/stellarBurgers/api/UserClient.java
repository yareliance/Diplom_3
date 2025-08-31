package stellarBurgers.api;

import io.qameta.allure.Step;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UserClient {
    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site";
    private static final String REGISTER_URL = "/api/auth/register";
    private static final String LOGIN_URL = "/api/auth/login";
    public static final String AUTH_USER_URL = "/api/auth/user";

    @Step("Регистрация пользователя")
    public Response registerUser(UserRandom user) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .log().all() // логирование всех деталей запроса
                .body(user)
                .when()
                .post(REGISTER_URL);
    }

    @Step("Авторизация пользователя по логину и паролю")
    public Response loginUser(UserRandom user) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .log().all() // логирование всех деталей запроса
                .body(user)
                .when()
                .post(LOGIN_URL);
    }

    @Step("Удаление пользователя")
    public Response deleteUser(String accessToken) {
        return given()
                .baseUri(BASE_URL)
                .header("Authorization", accessToken)
                .log().all()
                .when()
                .delete(AUTH_USER_URL);
    }

    @Step("Получение access token")
    public String getAccessToken(ValidatableResponse validatableResponse) {
        return validatableResponse.extract().path("accessToken");
    }

    @Step("Удаление пользователя после тестов")
    public void deletingUsersAfterTests(String accessToken) {
        Response response = null;
        if (accessToken != null) {
            try {
                response = deleteUser(accessToken);

                // Проверка на null ответа
                if (response == null) {
                    throw new RuntimeException("Ответ сервера пустой");
                }

                // Валидация успешного удаления
                assertThat(response.statusCode(), equalTo(202));
                assertThat(response.jsonPath().getBoolean("success"), equalTo(true));
                assertThat(response.jsonPath().getString("message"), equalTo("User successfully removed"));

                System.out.println("Пользователь успешно удален");

            } catch (AssertionError e) {
                // Безопасная проверка на null
                if (response != null) {
                    System.out.println("Ошибка при проверке удаления пользователя:");
                    System.out.println(response.prettyPrint());
                }
                throw e;

            } catch (Exception e) {
                System.out.println("Произошла ошибка при удалении пользователя: " + e.getMessage());
            }
        } else {
            System.out.println("Не удалось удалить пользователя - отсутствует accessToken");
        }
    }
}