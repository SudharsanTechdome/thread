package com.techdome.saralCRM.test;


import Saral.util.JsonUtils;
import Saral.util.LoggerUtils;
import com.techdome.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;



public class LeadsTest extends BaseTest {

    private static final LoggerUtils logger = new LoggerUtils(LeadsTest.class);
    private static final LoggerUtils log = new LoggerUtils(LeadsTest.class);
    @DataProvider(name = "LeadsData")
    public Object[][] loginData() {
        logger.info("Fetching test data for Leads page");
        return new Object[][]{
                {JsonUtils.getTestData("LeadsData")}
        };
    }
    /**
     To login with the valid data in the login page
     Expected : User should be able to login and should get the assert verification with the registered mail ID
     */

    @Test(priority = 1, description = "to verify the login of Saral CRM")
    public void TC_01_verify_login_page (){
        loginPage.login(properties.getProperty("email"),properties.getProperty("password"));
        Assert.assertEquals(loginPage.userEmail(), "sudharsanworklife@gmail.com");
    }

    /**
     To add the lead without any data
     Expected : User should get the validation message
     */
@Test(dataProvider = "LeadsData",priority = 2,dependsOnMethods = {"TC_01_verify_login_page"}, description = "to click the Add Lead button without any data")
public void TC_02_to_verify_add_lead_without_any_data (@org.jetbrains.annotations.NotNull Map< String, String > testData){
        leadsPage.addLead();
        Assert.assertEquals(leadsPage.emptyName(),testData.get("empty_validation_message"));
        Assert.assertEquals(leadsPage.emptyNumber(),testData.get("empty_validation_message"));
        Assert.assertEquals(leadsPage.emptyId(),testData.get("empty_validation_message"));
}

    /*
     To add the lead with invalid data
     Expected : User should get the validation message
    */
@Test(dataProvider = "LeadsData", priority = 3, description = "to Add lead with partial data")
public void TC_03_verify_leads_added_with_partial_data (@org.jetbrains.annotations.NotNull Map< String, String > testData){
        leadsPage.partialData(testData.get("partial_name"), testData.get("partial_number"),testData.get("partial_id"));
        Assert.assertEquals(leadsPage.partialName(),"Please enter a valid name (at least 3 characters, letters and spaces only)");
        Assert.assertEquals(leadsPage.partialNumber(), "Please enter a valid 10-digit phone number");
        Assert.assertEquals(leadsPage.partialId(),"Please enter a valid email address");
    }

      /*
      To add the lead with existing number
      Expected : The lead should not be added and should get a notification
      */
    @Test(dataProvider = "LeadsData", priority = 4, description = "to Add lead with existing number")
    public void TC_04_verify_leads_added_with_existing_number (@org.jetbrains.annotations.NotNull Map< String, String > testData) {
        leadsPage.existingNumber(testData.get("full_name"), testData.get("contact_number"), testData.get("email_id"));
         Assert.assertEquals(leadsPage.notification(),apkConstant.existingNumber);
    }

     /**
     To add the lead with valid data
     Expected : The lead should be added
      */
//    @Test(dataProvider = "LeadsData", priority = 5, description = "to Add lead with valid data")
//    public void TC_05_verify_leads_added_with_valid_data (@org.jetbrains.annotations.NotNull Map< String, String > testData) {
//        leadsPage.validData(testData.get("full_name"), testData.get("valid_number"), testData.get("email_id"));
//        Assert.assertEquals(leadsPage.userNumber(),"+91 9856341459");
//    }
//
//     /**
//      To verify whether the lead is added when cancel button is clicked
//      Expected : The lead shouldn't get added
//      */
//    @Test(dataProvider = "LeadsData", priority = 6, description = "to Add lead with existing number")
//    public void TC_06_verify_leads_added_when_cancel_button_is_clicked (@org.jetbrains.annotations.NotNull Map< String, String > testData) {
//        leadsPage.cancelButton(testData.get("full_name"), testData.get("valid_number"), testData.get("email_id"));
//        Assert.assertEquals(leadsPage.userNumber(),"+91 " +testData.get("valid_number"));
//    }

