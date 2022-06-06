import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.generatotrs.UserDataGenerator;
import ru.netology.users.UserInfo;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class AuthTestUserNotExist {
    static UserInfo auth = UserDataGenerator.Registration.registrationInfo("en", "blocked");

    @BeforeEach
    public void startBrowser() {
        open("http://localhost:9999/");
    }


    @Test
    public void shouldFailValidAuth() {
        $x("//span[@data-test-id=\"login\"]//child::input").setValue(auth.getLogin());
        $x("//span[@data-test-id=\"password\"]//child::input").setValue(auth.getPassword());
        $(".button[data-test-id=\"action-login\"]").click();
        $x("//div[text() = \"Ошибка\"]").should(visible, Duration.ofSeconds(15));
        $x("//div[text() = \"Неверно указан логин или пароль\"]").should(visible, Duration.ofSeconds(15));
    }

    @Test
    public void shouldFailLoginNull() {
        $x("//span[@data-test-id=\"login\"]//child::input").setValue("");
        $x("//span[@data-test-id=\"password\"]//child::input").setValue(auth.getPassword());
        $(".button[data-test-id=\"action-login\"]").click();
        $x("//span[@data-test-id=\"login\"]//child::span[@class = 'input__sub']").should(text("Поле обязательно для заполнения"));

    }

    @Test
    public void shouldFailPasswordNull() {
        $x("//span[@data-test-id=\"login\"]//child::input").setValue(auth.getLogin());
        $x("//span[@data-test-id=\"password\"]//child::input").setValue("");
        $(".button[data-test-id=\"action-login\"]").click();
        $x("//span[@data-test-id=\"password\"]//child::span[@class = 'input__sub']").should(text("Поле обязательно для заполнения"));

    }


}
