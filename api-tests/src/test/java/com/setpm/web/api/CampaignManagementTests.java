package com.setpm.web.api;

import com.setpm.web.api.service.CampaignApiService;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.core.IsNot.not;

public class CampaignManagementTests {

    CampaignApiService campaignApiService = new CampaignApiService();

    @BeforeAll
    static void SetUp() {
        RestAssured.baseURI = "http://dev.se-tpm.zone";
    }

    @Test
    @Description("Test can get list of all campaigns")
    public void  testCanGetListOfAllCampaigns() {
        // given
        ValidatableResponse validatableResponse = campaignApiService.getAllCampaigns();

        // assert
        validatableResponse.assertThat()
                .statusCode(200)
                .body("id[0]", response -> not(isEmptyString()));
    }

    @Test
    @Description("Test can create a new campaign")
    public void testCanCreateCampaign() {
        // given
        ValidatableResponse validatableResponse = campaignApiService
                .postNewCampaign(campaignApiService.createNewCampaignObject());

        // when
        String id = campaignApiService.getCampaignId(validatableResponse);

        // then
        validatableResponse.assertThat()
                .statusCode(200)
                .body("id", response -> is(id));
    }

    @Test
    @Description("Test can get campaign by id")
    public void testCanGetCampaignById() {
        // given
        ValidatableResponse validatableResponse = campaignApiService
                .postNewCampaign(campaignApiService.createNewCampaignObject());

        // when
        String id = campaignApiService.getCampaignId(validatableResponse);

        // and
        ValidatableResponse campaignResponse = campaignApiService.getCampaignById(id);

        // then
        campaignResponse.assertThat()
                .statusCode(200)
                .body("id", response -> is(id));

    }

    @Test
    @Description("Test can re-run campaign")
    public void testCanRerunCampaign() {
        // given
        ValidatableResponse validatableResponse = campaignApiService
                .postNewCampaign(campaignApiService.createNewCampaignObject());

        // when
        String id = campaignApiService.getCampaignId(validatableResponse);

        // and
        ValidatableResponse rerunnedResponse = campaignApiService.rerunCampaign(id);

        // then
        rerunnedResponse.assertThat()
                .statusCode(200)
                .body("id", response -> is(id));

    }



}
