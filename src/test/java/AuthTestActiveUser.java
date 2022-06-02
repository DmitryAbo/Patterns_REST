import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.generatotrs.UserDataGeneratorJson;
import ru.netology.users.UserInfo;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;

public class AuthTestActiveUser {
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://127.0.0.1")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    static Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    static UserInfo auth = UserDataGeneratorJson.Registration.registrationInfo("en", "active");

    @BeforeAll
    static void setUpAll() {
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(gson.toJson(auth)) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    @BeforeEach
    public void startBrowser() {
        open("http://127.0.0.1:9999/");
    }


    @Test
    public void shouldSuccess() {
        $x("//span[@data-test-id=\"login\"]//child::input").setValue(auth.getLogin());
        $x("//span[@data-test-id=\"password\"]//child::input").setValue(auth.getPassword());
        $(".button[data-test-id=\"action-login\"]").click();
        $x("//h2[contains(text(),'кабинет')]").should(visible, Duration.ofSeconds(15));
    }

    @Test
    public void shouldFailInvalidPassword() {
        $x("//span[@data-test-id=\"login\"]//child::input").setValue(auth.getLogin());
        $x("//span[@data-test-id=\"password\"]//child::input").setValue(auth.getPassword() + "fail");
        $(".button[data-test-id=\"action-login\"]").click();
        $x("//div[text() = \"Ошибка\"]").should(visible, Duration.ofSeconds(15));
        $x("//div[text() = \"Неверно указан логин или пароль\"]").should(visible, Duration.ofSeconds(15));
    }

    @Test
    public void shouldFailInvalidLogin() {
        $x("//span[@data-test-id=\"login\"]//child::input").setValue(auth.getLogin() + "fail");
        $x("//span[@data-test-id=\"password\"]//child::input").setValue(auth.getPassword());
        $(".button[data-test-id=\"action-login\"]").click();
        $x("//div[text() = \"Ошибка\"]").should(visible, Duration.ofSeconds(15));
        $x("//div[text() = \"Неверно указан логин или пароль\"]").should(visible, Duration.ofSeconds(15));
    }


}
