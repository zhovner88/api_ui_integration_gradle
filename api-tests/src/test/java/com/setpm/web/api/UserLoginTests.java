package com.setpm.web.api;

import com.setpm.web.api.model.User;
import com.setpm.web.api.service.UserApiService;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNot.not;

public class UserLoginTests {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://api.dev.se-tpm.zone";
    }

    UserApiService userApiService = new UserApiService();

    @Test
    @Story("VSTS-179 - As a VZ Admin I want to login to TSM Lite")
    @Description("Test can login user with valid credentials")
    public void testCanLoginUserWithValidCredentials() {
        // given
        User user = new User()
                .setUsername("test4")
                .setPassword("test4");

        // expect
        userApiService.loginUser(user).assertThat().statusCode(200)
                .body("token", response -> notNullValue());
    }

    @Test
    @Description("Test can't login user with invalid credentials")
    @Story("VSTS-179 - As a VZ Admin I want to login to TSM Lite")
    public void testCanNotLoginUserWithInvalidCredentials() {
        // given
        User user = new User()
                .setUsername("test4")
                .setPassword("wrongPassword");

        // expect
        userApiService.loginUser(user).assertThat().statusCode(401)
                .body("message", response -> equalTo("invalid_credentials"));
    }

}
