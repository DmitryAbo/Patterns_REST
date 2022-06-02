package ru.netology.generatotrs;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.experimental.UtilityClass;
import ru.netology.users.UserInfo;

import java.util.Locale;

@UtilityClass
public class UserDataGeneratorJson {
    @UtilityClass
    public static class Registration {
        public static UserInfo registrationInfo(String locale, String status) {
            Faker faker = new Faker(new Locale(locale));
            return new UserInfo(faker.gameOfThrones().character(), faker.dog().name(), status);
        }
    }
}
