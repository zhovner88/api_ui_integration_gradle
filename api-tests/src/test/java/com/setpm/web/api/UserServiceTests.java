package com.setpm.web.api;

import com.setpm.web.api.model.User;
import com.setpm.web.api.service.UserApiService;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNot.not;

public class UserServiceTests {

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
    @Story("VSTS-179 - As a VZ Admin I want to login to TSM Lite")
    @Description("Test can't login user with invalid credentials")
    public void testCanNotLoginUserWithInvalidCredentials() {
        // given
        User user = new User()
                .setUsername("test4")
                .setPassword("wrongPassword");

        // expect
        userApiService.loginUser(user).assertThat().statusCode(401)
                .body("message", response -> equalTo("invalid_credentials"));
    }

    @Test
    @Story("VSTS-268 - As a Root Admin I want to create a user and set user role")
    @Description("Test can return a list of registered users")
    public void testCanReturnListOfRegisteredUsers() {
        // given
        ValidatableResponse validatableResponse = userApiService.getAllUsers();

        // expect
        validatableResponse
                .assertThat()
                .statusCode(200)
                .body("id", response -> not(isEmptyString()));
    }

    @Test
    @Story("VSTS-268 - As a Root Admin I want to create a user and set user role")
    @Description("Test can get existing user by ID")
    public void testCanGetUserById() {
        // given
        String userId = userApiService.getUserId("0");

        // when
        ValidatableResponse validatableResponse = userApiService.getUserById(userId);

        // then
        validatableResponse
                .assertThat()
                .statusCode(200)
                .body("id", response -> is(userId));
    }

    @Test
    @Story("VSTS-268 - As a Root Admin I want to create a user and set user role")
    @Description("Test can get existing user by ID")
    public void testCatHandleRequestToNonExistentUser() {
        // given
        String userId = "nonExistentValue";

        // when
        ValidatableResponse validatableResponse = userApiService.getUserById(userId);

        // then
        validatableResponse
                .assertThat()
                .statusCode(404)
                .body("message", response -> is("user_not_exists"));

    }
    

}
