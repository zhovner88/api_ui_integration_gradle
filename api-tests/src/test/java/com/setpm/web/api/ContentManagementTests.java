package com.setpm.web.api;

import com.setpm.web.api.service.ContentApiService;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.core.IsNot.not;

public class ContentManagementTests {

    @BeforeAll
    static void SetUp() {
        RestAssured.baseURI = "http://api.dev.se-tpm.zone";
    }

    ContentApiService contentApiService = new ContentApiService();

    // UICC group tests

    @Test
    @Description("Test can get a list of UICCgroup groups")
    public void testCanGetListOfUICC() {
        // given
        ValidatableResponse validatableResponse = contentApiService.getListAllUICC();

        // expect
        validatableResponse.assertThat()
                .statusCode(200)
                .body("id[0]", response -> not(isEmptyString()));
    }

    @Test
    @Description("Test can create a new UICCgroup group")
    public void testCanCreateNewUiccGroup() {

        // given
        ValidatableResponse validatableResponse = contentApiService
                .createUICCgroup(contentApiService.createNewUiccGroupObject());

        // expect
        validatableResponse.assertThat()
                .statusCode(200)
                .body("description", response -> not(isEmptyString()));
    }

    @Test
    @Description("Test can get UICC group group by ID")
    @Disabled("VSTS-311")
    public void testCanGetUICCgroupById() {
        // given
        String id = contentApiService.getNewlyCreatedUICCgroupId();

        // when
        ValidatableResponse validatableResponse = contentApiService.getUiccGroupById(id);

        // then
        validatableResponse.assertThat()
                .statusCode(200)
                .body("id", response -> is(id));
    }

    @Test
    @Description("Test can delete UICCgroup group by ID")
    public void testCanDeleteUICCgroupById() {
        // given
        String id = contentApiService.getNewlyCreatedUICCgroupId();

        // when
        ValidatableResponse validatableResponse = contentApiService.deleteUiccGroupById(id);

        // then
        validatableResponse.assertThat()
                .statusCode(200);
    }

    // UICCs tests

    @Test
    @Description("Test can get list of UICCgroup groups")
    public void testCanGetListOfUiccGrops() {
        // when
        ValidatableResponse validatableResponse = contentApiService.getListOfUICCgroups();

        // expect
        validatableResponse.assertThat()
                .statusCode(200)
                .body("id", response -> not(isEmptyString()));
    }

    @Test
    @Description("Test can get list of UICCs")
    public void testCanGetListOfUICCs() {
        // when
        ValidatableResponse validatableResponse = contentApiService.getListOfUICCs();

        // expect
        validatableResponse.assertThat()
                .statusCode(200)
                .body("id", response -> not(isEmptyString()));
    }

    // test can create UICC
    @Test
    @Description("Test can create a new UICC")
    public void testCanCreateUICC() {
        // when
        ValidatableResponse validatableResponse = contentApiService
                .postNewUICCobject(contentApiService.createNewUiccObject());

        // expect
        validatableResponse.assertThat()
                .statusCode(200)
                .body("id", response -> not(isEmptyString()));
    }

    // test can get UICC by ID
    @Test
    @Disabled("VSTS-311")
    @Description("Test can get newly created UICC by Id")
    public void testCanGetUiccById() {
        // given
        String id = contentApiService.getNewlyCreatedUICCid();

        // when
        ValidatableResponse validatableResponse = contentApiService.getUiccById(id);

        // then
        validatableResponse.assertThat()
                .statusCode(200)
                .body("id", response -> is(id));
    }

    @Test
    @Description("Test can delete newly created UICC by ID")
    public void testCanDeleteUiccById() {
        // given
        String id = contentApiService.getNewlyCreatedUICCid();

        // when
        ValidatableResponse validatableResponse = contentApiService.deleteUiccById(id);

        // then
        validatableResponse.assertThat()
                .statusCode(200);
    }



}
