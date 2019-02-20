package com.setpm.web.api.service;

import com.github.javafaker.Faker;
import com.setpm.web.api.model.campaignModel.Campaign;
import com.setpm.web.api.model.campaignModel.UICCs;
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
public class CampaignApiService {

    public RequestSpecification setup() {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .filters(new RequestLoggingFilter(),
                        new ResponseLoggingFilter(),
                        new AllureRestAssured());
    }

    // returns a list of all campaigns
    public ValidatableResponse getAllCampaigns() {
        return setup()
                .when()
                .get("content-service/campaigns")
                .then();
    }

    // POST a new campaign object
    public ValidatableResponse postNewCampaign(Campaign campaign) {
        return setup()
                .body(campaign)
                .when()
                .post("/content-service/campaigns/start")
                .then();
    }

    // create a new campaign object
    public Campaign createNewCampaignObject() {
        ArrayList<Integer> uiccIds = new ArrayList<>();
        uiccIds.add(1000000001);
        uiccIds.add(1000000002);

        Faker faker = new Faker();
        return new Campaign()
                .setName("Auto generated campaign " + faker.idNumber().valid())
                .setScript("CREATE_USER")
                .setUiccIds(uiccIds);
    }

    // get campaign ID from a response
    public String getCampaignId(ValidatableResponse validatableResponse) {
        return validatableResponse.extract().path("id");
    }

    // get campaign by ID
    public ValidatableResponse getCampaignById(String id) {
        return setup()
                .when()
                .get("/content-service/campaigns/" + id)
                .then();
    }

    // re-run campaign
    public ValidatableResponse rerunCampaign(String id) {
        ArrayList<Integer> uicc = new ArrayList<>();
        uicc.add(1000000001);
        uicc.add(1000000002);
        uicc.add(1000000003);

        UICCs uicCsList = new UICCs().setUiccIds(uicc);

        return setup()
                .body(uicCsList)
                .when()
                .post("/content-service/campaigns/" + id + "/re-run")
                .then();
    }

}
