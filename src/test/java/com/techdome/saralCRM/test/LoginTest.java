package com.techdome.saralCRM.test;


import Saral.util.LoggerUtils;
import Saral.util.JsonUtils;
import Saral.util.LoggerUtils;
import com.techdome.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;



public class LoginTest extends BaseTest {
    private static final LoggerUtils logger = new LoggerUtils(LoginTest.class);

    @DataProvider(name = "LoginData")
    public Object[][] loginData() {
        logger.info("Fetching test data for Login page");
        return new Object[][]{
                {JsonUtils.getTestData("LoginData")}
        };
    }


    @Test(priority = 1, description = "to check the login without any data")
    public void TC_01_verify_the_login_without_any_credientials() {
        loginPage.emptyLogin();
        Assert.assertEquals(loginPage.emptyEmail(), "This field cannot be left empty.");
        Assert.assertEquals(loginPage.emptyPassword(), "This field cannot be left empty.");
    }

    @Test(dataProvider = "LoginData", priority = 2, description = "to verify the login without password to SaralCRM")
    public void TC_02_verify_login_without_password(@org.jetbrains.annotations.NotNull Map<String, String> testData) {
        loginPage.loginWithoutEmail(testData.get("email_field"));
        Assert.assertEquals(loginPage.withoutPassword(), "This field cannot be left empty.");
    }

    @Test(dataProvider = "LoginData", priority = 3, description = "to verify the login without email to SaralCRM")
    public void TC_03_verify_login_without_email(@org.jetbrains.annotations.NotNull Map<String, String> testData) {
        loginPage.loginWithoutPassword(testData.get("password_field"));
        Assert.assertEquals(loginPage.withoutPassword(), "This field cannot be left empty.");
    }

    @Test(dataProvider = "LoginData", priority = 4, description = "to verify the invalid email and correct password to login to SaralCRM")
    public void TC_04_verify_invalid_email(@org.jetbrains.annotations.NotNull Map<String, String> testData) {
        loginPage.invalidEmailLogin(testData.get("email_feed"), testData.get("password_feed"));
        Assert.assertEquals(loginPage.invalidEmail(), "Please enter a valid email address.");
    }

//    @Test(dataProvider = "LoginData", priority = 5, description = "to verify the valid email and invalid password login to SaralCRM")
//    public void TC_05_verify_invalid_password(@org.jetbrains.annotations.NotNull Map<String, String> testData) {
//        loginPage.invalidPasswordLogin(testData.get("email_field"), testData.get("password_text"));
//        Assert.assertEquals(loginPage.invalidPassword(), "Wrong password. 2 attempts left or Click 'Forgot Password' to reset.");
//    }


    @Test(dataProvider = "LoginData", priority = 6, description = "to verify the not registered email to login to SaralCRM")
    public void TC_06_verify_not_registered_email(@org.jetbrains.annotations.NotNull Map<String, String> testData) {
        loginPage.notRegisteredMail(testData.get("email"), testData.get("password_feed"));
        Assert.assertEquals(loginPage.notRegisteredEmail(), "This email address is not registered with Saral. Please check and try again.");
    }

    @Test(dataProvider = "LoginData", priority = 7, description = "to verify the login of SaralCRM by forget password")
    public void TC_07_verify_login_page_forgot_password(@org.jetbrains.annotations.NotNull Map<String, String> testData) {
        loginPage.forgotPassword(testData.get("email_field"));


    }

    @Test(dataProvider = "LoginData", priority = 8, description = "to verify the invalid email in forgot password to SaralCRM")
    public void TC_08_verify_invalid_email_in_forgot_password(@org.jetbrains.annotations.NotNull Map<String, String> testData) {
        loginPage.invalidForgotPasswords(testData.get("email_feed"));
        Assert.assertEquals(loginPage.invalidEmail(), "Please enter a valid email address.");
    }


    @Test(dataProvider = "LoginData", priority = 9, description = "to verify the not registered email in forgot password to SaralCRM")
    public void TC_09_verify_not_registered_email_in_forgot_password(@org.jetbrains.annotations.NotNull Map<String, String> testData) {
        loginPage.notRegisteredForgotPasswords(testData.get("email"));
        Assert.assertEquals(loginPage.notRegisteredEmailInForgotPassword(), "This email address is not registered with Saral. Please check and try again.");
    }

    @Test(dataProvider = "LoginData", priority = 10, description = "to verify the back to login in forgot password to SaralCRM")
    public void TC_10_verify_back_to_login_in_forgot_password(@org.jetbrains.annotations.NotNull Map<String, String> testData) {
        loginPage.backToLogin(testData.get("email_field"));

    }

    @Test(priority = 11, description = "to verify the login to SaralCRM")
    public void TC_11_verify_login_page() {
        loginPage.login(properties.getProperty("email"), properties.getProperty("password"));
        Assert.assertEquals(loginPage.userEmail(), "sudharsanworklife@gmail.com");
    }



}