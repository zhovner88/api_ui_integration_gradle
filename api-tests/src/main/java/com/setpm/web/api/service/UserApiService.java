package com.setpm.web.api.service;

import com.setpm.web.api.model.User;
import com.setpm.web.api.model.UserObject;
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

    // logging in a user
    public ValidatableResponse loginUser(User user) {
        log.info("Registering user {}", user);

        return setup()
                .body(user)
                .when()
                .post("user-service/auth/login")
                .then();
    }

    // returns a response with a list of all users
    public ValidatableResponse getAllUsers() {
        log.info("Getting the user list");

        return setup()
                .when()
                .get("user-service/users")
                .then();
    }

    // register a new user
    public ValidatableResponse registerUser(UserObject userObject) {
        log.info("Registering a new user {}: ", userObject);

        return setup()
                .body(userObject)
                .when()
                .post("user-service/users")
                .then();
    }

    // get a user by Id
    public ValidatableResponse getUserById(String id) {
        log.info("Getting user by id: ");

        return setup()
                .when()
                .get("user-service/users/" + id)
                .then();
    }

    // returns user id value as a String
    public String getUserId(String id) {
        ValidatableResponse validatableResponse = getAllUsers();
        String userId = validatableResponse
                .extract().path("id[" + id + "]");
        return userId;
    }

}
