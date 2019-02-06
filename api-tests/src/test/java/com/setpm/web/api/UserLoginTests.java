package com.setpm.web.api;

import com.setpm.web.api.model.User;
import com.setpm.web.api.service.UserApiService;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.core.IsNot.not;

public class UserLoginTests {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://api.dev.se-tpm.zone";
    }

    UserApiService userApiService = new UserApiService();

    @Test
    @Description("Test can login user with valid credentials")
    public void testCanLoginUserWithValidCredentials() {
        // given
        User user = new User()
                .setUsername("test4")
                .setPassword("test4");

        // expect
        userApiService.loginUser(user).assertThat().body("id", not(isEmptyString()));
    }

    @Test
    @Description("Test can't login user with invalid credentials")
    public void testCanNotLoginUserWithInvalidCredentials() {
        // given
        User user = new User()
                .setUsername("test4")
                .setPassword("wrongPassword");

        // expect
        userApiService.loginUser(user).assertThat().statusCode(401)
                .body("message", equalTo("invalid_credentials"));
    }

}
