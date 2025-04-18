package com.techdome.saralCRM.Pages;


import Saral.util.LoggerUtils;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import Saral.util.LoggerUtils;
import com.techdome.saralCRM.Pages.Locator_Constant;

//import static com.techdome.saralCRM.util.WrapperClass.*;
import static com.techdome.saralCRM.Pages.Locator_Constant.*;

public class LoginPage {
    private final Page page;
    private final LoggerUtils logger = new LoggerUtils(LoginPage.class);

    public LoginPage(Page page) {

        this.page = page;
    }

    public void login(String email, String password) {
        page.fill(EMAIL_FIELD, email);
        page.fill(PASSWORD_FIElD, password);
        page.waitForSelector(LOGIN_BUTTON, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(2000));
        page.click(LOGIN_BUTTON);
        logger.info("login");
        page.waitForTimeout(30000);
    }

    public String userEmail() {

        page.waitForSelector(TO_VALIDATE_EMAIL, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));

        return page.textContent(TO_VALIDATE_EMAIL);
    }


    public void forgotPassword(String emailaddress) {
        page.click(FORGOT_PASSWORD);
        page.waitForSelector(EMAIL_ADDRESS, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(2000));
        page.fill(EMAIL_ADDRESS, emailaddress);
        page.waitForSelector(BTN_RESETPASSWORD, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(2000));
        page.click(BTN_RESETPASSWORD);
        page.waitForSelector(BTN_BACK_TO_LOGIN, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(2000));
        page.click(BTN_BACK_TO_LOGIN);
        page.waitForTimeout(5000);
    }

    public String resetPassword() {
        return page.textContent(RESET_PASSWORD_MSG);
    }

    public void invalidEmailLogin(String email, String password) {
        page.locator(EMAIL_FIELD).clear();
        page.fill(EMAIL_FIELD, email);
        page.fill(PASSWORD_FIElD, password);
        page.waitForSelector(LOGIN_BUTTON, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(2000));
        page.click(LOGIN_BUTTON);
        page.waitForTimeout(5000);
    }

    public String invalidEmail() {
        // page.waitForSelector(INVALID_EMAIL, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));

        return page.textContent(INVALID_EMAIL);
    }

    public void invalidPasswordLogin(String email, String password) {
        page.locator(EMAIL_FIELD).clear();
        page.locator(PASSWORD_FIElD).clear();
        page.fill(EMAIL_FIELD, email);
        page.fill(PASSWORD_FIElD, password);
        page.waitForSelector(LOGIN_BUTTON, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(2000));
        page.click(LOGIN_BUTTON);
        page.waitForTimeout(5000);
    }

    public String invalidPassword() {
        return page.textContent(INVALID_PASSWORD);
    }

    public void notRegisteredMail(String email, String password) {
        page.locator(EMAIL_FIELD).clear();
        page.fill(EMAIL_FIELD, email);
        page.fill(PASSWORD_FIElD, password);
        page.waitForSelector(LOGIN_BUTTON, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(2000));
        page.click(LOGIN_BUTTON);
        page.waitForTimeout(3000);
    }

    public String notRegisteredEmail() {
        return page.textContent(NOT_REGISTERED_EMAIL);
    }

    public String notRegisteredEmailInForgotPassword() {
        return page.textContent(NOT_REGISTERED_EMAIL_FORGOT_PASSWORD);
    }


    public void emptyLogin() {
        page.waitForSelector(LOGIN_BUTTON, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(2000));
        page.click(LOGIN_BUTTON);
        page.waitForTimeout(5000);
    }

    public String emptyEmail() {

        return page.textContent(EMPTY_EMAIL);
    }

    public String emptyPassword() {

        return page.textContent(EMPTY_PASSWORD);
    }

    public void backToLogin(String emailaddress) {
        page.locator(EMAIL_ADDRESS).clear();
        page.fill(EMAIL_ADDRESS, emailaddress);
        page.waitForSelector(BTN_RESETPASSWORD, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(2000));
        page.click(BTN_RESETPASSWORD);
        page.waitForTimeout(2000);
        page.click(BTN_BACK_TO_LOGIN);
        page.waitForTimeout(2000);
    }


    public void loginWithoutEmail(String email) {

        page.fill(EMAIL_FIELD, email);
        page.waitForSelector(LOGIN_BUTTON, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(2000));
        page.click(LOGIN_BUTTON);
        page.waitForTimeout(2000);
    }

    public String withoutPassword() {
        return page.textContent(WITHOUT_PASSWORD);
    }

    public void loginWithoutPassword(String password) {
        page.locator(EMAIL_FIELD).clear();
        page.fill(PASSWORD_FIElD, password);
        page.waitForSelector(LOGIN_BUTTON, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(2000));
        page.click(LOGIN_BUTTON);
        page.waitForTimeout(2000);
    }


    public void invalidForgotPasswords(String emailaddress) {
        page.click(FORGOT_PASSWORD);
        page.waitForSelector(EMAIL_ADDRESS, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(2000));
        page.fill(EMAIL_ADDRESS, emailaddress);
        page.waitForSelector(BTN_RESETPASSWORD, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(2000));
        page.click(BTN_RESETPASSWORD);
    }

    public void notRegisteredForgotPasswords(String emailaddress) {
        page.locator(EMAIL_ADDRESS).clear();
        page.fill(EMAIL_ADDRESS, emailaddress);
        page.waitForSelector(BTN_RESETPASSWORD, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(2000));
        page.click(BTN_RESETPASSWORD);

    }


}







