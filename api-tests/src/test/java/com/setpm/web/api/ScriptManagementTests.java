package com.setpm.web.api;

import com.setpm.web.api.service.ScriptApiService;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class ScriptManagementTests {

    @BeforeAll
    static void SetUp() {
        RestAssured.baseURI = "http://api.dev.se-tpm.zone";
    }

    ScriptApiService scriptApiService = new ScriptApiService();

    @Test
    @Description("Test can get a list of all scripts")
    public void testCanGetListOfScripts() {
        // given
        ValidatableResponse validatableResponse = scriptApiService.getScriptsList();

        // expect
        validatableResponse.assertThat()
                .statusCode(200)
                .body("id", response -> everyItem(not(isEmptyString())));
    }

    @Test
    @Description("Test can POST a new script")
    public void testCanPostNewScript() {
        // given
        ValidatableResponse validatableResponse = scriptApiService
                .postNewScript(scriptApiService.createNewScript());

        // when
        String id = scriptApiService.getScriptId(validatableResponse);

        // expect
        validatableResponse.assertThat()
                .statusCode(200)
                .body("id", response -> is(id));
    }

    @Test
    @Description("Test can get newly created script by id")
    public void testCanGetScriptById() {
        // given
        ValidatableResponse validatableResponse = scriptApiService
                .postNewScript(scriptApiService.createNewScript());

        // when
        String id = scriptApiService.getScriptId(validatableResponse);

        // and
        ValidatableResponse validatableResponse1GetScript = scriptApiService.getScriptById(id);

        // then
        validatableResponse1GetScript.assertThat()
                .statusCode(200)
                .body("id", response -> is(id));
    }

    @Test
    @Description("Test can update script")
    public void testCanUpdateScript() {
        // given
        ValidatableResponse validatableResponse = scriptApiService
                .postNewScript(scriptApiService.createNewScript());

        // when
        String id = scriptApiService.getScriptId(validatableResponse);

        // and
        ValidatableResponse validatableResponse1GetUpdatedScript = scriptApiService.updateScriptById(id);

        // then
        validatableResponse1GetUpdatedScript.assertThat()
                .statusCode(200)
                .body("id", response -> is(id));
    }

    @Test
    @Description("Test can delete script by ID")
    public void testCanDeleteScript() {
        // given
        ValidatableResponse validatableResponse = scriptApiService
                .postNewScript(scriptApiService.createNewScript());

        // when
        String id = scriptApiService.getScriptId(validatableResponse);

        // and
        ValidatableResponse validatableResponseDeletedScript = scriptApiService.delteScriptById(id);

        // then
        validatableResponseDeletedScript.assertThat()
                .statusCode(200);
    }

}
