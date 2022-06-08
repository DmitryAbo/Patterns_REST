import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.generatotrs.UserDataGenerator;
import ru.netology.requests.Request;
import ru.netology.users.UserInfo;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;

public class AuthTestUnActiveUser {
    static UserInfo auth = UserDataGenerator.Registration.registrationInfo("en", "blocked");
    UserInfo authFail = UserDataGenerator.Registration.registrationFail("en", "active");

    @BeforeAll
    static void setUpAll() {
        RequestSpecification requestSpec = Request.requestSpec("http://localhost", 9999, ContentType.JSON, ContentType.JSON, LogDetail.ALL);
        Request.send(requestSpec, auth, "/api/system/users", 200);
    }

    @BeforeEach
    public void startBrowser() {
        open("http://localhost:9999/");
    }


    @Test
    public void shouldFailValidAuth() {
        $x("//span[@data-test-id=\"login\"]//child::input").setValue(auth.getLogin());
        $x("//span[@data-test-id=\"password\"]//child::input").setValue(auth.getPassword());
        $(".button[data-test-id=\"action-login\"]").click();
        $("div[data-test-id=\"error-notification\"]").should(visible, Duration.ofSeconds(15));
        $x("//div[text() = \"Пользователь заблокирован\"]").should(visible, Duration.ofSeconds(15));
    }

    @Test
    public void shouldFailInvalidPassword() {
        $x("//span[@data-test-id=\"login\"]//child::input").setValue(auth.getLogin());
        $x("//span[@data-test-id=\"password\"]//child::input").setValue(authFail.getPassword());
        $(".button[data-test-id=\"action-login\"]").click();
        $x("//div[text() = \"Ошибка\"]").should(visible, Duration.ofSeconds(15));
        $x("//div[text() = \"Неверно указан логин или пароль\"]").should(visible, Duration.ofSeconds(15));
    }

    @Test
    public void shouldFailInvalidLogin() {
        $x("//span[@data-test-id=\"login\"]//child::input").setValue(authFail.getLogin());
        $x("//span[@data-test-id=\"password\"]//child::input").setValue(auth.getPassword());
        $(".button[data-test-id=\"action-login\"]").click();
        $x("//div[text() = \"Ошибка\"]").should(visible, Duration.ofSeconds(15));
        $x("//div[text() = \"Неверно указан логин или пароль\"]").should(visible, Duration.ofSeconds(15));
    }


}