     /**
      To click and verify the loan amount filter up to 25l
      Expected : The loan amount above 25l should be displayed
      */

    @Test (priority = 7, description = "to apply the loan filter up to 25l")
    public void TC_07_to_verify_the_loan_filter_up_to_25l(){
        leadsPage.loanAmountUpTo25L();
        Assert.assertEquals(leadsPage.successToast(), apkConstant.filtersApplied);
        Assert.assertTrue(leadsPage.loanUpTo25L());
        //Assert.assertEquals(leadsPage.applyFilterNotification(),"Filters Applied Successfully!");
    }
    /**
      To click and verify the loan amount filter upto 2CR
      Expected : The loan amount above 2CR should be displayed
      */

    @Test (priority = 8, description = "to apply the loan filter up to 2CR")
    public void TC_08_to_verify_the_loan_filter_up_to_2cr(){
        leadsPage.loanAmountUpTo2CR();
        Assert.assertEquals(leadsPage.successToast(), apkConstant.filtersApplied);
        Assert.assertTrue(leadsPage.loanUpTo2CR());
    }

    /**
      To click and verify the loan amount filter up to 10CR
      Expected : The loan amount above 10CR should be displayed
      */

    @Test (priority = 9, description = "to apply the loan filter")
    public void TC_09_to_verify_the_loan_filter_up_to_10cr(){
        leadsPage.loanAmountUpTo10CR();
        Assert.assertEquals(leadsPage.successToast(), apkConstant.filtersApplied);
        Assert.assertTrue(leadsPage.loanUpTo10CR());
    }

    /**
      To click and verify the loan amount filter above 10CR
      Expected : The loan amount above 10CR should be displayed
      */

    @Test (priority = 10, description = "to apply the loan filter")
    public void TC_10_to_verify_the_loan_filter_above_10cr(){
        leadsPage.loanAmountAbove10CR();
        Assert.assertEquals(leadsPage.successToast(), apkConstant.filtersApplied);
        Assert.assertTrue(leadsPage.loanAbove10CR());
    }

     /**
      To click and verify the employment type filter
      Expected : The employment type filter should be displayed
      */

    @Test (priority = 11 , description = "to apply the employment type filter")
    public void TC_11_to_verify_the_employment_type_filter_options(){
        leadsPage.employmentFilter();
        Assert.assertEquals(leadsPage.successToast(), apkConstant.filtersApplied);
        Assert.assertTrue(leadsPage.employmentValues());
    }

     /**
      To click and verify the CIBIL score from 300 to 500
      Expected : The CIBIL score from 300 to 500 should be displayed
      */
     @Test (priority = 12 , description = "to apply the CIBIL filter from 300 to 500")
       public void TC_12_to_verify_the_CIBIL_filter_options_from_300_to_500(){
            leadsPage.CIBILUpto500();
         Assert.assertEquals(leadsPage.successToast(), apkConstant.filtersApplied);
            Assert.assertTrue(leadsPage.validateCIBILUpTo500());
       }
        /**
            To click and verify the CIBIL score from 501 to 700
           Expected : The CIBIL score from 501 to 700 should be displayed
      */

        @Test (priority = 13 , description = "to apply the CIBIL from 501 to 700")
        public void TC_13_to_verify_the_CIBIL_filter_options_from_501_to_700(){
            leadsPage.CIBILUpTo700();
            Assert.assertEquals(leadsPage.successToast(), apkConstant.filtersApplied);
            Assert.assertTrue(leadsPage.validateCIBILUpTo700());
        }
        /**
          To click and verify the CIBIL score from 701 to 850
               Expected : The CIBIL score from 701 to 850 should be displayed
          */

