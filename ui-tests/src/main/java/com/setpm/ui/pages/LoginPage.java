package com.setpm.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    public SelenideElement emailInputField =
            $("div.v-input:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > input:nth-child(2)");
    public SelenideElement passwordInputField =
            $("div.v-input:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > input:nth-child(2)");
    public SelenideElement loginButton =
            $(".login-page__btn");
    public SelenideElement loginErrorMessage =
            $("#app > div > main > div > div > form > div.login-page__form");

    public static LoginPage open() {
        return Selenide.open("/login", LoginPage.class);
    }

    @Step("Login with username: {0}, password: {1}")
    public void loginUser(String UserName, String Password) {
        emailInputField.setValue(UserName);
        passwordInputField.setValue(Password);
        loginButton.click();
    }

}
