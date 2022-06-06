package ru.netology.requestSpecifications;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Specification {
    public static RequestSpecification requestSpec(String baseUri,int port,ContentType Accept,ContentType contentType,LogDetail log) {
            return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setPort(port)
                .setAccept(Accept)
                .setContentType(contentType)
                .log(log)
                .build();
    }
}
