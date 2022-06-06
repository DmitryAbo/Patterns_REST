package ru.netology.requests;

import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;

import static io.restassured.RestAssured.given;
@UtilityClass
public class Request {
    public static void send(RequestSpecification spec,Object body,String path,int statusCode){
        given() // "дано"
                .spec(spec) // указываем, какую спецификацию используем
                .body(body) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post(path) // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(statusCode); // код 200 OK
    }
}
