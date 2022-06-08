package ru.netology.requests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;

import static io.restassured.RestAssured.given;

@UtilityClass
public class Request {
    public static RequestSpecification requestSpec(String baseUri, int port, ContentType Accept, ContentType contentType, LogDetail log) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setPort(port)
                .setAccept(Accept)
                .setContentType(contentType)
                .log(log)
                .build();
    }

    public static void send(RequestSpecification spec, Object body, String path, int statusCode) {
        given() // "дано"
                .spec(spec) // указываем, какую спецификацию используем
                .body(body) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post(path) // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(statusCode); // код 200 OK
    }
}
