package com.setpm.web.api.service;

import com.github.javafaker.Faker;
import com.setpm.web.api.model.User;
import com.setpm.web.api.model.UserObject;
import com.setpm.web.api.model.UserRole;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

import static io.restassured.RestAssured.get;

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

    // login user
    public ValidatableResponse loginUser(UserObject user) {
        log.info("Registering user {}", user);

        return setup()
                .body(user)
                .when()
                .post("user-service/auth/login")
                .then();
    }

    // get list of roles
    public ValidatableResponse getAllRoles() {
        return setup()
                .when()
                .get("user-service/roles")
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

    // returns a specific role # from list af all roles
    public String getUserRole(String id) {
        ValidatableResponse validatableResponse = getAllRoles();
        String roleId = validatableResponse
                .extract().path("id[" + id + "]");
        return roleId;
    }

    // returns a specific username from list
    public String getUserName(String id) {
        ValidatableResponse validatableResponse = getAllUsers();
        String userName = validatableResponse
                .extract().path("username[" + id + "]");
        return userName;
    }

    // update user
    public ValidatableResponse updateUser(UserObject user, String userId) {
        return setup()
                .body(user)
                .when()
                .put("user-service/users/" + userId)
                .then();
    }

    // create mock user
    public UserObject createNewUser() {
        Faker faker = new Faker();
        ArrayList<String> uiccIds = new ArrayList<>();
        uiccIds.add("testUiccid");

        UserObject user = new UserObject()
                .setUsername(faker.name().username())
                .setPassword("password")
                .setGroupId("autotestGeneratedUsers")
                .setRoleId(getUserRole("0"))
                .setUiccIds(uiccIds);

        return user;
    }

    // create user with existing username
    public UserObject createUserWithExistingUsername() {
        ArrayList<String> uiccIds = new ArrayList<>();
        uiccIds.add("testUiccid");

        UserObject user = new UserObject()
                .setUsername(getUserName("0"))
                .setPassword("password")
                .setGroupId("autotestGeneratedUsers")
                .setRoleId(getUserRole("0"))
                .setUiccIds(uiccIds);

        return user;
    }

    // create user with non-existent role
    public UserObject createUserWithNonExistentRole() {
        Faker faker = new Faker();
        ArrayList<String> uiccIds = new ArrayList<>();
        uiccIds.add("testUiccid");

        UserObject user = new UserObject()
                .setUsername(faker.name().username())
                .setPassword("password")
                .setGroupId("autotestGeneratedUsers")
                .setRoleId(faker.idNumber().valid())
                .setUiccIds(uiccIds);

        return user;
    }

    // update existing user
    public UserObject updateExistingUser(String userId) {
        Faker faker = new Faker();
        ArrayList<String> uiccIds = new ArrayList<>();
        uiccIds.add("testUiccid");

        UserObject user = new UserObject()
                .setUsername(faker.name().username())
                .setPassword("password")
                .setGroupId("autotestGeneratedUsers")
                .setRoleId(getUserRole("0"))
                .setUiccIds(uiccIds)
                .setId(userId);

        return user;
    }

    // update user with existent username
    public UserObject createUserWithExistentUsername() {
        ValidatableResponse validatableResponse = getAllUsers();

        UserObject user = new UserObject()
                .setId(validatableResponse.extract().body().jsonPath().get("id[0]"))
                .setUsername(validatableResponse.extract().body().jsonPath().get("username[1]"))
                .setGroupId(validatableResponse.extract().body().jsonPath().get("groupId[0]"))
                .setRoleId(validatableResponse.extract().body().jsonPath().get("roleId[0]"));

        return user;
    }

    // Create user with non-existent roleId
    public UserObject createUserWithNonExistentRoleId() {
        ValidatableResponse validatableResponse = getAllUsers();
        Faker faker = new Faker();

        UserObject user = new UserObject()
                .setId(validatableResponse.extract().body().jsonPath().get("id[0]"))
                .setUsername(validatableResponse.extract().body().jsonPath().get("username[0]"))
                .setGroupId(validatableResponse.extract().body().jsonPath().get("groupId[0]"))
                .setRoleId(faker.idNumber().invalid());

        return user;
    }

    // Delete user by id
    public ValidatableResponse deleteUserById(String userId) {
        log.info("Deleting the user with id: {}", userId);

        return setup()
                .when()
                .delete("user-service/users/" + userId)
                .then();
    }

    // method returns id field from response
    public String extractUserIdFromResponse(ValidatableResponse response) {
        String userId = response.extract().body().jsonPath().get("id");
        return userId;
    }

    // get role by id
    public ValidatableResponse getRoleById(String roleId) {
        log.info("Getting role with id {}", roleId);

        return setup()
                .when()
                .get("user-service/roles/" + roleId)
                .then();
    }

    // post a new user role
    public ValidatableResponse createNewUserRole() {
        UserRole userRole = createNewRoleObject();

        return setup()
                .when()
                .body(userRole)
                .post("user-service/roles")
                .then();
    }

    // create a new role object
    public UserRole createNewRoleObject() {
        Faker faker = new Faker();
        ArrayList<String> rolesList = new ArrayList<>();
        rolesList.add("access_content_any");
        rolesList.add("read_role_list");

        UserRole userRole = new UserRole()
                .setRoleName("autotest_" + faker.idNumber().valid())
                .setPermissions(rolesList);

        return userRole;
    }

    // create a new role with a specific name
    public UserRole createNewRoleObject(String roleName) {
        Faker faker = new Faker();
        ArrayList<String> rolesList = new ArrayList<>();
        rolesList.add("access_content_any");
        rolesList.add("read_role_list");

        UserRole userRole = new UserRole()
                .setRoleName(roleName)
                .setPermissions(rolesList);

        return userRole;
    }

    // create a new role with already a specific name
    public ValidatableResponse createNewUserRoleWithSpecifiedName(String roleName) {

        UserRole userRole = createNewRoleObject(roleName);

        return setup()
                .when()
                .body(userRole)
                .post("user-service/roles")
                .then();
    }

    // get an existing role name
    public String getExistingRoleName() {
        ValidatableResponse listOfAllRoles = getAllRoles();
        return listOfAllRoles.extract().body().jsonPath().get("roleName[0]");
    }


    // create a new role with a specific roleId
    public UserRole createNewRoleObjectWithId(String roleId) {
        Faker faker = new Faker();
        ArrayList<String> rolesList = new ArrayList<>();
        rolesList.add("access_content_any");
        rolesList.add("read_role_list");

        UserRole userRole = new UserRole()
                .setRoleName("autotest_" + faker.idNumber().valid())
                .setPermissions(rolesList)
                .setId(roleId);

        return userRole;
    }


    // get list of roles
    public ValidatableResponse getListOfAllRoles() {
        return setup()
                .get("user-service/roles")
                .then();
    }

    // return an existing role from list
    public String returnRoleIdFromList(ValidatableResponse validatableResponse, String roleNumberInList) {
        String roleId = validatableResponse.extract().body().jsonPath().get("id[roleNumberInList]");
        return roleId;
    }

    // return a role id from created role object
    public String returnRoleIdFromOjbect(ValidatableResponse validatableResponse) {
        String roleId = validatableResponse.extract().body().jsonPath().get("id");
        return roleId;
    }

    // update an existing role with given roleId
    public ValidatableResponse editRole(String roleId) {
        UserRole userRole = createNewRoleObjectWithId(roleId);

        return setup()
                .body(userRole)
                .when()
                .put("user-service/roles/" + roleId)
                .then();
    }

    // returns a role ID with "not_editable" status value
    public String findNotEditableRoleId() {
        return get("user-service/roles").path("find { it.status == 'not_editable' }.id");
    }

    // delete user role with given roleId
    public ValidatableResponse deleteRoleById(String roleId) {
        return setup()
                .when()
                .delete("user-service/roles/" + roleId)
                .then();
    }

}
