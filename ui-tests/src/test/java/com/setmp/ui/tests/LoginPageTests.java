package com.setmp.ui.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.setpm.ui.pages.DashboardPage;
import com.setpm.ui.pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Disabled;
import org.testng.annotations.Test;

@Feature("Dashboard page tests")
public class LoginPageTests extends BaseTest {

    @Test
    @Disabled
    @Description("User can login with valid credentials")
    public void testCanLoginUserWithValidCredentials() {
        // given
        LoginPage.open().loginUser("test4", "test4");

        // when
        at(DashboardPage.class).userName.shouldBe(Condition.visible);

        // then
        at(DashboardPage.class).userName.shouldHave(Condition.exactText("test4"));
    }

    @Test
    @Disabled
    @Description("User can't login with invalid credentials")
    public void testCantLoginUserWithInvalidCredentials() {
        // given
        Selenide.close();
        LoginPage.open();

        // when
        at(LoginPage.class).loginUser("invalid@name.com", "password");

        //then
        at(LoginPage.class).loginErrorMessage.shouldBe(Condition.visible);
    }

}