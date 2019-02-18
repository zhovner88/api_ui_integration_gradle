package com.setpm.web.api.service;

import com.github.javafaker.Faker;
import com.setpm.web.api.model.UICCgroup;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
public class ContentApiService {

    public RequestSpecification setup() {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .filters(new RequestLoggingFilter(),
                        new ResponseLoggingFilter(),
                        new AllureRestAssured());
    }

    // get list of all UICCgroup
    public ValidatableResponse getListAllUICC() {
        return setup()
                .when()
                .get("content-service/uicc")
                .then();
    }

    // post a new UICCgroup group
    public ValidatableResponse createUICCgroup(UICCgroup uiccGroup) {
        return setup()
                .body(uiccGroup)
                .when()
                .post("content-service/uicc_groups")
                .then();
    }

    // create a new UICCgroup group object
    public UICCgroup createNewUiccGroupObject() {
        Faker faker = new Faker();

        ArrayList<String> UiccList = new ArrayList<>();
        UiccList.add("1000000001");
        UiccList.add("1000000002");
        UiccList.add("1000000003");

        UICCgroup group = new UICCgroup()
                .setDescription("auto generated group " + faker.idNumber().valid())
                .setIccids(UiccList);

        return group;
    }

    // get newly created
    public String getUiccGroupId(ValidatableResponse validatableResponse) {
        return validatableResponse.extract().path("id");
    }

    // return a newly created UICCgroup group ID
    public String getNewlyCreatedUICCgroupId() {
        return getUiccGroupId(createUICCgroup(createNewUiccGroupObject()));
    }

    // get UICCgroup by ID
    public ValidatableResponse getUiccGroupById(String id) {
        return setup()
                .when()
                .get("content-service/uicc/" + id)
                .then();

    }

    // delete UICCgroup by ID
    public ValidatableResponse deleteUiccGroupById(String id) {
        return setup()
                .when()
                .delete("content-service/uicc/" + id)
                .then();
    }

    // get list of UICCgroup groups
    public ValidatableResponse getListOfUICCgroups() {
        return setup()
                .when()
                .get("content-service/uicc_groups")
                .then();
    }

}
