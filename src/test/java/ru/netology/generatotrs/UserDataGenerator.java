package ru.netology.generatotrs;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;
import ru.netology.users.UserInfo;

import java.util.Locale;

@UtilityClass
public class UserDataGenerator {
    @UtilityClass
    public static class Registration {
        public static UserInfo registrationInfo(String locale, String status) {
            Faker faker = new Faker(new Locale(locale));
            return new UserInfo(faker.gameOfThrones().character(), faker.dog().name(), status);
        }

        public static UserInfo registrationFail(String locale, String status) {
            Faker faker = new Faker(new Locale(locale));
            return new UserInfo(faker.gameOfThrones().character(), faker.dog().name(), status);
        }
    }
}
