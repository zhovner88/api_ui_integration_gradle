package com.setpm.web.api.service;

import com.github.javafaker.Faker;
import com.setpm.web.api.model.UICCmodel.UICC;
import com.setpm.web.api.model.UICCmodel.UICCgroup;
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

    // get newly created group ID
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

    // get list of UICCs
    public ValidatableResponse getListOfUICCs() {
        return setup()
                .when()
                .get("content-service/uicc")
                .then();
    }

    // post new UICC object
    public ValidatableResponse postNewUICCobject(UICC uicc) {
        return setup()
                .body(uicc)
                .when()
                .post("content-service/uicc")
                .then();
    }

    // create new UICC object
    public UICC createNewUiccObject() {
        Faker faker = new Faker();

        ArrayList<String> UiccList = new ArrayList<>();
        UiccList.add("6e443116-eb71-4fcd-8645-134b2254d749");
        UiccList.add("15aecf70-7a84-4bf9-8777-9386a71cfe94");

        UICC uicc = new UICC()
                .setDescription("auto generated UICC " + faker.idNumber().valid())
                .setUserIds(UiccList);

        return uicc;
    }

    // get UICC by ID
    public ValidatableResponse getUiccById(String id) {
        return setup()
                .when()
                .get("content-service/uicc/" + id)
                .then();

    }

    public String getUiccId(ValidatableResponse validatableResponse) {
        return validatableResponse.extract().path("id");
    }

    // return a newly created UICCgroup group ID
    public String getNewlyCreatedUICCid() {
        return getUiccId(postUICC(createNewUiccObject()));
    }

    // post a new UICC object
    public ValidatableResponse postUICC(UICC uicc) {
        return setup()
                .body(uicc)
                .when()
                .post("content-service/uicc")
                .then();
    }

    // delete UICC by ID
    public ValidatableResponse deleteUiccById(String id) {
        return setup()
                .when()
                .delete("content-service/uicc/" + id)
                .then();
    }


}
