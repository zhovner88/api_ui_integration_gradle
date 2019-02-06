package com.setmp.ui.tests;

import com.codeborne.selenide.Condition;
import com.setpm.ui.pages.DashboardPage;
import com.setpm.ui.pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

@Feature("Login page Tests")
public class DashboardPageTests extends BaseTest {

    @Test
    @Description("User can logout from account")
    public void loggedUserCanLogOut() {
        // given
        LoginPage.open().loginUser("test4", "test4");

        // when
        at(DashboardPage.class).logOut.click();

        // then
        at(LoginPage.class).emailInputField.shouldBe(Condition.visible);
    }


}
