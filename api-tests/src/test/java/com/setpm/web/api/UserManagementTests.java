package com.setpm.web.api;

import com.setpm.web.api.model.userModel.User;
import com.setpm.web.api.model.userModel.UserObject;
import com.setpm.web.api.service.UserApiService;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNot.not;

public class UserManagementTests {

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
        UserObject user = userApiService.createNewUser();

        // when
        userApiService.registerUser(user);

        // and
        ValidatableResponse validatableResponse = userApiService.loginUser(user);

        // expect
        validatableResponse.assertThat().statusCode(200)
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
    @Description("Test can handle request to non existent user")
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

    @Test
    @Story("VSTS-268 - As a Root Admin I want to create a user and set user role")
    @Description("Test can register a new user")
    public void testCanRegisterNewUser() {
        // given
        UserObject user = userApiService.createNewUser();

        // when
        ValidatableResponse validatableResponse = userApiService.registerUser(user);

        // then
        validatableResponse.assertThat()
                .statusCode(200)
                .body("username", response -> is(user.getUsername()));

    }

    @Test
    @Story("VSTS-268 - As a Root Admin I want to create a user and set user role")
    @Description("Test checks if it's not possible to create a user with an existing username")
    public void testCannotRegisterUserWithExistingUsername() {
        // given
        UserObject existingUser = userApiService.createUserWithExistingUsername();

        // when
        ValidatableResponse validatableResponse = userApiService.registerUser(existingUser);

        // then
        validatableResponse.assertThat()
                .statusCode(400)
                .body("message", response -> is("user_exists"));

    }

    @Test
    @Story("VSTS-268 - As a Root Admin I want to create a user and set user role")
    @Description("Test can not register user with non-existent role")
    public void testCannotRegisterUserWithNonExistentRole() {
        // given
        UserObject user = userApiService.createUserWithNonExistentRole();

        // when
        ValidatableResponse validatableResponse = userApiService.registerUser(user);

        // then
        validatableResponse.assertThat()
                .statusCode(404)
                .body("message", response -> is("role_not_exists"));
    }

    @Test
    @Story("VSTS-268 - As a Root Admin I want to create a user and set user role")
    @Description("Test can update an existing user")
    public void testCanUpdateUser() {
        // given
        ValidatableResponse validatableResponse = userApiService.registerUser(userApiService.createNewUser());
        String userId = userApiService.extractUserIdFromResponse(validatableResponse);

        // when
        ValidatableResponse validatableResponseUpdatedUser =
                userApiService.updateUser(userApiService.updateExistingUser(userId), userId);

        // then
        validatableResponseUpdatedUser.assertThat()
                .statusCode(200)
                .body("updated", not(isEmptyString()));
    }

    @Test
    @Story("VSTS-268 - As a Root Admin I want to create a user and set user role")
    @Description("Test can not update user with already existing name")
    public void testCanNotUpdateUserWithExistingName() {
        // given
        UserObject user = userApiService.createUserWithExistentUsername();

        // when
        ValidatableResponse validatableResponse =
                userApiService.updateUser(user, user.getId());

        // then
        validatableResponse.assertThat()
                .statusCode(400)
                .body("message", response -> is("user_exists"));

    }

    @Test
    @Story("VSTS-268 - As a Root Admin I want to create a user and set user role")
    @Description("Test can not update user with non-existent role id")
    public void testCanNotUpdateUserWithNonExistentRoleId() {
        // given
        UserObject user = userApiService.createUserWithNonExistentRoleId();

        // when
        ValidatableResponse validatableResponse =
                userApiService.updateUser(user, user.getId());

        // then
        validatableResponse.assertThat()
                .statusCode(404)
                .body("message", response -> is("role_not_exists"));
    }

    @Test
    @Story("VSTS-268 - As a Root Admin I want to create a user and set user role")
    @Description("Test can delete an existing user")
    public void testCanDeleteExistingUser() {
        // given
        UserObject user = userApiService.createNewUser();
        ValidatableResponse validatableResponseUser = userApiService.registerUser(user);
        String userId = userApiService.extractUserIdFromResponse(validatableResponseUser);

        // when
        ValidatableResponse validatableResponseDelete =
                userApiService.deleteUserById(userId);

        // then
        validatableResponseDelete.assertThat()
                .statusCode(200);

        // and
        userApiService.getUserById(userId)
                .assertThat()
                .statusCode(404)
                .body("message", response -> is("user_not_exists"));
    }

    @Test
    @Story("VSTS-268 - As a Root Admin I want to create a user and set user role")
    @Description("Test can handle getting non existing role id")
    public void testCanHandleGettingNonExistingRoleId() {
        // when
        ValidatableResponse validatableResponse = userApiService.getRoleById("inexistingRoleId");

        // assert
        validatableResponse.assertThat()
                .statusCode(404)
                .body("message", response -> is("role_not_exists"));
    }

    @Test
    @Story("VSTS-268 - As a Root Admin I want to create a user and set user role")
    @Description("Test can create a new user role")
    public void testCanCreateNewRole() {
        // when
        ValidatableResponse validatableResponse = userApiService.createNewUserRole();

        // assert
        validatableResponse.assertThat()
                .statusCode(200)
                .body("id", response -> not(isEmptyString()));
    }

    @Test
    @Story("VSTS-268 - As a Root Admin I want to create a user and set user role")
    @Description("Test can edit a newly created UserRole")
    public void testCanEditUserRole() {
        // given
        String createdRoleId = userApiService.returnRoleIdFromOjbect(userApiService.createNewUserRole());

        // when
        ValidatableResponse validatableResponse = userApiService.editRole(createdRoleId);

        // then
        validatableResponse.assertThat()
                .statusCode(200)
                .body("updated", response -> not(isEmptyString()));
    }

    @Test
    @Story("VSTS-268 - As a Root Admin I want to create a user and set user role")
    @Description("Test can not create a user role with already existing name")
    public void testCanNotRegisterRoleWithExistingName() {
        // given
        String existingRoleName = userApiService.getExistingRoleName();

        // when
        ValidatableResponse validatableResponse = userApiService.createNewUserRoleWithSpecifiedName(existingRoleName);

        // then
        validatableResponse.assertThat()
                .statusCode(400)
                .body("message", response -> is("role_exists"));
    }

    @Test
    @Story("VSTS-268 - As a Root Admin I want to create a user and set user role")
    @Description("Test can not edit user role with not_editable status")
    public void testCanNotEditRoleWithNotEditableStatus() {
        // given
        String notEditableRoleId = userApiService.findNotEditableRoleId();

        // when
        ValidatableResponse validatableResponse = userApiService.editRole(notEditableRoleId);

        // then
        validatableResponse.assertThat()
                .statusCode(400)
                .body("message", response -> is("not_editable_role"));
    }
}
