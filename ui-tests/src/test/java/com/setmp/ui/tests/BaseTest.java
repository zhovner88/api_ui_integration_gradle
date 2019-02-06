package com.setmp.ui.tests;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    @BeforeSuite
    public void setUp() {
        Configuration.headless = true;
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "http://dev.se-tpm.zone";
    }

    @Step
    protected <T> T at(Class<T> tClass) {
        try {
            return tClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}