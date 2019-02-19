package com.setpm.ui.pages;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {

    // Side-menu locators:
    public SelenideElement userName = $(".side-menu__account > span:nth-child(2)");
    public SelenideElement dashboard = $(Selectors.byText("Dashboard"));
    public SelenideElement scripts = $(Selectors.byText("Scripts"));
    public SelenideElement applets = $(Selectors.byText("Applets"));
    public SelenideElement uiccGroups = $(Selectors.byText("UICCgroup’s groups"));
    public SelenideElement relationProfiles = $(Selectors.byText("Relation profiles"));
    public SelenideElement campaigns = $(Selectors.byText("Campaigns"));
    public SelenideElement logSearch = $(Selectors.byText("Log search"));
    public SelenideElement operations = $(Selectors.byText("Operations"));
    public SelenideElement activateKCSS = $(Selectors.byText("Activate KCSS"));
    public SelenideElement userManagement = $(Selectors.byText("User management"));
    public SelenideElement permissionsSettings = $(Selectors.byText("Permissions settings"));
    public SelenideElement logOut = $(Selectors.byText("Log out"));

    @Step("Open dashboard page")
    public static DashboardPage open() {
        return Selenide.open("/", DashboardPage.class);
    }


}
