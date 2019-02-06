package com.setpm.web.api.service;

import com.setpm.web.api.model.User;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserApiService {

    public RequestSpecification setup() {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .filters(new RequestLoggingFilter(),
                        new ResponseLoggingFilter(),
                        new AllureRestAssured());
    }

    public ValidatableResponse loginUser(User user) {
        log.info("Registering user {}", user);

        return setup()
                .body(user)
                .when()
                .post("user-service/auth/login")
                .then();
    }

}