        @Test (priority = 14 , description = "to apply the CIBIL from 701 to 850")
        public void TC_14_to_verify_the_CIBIL_filter_options_from_701_to_850(){
            leadsPage.CIBILUpTo850();
            Assert.assertEquals(leadsPage.successToast(), apkConstant.filtersApplied);
            Assert.assertTrue(leadsPage.validateCIBILUpTo850());
    }
    /**
     To click and verify the source filter
     Expected : The source filter should be displayed
     */
    @Test (priority = 15 , description = "to apply the source filter")
    public void TC_15_to_verify_the_source_filter_options(){
        leadsPage.sourceFilter();
        Assert.assertEquals(leadsPage.successToast(), apkConstant.filtersApplied);
        Assert.assertTrue(leadsPage.sourceValues());
    }

    /**
     To click and verify the search functionalities
     Expected : The entered test data should be displayed
     */
    @Test(dataProvider = "LeadsData", priority = 16, description = "to verify the search bar functionalities")
    public void TC_16_to_verify_the_search_functionalities(@org.jetbrains.annotations.NotNull Map< String, String > testData){
        leadsPage.searchData(testData.get("search_text"));
        Assert.assertEquals(leadsPage.searchUser(),"91 8754174414");
    }

    @Test(dataProvider = "LeadsData", priority = 17, description = "to verify the action and edit profile functionalities with empty data")
    public void TC_17_to_verify_the_edit_profile_functionalities_with_empty_data(@org.jetbrains.annotations.NotNull Map< String, String > testData){
        leadsPage.toClearEditProfile();
        Assert.assertEquals(leadsPage.userText(),"Sudharsan V");
        Assert.assertEquals(leadsPage.validationMessageName(),testData.get("empty_validation_message"));
        Assert.assertEquals(leadsPage.validationMessageId(),testData.get("empty_validation_message"));
    }


    @Test(dataProvider = "LeadsData", priority = 18, description = "to verify the action and edit profile functionalities by invalid data")
    public void TC_18_to_verify_the_edit_profile_functionalities_by_invalid_data(@org.jetbrains.annotations.NotNull Map< String, String > testData) {
        leadsPage.invalidUser(testData.get("ep_invalid_name"), testData.get("ep_invalid_id"));
        Assert.assertEquals(leadsPage.invalidName(), apkConstant.invalidName);
        Assert.assertEquals(leadsPage.invalidId(),apkConstant.invalidId);
    }

    @Test(dataProvider = "LeadsData", priority = 19, description = "to verify the action and edit profile functionalities")
    public void TC_19_to_verify_the_edit_profile_functionalities(@org.jetbrains.annotations.NotNull Map< String, String > testData) {
        leadsPage.toEditProfile(testData.get("ep_user_name"), testData.get("ep_user_id"));
        Assert.assertEquals(leadsPage.userText(), "Sudharsan VV");
    }


    @Test(dataProvider = "LeadsData", priority = 19, description = "to verify the action and edit profile functionalities")
    public void TC_20_to_verify_the_edit_profile_functionalities(@org.jetbrains.annotations.NotNull Map< String, String > testData) {
        leadsPage.toReeditProfile(testData.get("ep_user_test"), testData.get("ep_user_id"));
        //Assert.assertEquals(leadsPage.userText(), "Sudharsan V");
    }

    @Test(dataProvider = "LeadsData", priority = 20, description = "to verify the notes functionalities by adding notes")
    public void TC_21_to_verify_the_notes_functionalities_by_adding_data(@org.jetbrains.annotations.NotNull Map< String, String > testData) {
        leadsPage.notesFunction(testData.get("notesData"));
        Assert.assertEquals(leadsPage.txtContent(),"test");
    }

    @Test(dataProvider = "LeadsData", priority = 21, description = "to verify the notes functionalities by cancelling the added notes")
    public void TC_22_to_verify_the_notes_functionalities_by_cancelling_the_added_data(@org.jetbrains.annotations.NotNull Map< String, String > testData) {
        leadsPage.cancelAddedNotes(testData.get("notesData"));
        Assert.assertEquals(leadsPage.txtContent(),"test");
    }

    @Test(priority = 22, description = "to verify without uploading the document")
    public void TC_23_to_verify_without_uploading_the_document(){
        leadsPage.emptyDocumentUpload();
        Assert.assertEquals(leadsPage.docValidation(),apkConstant.emptyDocument);

    }

















}
