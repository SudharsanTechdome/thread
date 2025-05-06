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
        log.info("Session started with fetching the empty data");
        Assert.assertEquals(leadsPage.emptyName(),testData.get("empty_validation_message"));
        Assert.assertEquals(leadsPage.emptyNumber(),testData.get("empty_validation_message"));
        Assert.assertEquals(leadsPage.emptyId(),testData.get("empty_validation_message"));
        log.info("Session gets over by fetching the validation message");
}

    /**
     To add the lead with invalid data
     Expected : User should get the validation message
    */
@Test(dataProvider = "LeadsData", priority = 3, description = "to Add lead with partial data")
public void TC_03_verify_leads_added_with_partial_data (@org.jetbrains.annotations.NotNull Map< String, String > testData){
        leadsPage.partialData(testData.get("partial_name"), testData.get("partial_number"),testData.get("partial_id"));
        log.info("Session starts by adding the invalid data");
        Assert.assertEquals(leadsPage.partialName(),"Please enter a valid name (at least 3 characters, letters and spaces only)");
        Assert.assertEquals(leadsPage.partialNumber(), "Please enter a valid 10-digit phone number");
        Assert.assertEquals(leadsPage.partialId(),"Please enter a valid email address");
        log.info("Session gets completed by fetching the validation message");
    }

      /**
      To add the lead with existing number
      Expected : The lead should not be added and should get a notification
      */
    @Test(dataProvider = "LeadsData", priority = 4, description = "to Add lead with existing number")
    public void TC_04_verify_leads_added_with_existing_number (@org.jetbrains.annotations.NotNull Map< String, String > testData) {
        leadsPage.existingNumber(testData.get("full_name"), testData.get("contact_number"), testData.get("email_id"));
         Assert.assertEquals(leadsPage.notification(),apkConstant.existingNumber);
        log.info("Session gets completed by getting the toast message");
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
        log.info("Session gets starts by fetching the loan amount up to 25l");
        Assert.assertEquals(leadsPage.successToast(), apkConstant.filtersApplied);
        Assert.assertTrue(leadsPage.loanUpTo25L());
        log.info("Session gets completed by fetching the loan amount up to 25L");
    }
    /**
      To click and verify the loan amount filter upto 2CR
      Expected : The loan amount above 2CR should be displayed
      */

    @Test (priority = 8, description = "to apply the loan filter up to 2CR")
    public void TC_08_to_verify_the_loan_filter_up_to_2cr(){
        leadsPage.loanAmountUpTo2CR();
        log.info("Session starts by fetching the loan amount up to 2CR");
        Assert.assertEquals(leadsPage.successToast(), apkConstant.filtersApplied);
        Assert.assertTrue(leadsPage.loanUpTo2CR());
        log.info("Session gets completed by fetching the loan up to 2 CR");
    }

    /**
      To click and verify the loan amount filter up to 10CR
      Expected : The loan amount above 10CR should be displayed
      */

    @Test (priority = 9, description = "to apply the loan filter")
    public void TC_09_to_verify_the_loan_filter_up_to_10cr(){
        leadsPage.loanAmountUpTo10CR();
        log.info("Session starts by fetching the loan up to 10 Cr");
        Assert.assertEquals(leadsPage.successToast(), apkConstant.filtersApplied);
        Assert.assertTrue(leadsPage.loanUpTo10CR());
        log.info("Session gets completed by fetching the loan up to 10 CR");
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
        log.info("Session gets completed by fetching lona above 10 CR");
    }

     /**
      To click and verify the employment type filter
      Expected : The employment type filter should be displayed
      */

    @Test (priority = 11 , description = "to apply the employment type filter")
    public void TC_11_to_verify_the_employment_type_filter_options(){
        leadsPage.employmentFilter();
        log.info("Session starts by fetching the employment");
        Assert.assertEquals(leadsPage.successToast(), apkConstant.filtersApplied);
        Assert.assertTrue(leadsPage.employmentValues());
        log.info("Session gets completed by fetching all the employment type");
    }

     /**
      To click and verify the CIBIL score from 300 to 500
      Expected : The CIBIL score from 300 to 500 should be displayed
      */
     @Test (priority = 12 , description = "to apply the CIBIL filter from 300 to 500")
       public void TC_12_to_verify_the_CIBIL_filter_options_from_300_to_500(){
         leadsPage.CIBILUpto500();
         log.info("Session starts by fetching the CIBIL score up to 500");
         Assert.assertEquals(leadsPage.successToast(), apkConstant.filtersApplied);
         Assert.assertTrue(leadsPage.validateCIBILUpTo500());
         log.info("Session gets completed by fetching the CIBIL score up to 500");
       }
        /**
            To click and verify the CIBIL score from 501 to 700
           Expected : The CIBIL score from 501 to 700 should be displayed
      */

        @Test (priority = 13 , description = "to apply the CIBIL from 501 to 700")
        public void TC_13_to_verify_the_CIBIL_filter_options_from_501_to_700(){
            leadsPage.CIBILUpTo700();
            log.info("Session stars by fetching the CIBIL Score up to 700");
            Assert.assertEquals(leadsPage.successToast(), apkConstant.filtersApplied);
            Assert.assertTrue(leadsPage.validateCIBILUpTo700());
            log.info("Session gets completed by fetching the CIBIL score up to 700");
        }
        /**
          To click and verify the CIBIL score from 701 to 850
               Expected : The CIBIL score from 701 to 850 should be displayed
          */

        @Test (priority = 14 , description = "to apply the CIBIL from 701 to 850")
        public void TC_14_to_verify_the_CIBIL_filter_options_from_701_to_850(){
            leadsPage.CIBILUpTo850();
            log.info("Session starts by fetching the CIBIL score up to 850");
            Assert.assertEquals(leadsPage.successToast(), apkConstant.filtersApplied);
            Assert.assertTrue(leadsPage.validateCIBILUpTo850());
            log.info("Session gets completed by fetching the CIBIL score up to 850");
    }
    /**
     To click and verify the source filter
     Expected : The source filter should be displayed
     */
    @Test (priority = 15 , description = "to apply the source filter")
    public void TC_15_to_verify_the_source_filter_options(){
        leadsPage.sourceFilter();
        log.info("Session gets starts by fetching the source");
        Assert.assertEquals(leadsPage.successToast(), apkConstant.filtersApplied);
        Assert.assertTrue(leadsPage.sourceValues());
        log.info("Session gets completed by fetching the Source");
    }

    /**
     To click and verify the search functionalities
     Expected : The entered test data should be displayed
     */
    @Test(dataProvider = "LeadsData", priority = 16, description = "to verify the search bar functionalities")
    public void TC_16_to_verify_the_search_functionalities(@org.jetbrains.annotations.NotNull Map< String, String > testData){
        leadsPage.searchData(testData.get("search_text"));
        Assert.assertEquals(leadsPage.searchUser(),"91 8754174414");
        log.info("Session gets over by searching the search data");
    }

    @Test(dataProvider = "LeadsData", priority = 17, description = "to verify the action and edit profile functionalities with empty data")
    public void TC_17_to_verify_the_edit_profile_functionalities_with_empty_data(@org.jetbrains.annotations.NotNull Map< String, String > testData){
        leadsPage.toClearEditProfile();
        Assert.assertEquals(leadsPage.userText(),"Sudharsan V");
        Assert.assertEquals(leadsPage.validationMessageName(),testData.get("empty_validation_message"));
        Assert.assertEquals(leadsPage.validationMessageId(),testData.get("empty_validation_message"));
        log.info("Session gets over by fetching the validation message");
    }


    @Test(dataProvider = "LeadsData", priority = 18, description = "to verify the action and edit profile functionalities by invalid data")
    public void TC_18_to_verify_the_edit_profile_functionalities_by_invalid_data(@org.jetbrains.annotations.NotNull Map< String, String > testData) {
        leadsPage.invalidUser(testData.get("ep_invalid_name"), testData.get("ep_invalid_id"));
        Assert.assertEquals(leadsPage.invalidName(), apkConstant.invalidName);
        Assert.assertEquals(leadsPage.invalidId(),apkConstant.invalidId);
        log.info("Session gets over by fetching the validation message");
    }

    @Test(dataProvider = "LeadsData", priority = 19, description = "to verify the action and edit profile functionalities")
    public void TC_19_to_verify_the_edit_profile_functionalities(@org.jetbrains.annotations.NotNull Map< String, String > testData) {
        leadsPage.toEditProfile(testData.get("ep_user_name"), testData.get("ep_user_id"));
        log.info("Session starts by editing the user data");
        Assert.assertEquals(leadsPage.userText(), "Sudharsan VV");
        log.info("Session gets over by editing the user data");

    }


    @Test(dataProvider = "LeadsData", priority = 19, description = "to verify the action and edit profile functionalities")
    public void TC_20_to_verify_the_edit_profile_functionalities(@org.jetbrains.annotations.NotNull Map< String, String > testData) {
        leadsPage.toReeditProfile(testData.get("ep_user_test"), testData.get("ep_user_id"));
        //Assert.assertEquals(leadsPage.userText(), "Sudharsan V");
        log.info("Session gets over by editing the user data");

    }

    @Test(dataProvider = "LeadsData", priority = 20, description = "to verify the notes functionalities by adding notes")
    public void TC_21_to_verify_the_notes_functionalities_by_adding_data(@org.jetbrains.annotations.NotNull Map< String, String > testData) {
        leadsPage.notesFunction(testData.get("notesData"));
        log.info("Session stars  by adding the notes data");
        Assert.assertEquals(leadsPage.txtContent(),"test");
        log.info("Session gets over by adding the notes data");
    }

    @Test(dataProvider = "LeadsData", priority = 21, description = "to verify the notes functionalities by cancelling the added notes")
    public void TC_22_to_verify_the_notes_functionalities_by_cancelling_the_added_data(@org.jetbrains.annotations.NotNull Map< String, String > testData) {
        leadsPage.cancelAddedNotes(testData.get("notesData"));
        Assert.assertEquals(leadsPage.txtContent(),"test");
        log.info("Session gets over by canceling the added notes data");

    }

    @Test(priority = 22, description = "to verify without uploading the document")
    public void TC_23_to_verify_without_uploading_the_document(){
        leadsPage.emptyDocumentUpload();
        Assert.assertEquals(leadsPage.docValidation(),apkConstant.emptyDocument);
        log.info("Session gets over by fetching the validation message");

    }


    /**
     To upload the given document
     Expected : User can the given document is uploaded
     */
    @Test(dataProvider = "LeadsData", priority = 24,description = "to upload the document")
    public void TC_24_to_upload_the_document(@org.jetbrains.annotations.NotNull Map< String, String > testData) {
        log.info("Session starts by uploading the document");
        leadsPage.uploadDocument(testData.get("document"), properties.getProperty("pdf"));
        Assert.assertEquals(leadsPage.uploadedDocument(), apkConstant.documentUploaded);
        log.info("Session gets completed by uploading the document");
    }

    /**
     To rename the uploaded the document
     Expected : User can see the document is renamed
     */
    @Test(dataProvider = "LeadsData", priority = 25,description = "to rename the document")
    public void TC_25_to_rename_the_document(@org.jetbrains.annotations.NotNull Map< String, String > testData) {
        log.info("Session start by renaming the uploaded document");
        leadsPage.toRename(testData.get("rename"));
        Assert.assertEquals(leadsPage.rephrasedName(),"Sudharsan");
        log.info("Session gets completed by renaming the document");
    }
    /**
     To cancel the selected document from delete
     Expected : User can see the document is not deleted
     */

    @Test( priority = 26,description = "to cancel the deleted document")
    public void TC_26_to_cancel_the_deleted_document() {
        leadsPage.cancelDeleteOption();
        Assert.assertEquals(leadsPage.rephrasedName(),"Sudharsan");
        log.info("Session gets completed by by without deleting the document");
    }

    /**
     To delete the document
     Expected: User can see the selected document is deleted
     */
    @Test( priority = 27,description = "to delete the document")
    public void TC_27_to_delete_the_document(){
        log.info("Session is getting started by deleting the document");
        leadsPage.toDelete();
        Assert.assertEquals(leadsPage.deleteToste(),apkConstant.documentDeleted);
        log.info("Session gets over by deleting the document");
    }

    /**
     To click and verify the functionalities of loan amount column
     Expected : The selected columns shouldn't be displayed
     */
    @Test(priority = 28, description = "to verify the functionalities of loan amount column")
    public void TC_28_to_verify_the_functionalities_of_loan_amount_columns(){
        leadsPage.columnLoanAmount();
        Assert.assertTrue(leadsPage.loanAmount());
        log.info("Session is completed by not fetching loan amount columns");
    }

    /**
     To click and verify the functionalities of lead source column
     Expected : The selected columns shouldn't be displayed
     */
    @Test(priority = 29, description = "to verify the functionalities of lead source column")
    public void TC_29_to_verify_the_functionalities_of_lead_source_columns(){
        leadsPage.columnLeadSource();
        Assert.assertTrue(leadsPage.leadSource());
        log.info("Session is completed by not fetching lead source columns");
    }

    /**
     To click and verify the functionalities of Employment type column
     Expected : The selected columns shouldn't be displayed
     */
    @Test(priority = 30, description = "to verify the functionalities of Employment type column")
    public void TC_30_to_verify_the_functionalities_of_Employment_type_columns(){
        leadsPage.columnEmploymentType();
        Assert.assertTrue(leadsPage.employmentType());
        log.info("Session is completed by not fetching employment type columns");
    }

    /**
     To click and verify the functionalities of loan type column
     Expected : The selected columns shouldn't be displayed
     */
    @Test(priority = 31, description = "to verify the functionalities of loan type column")
    public void TC_31_to_verify_the_functionalities_of_loan_type_columns(){
        leadsPage.columnLoanType();
        Assert.assertTrue(leadsPage.loanType());
        log.info("Session is completed by not fetching loan type columns");
    }
    /**
     To click and verify the functionalities of CIBIL score column
     Expected : The selected columns shouldn't be displayed
     */
    @Test(priority = 32, description = "to verify the functionalities of CIBIL score column")
    public void TC_32_to_verify_the_functionalities_of_CIBIL_score_columns(){
        leadsPage.columnCIBILScore();
        Assert.assertTrue(leadsPage.CIBILScore());
        log.info("Session is completed by not fetching CIBIL score columns");
    }
    /**
     To click and verify the functionalities of Added on column
     Expected : The selected columns shouldn't be displayed
     */
    @Test(priority = 33, description = "to verify the functionalities of Added on column")
    public void TC_33_to_verify_the_functionalities_of_Added_on_columns(){
        leadsPage.columnAddedOn();
        Assert.assertTrue(leadsPage.addedOn());
        log.info("Session is completed by not fetching added on columns");
    }

    /**
     To click and verify the functionalities of cancel column
     Expected : The selected columns shouldn't be displayed
     */
    @Test(priority = 34, description = "to verify the functionalities of column by clicking cancel button")
    public void TC_34_to_verify_the_functionalities_of_columns_by_selecting_cancel_options(){
        leadsPage.cancelColumnOptions();
        playwrightFactory.takeScreenshot();
    }














}
