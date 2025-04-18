package com.techdome.saralCRM.Pages;


import Saral.util.LoggerUtils;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import Saral.util.LoggerUtils;

import java.nio.file.Paths;

import static com.techdome.saralCRM.util.WrapperClass.*;
import static com.techdome.saralCRM.Pages.Locator_Constant.*;

public class LeadsPage  {
    private final Page page;
    private final LoggerUtils logger = new LoggerUtils(LeadsPage.class);
    public LeadsPage(Page page) {

        this.page = page;
    }


    public void loanAmountUpTo25L() {
        click(BTN_FILTER);
        click(CKBOX_UPTO25L);
        click(BTN_APPLYFILTER);
        //waitForSelector(LOAN_AMOUNT_VALUE,40000);

    }
    public Boolean loanUpTo25L() {
        page.textContent(LOAN_AMOUNT_VALUE);

        Locator loanAmountCells = page.locator(LOAN_AMOUNT_VALUE);
        boolean flag=false;
        int count = loanAmountCells.count();
        for (int i = 0; i < count; i++) {
            String loanAmount = loanAmountCells.nth(i).innerText().trim();
            String amount = loanAmount.replaceAll("[₹,]", "").trim();
            // String amount = loanAmount.replaceAll("₹,","").trim();
            logger.info(amount);
            if (Long.parseLong(amount) <= 2500000) {
                flag=true;
            }
        }

        return flag;

    }
    public String successToast(){

        String nofitication = textContent(APPLY_FILTERS_NOTIFICATION);
        logger.info(nofitication);
        return nofitication;
    }

    public void loanAmountUpTo2CR() {
        click(BTN_FILTER);
        click(BTN_CLEAR_ALL);
        page.waitForTimeout(3000);
        waitForSelector(BTN_FILTER,10000);
        click(BTN_FILTER);
        click(CKBOX_UPTO2CR);
        click(BTN_APPLYFILTER);
        // waitForSelector(LOAN_AMOUNT_VALUE,40000);

    }


    public Boolean loanUpTo2CR() {
        waitForSelector(LOAN_AMOUNT_VALUE,40000);
        page.textContent(LOAN_AMOUNT_VALUE);
        Locator loanAmountCells = page.locator(LOAN_AMOUNT_VALUE);
        boolean flag=false;
        int count = loanAmountCells.count();
        for (int i = 0; i < count; i++) {
            String loanAmount = loanAmountCells.nth(i).innerText().trim();
            String amount = loanAmount.replaceAll("[₹,]", "").trim();
            // String amount = loanAmount.replaceAll("₹,","").trim();
            logger.info(amount);
            if (Long.parseLong(amount) > 2500000 ||Long.parseLong(amount) <=20000000  ) {
                flag=true;
            }
        }

        return flag;

    }


    public void loanAmountUpTo10CR() {
        click(BTN_FILTER);
        click(BTN_CLEAR_ALL);
        page.waitForTimeout(3000);
        waitForSelector(BTN_FILTER,10000);
        click(BTN_FILTER);
        click(CKBOX_UPTO10CR);
        click(BTN_APPLYFILTER);
        //   waitForSelector(LOAN_AMOUNT_VALUE,10000);
    }

    public Boolean loanUpTo10CR() {
        waitForSelector(LOAN_AMOUNT_VALUE,40000);
        page.textContent(LOAN_AMOUNT_VALUE);
        Locator loanAmountCells = page.locator(LOAN_AMOUNT_VALUE);
        boolean flag=false;
        int count = loanAmountCells.count();
        for (int i = 0; i < count; i++) {
            String loanAmount = loanAmountCells.nth(i).innerText().trim();
            String amount = loanAmount.replaceAll("[₹,]", "").trim();
            // String amount = loanAmount.replaceAll("₹,","").trim();
            logger.info(amount);
            if (Long.parseLong(amount) >= 20000000 ||Long.parseLong(amount) <=100000000  ) {
                flag=true;
            }
        }

        return flag;

    }


    public void loanAmountAbove10CR() {
        page.click(BTN_FILTER);
        click(BTN_FILTER);
        click(BTN_CLEAR_ALL);
        page.waitForTimeout(3000);
        // waitForSelector(BTN_FILTER,40000);
        click(BTN_FILTER);
        click(CKBOX_ABOVE10CR);
        click(BTN_APPLYFILTER);
        //waitForSelector(LOAN_AMOUNT_VALUE,40000);

    }


    public Boolean loanAbove10CR() {
        waitForSelector(LOAN_AMOUNT_VALUE,40000);
        textContent(LOAN_AMOUNT_VALUE);
        Locator loanAmountCells = page.locator(LOAN_AMOUNT_VALUE);
        boolean flag=false;
        int count = loanAmountCells.count();
        for (int i = 0; i < count; i++) {
            String loanAmount = loanAmountCells.nth(i).innerText().trim();
            String amount = loanAmount.replaceAll("[₹,]", "").trim();
            // String amount = loanAmount.replaceAll("₹,","").trim();
            logger.info(amount);
            if (Long.parseLong(amount) >= 100000000 ) {
                flag=true;
            }
        }

        return flag;

    }



    public void employmentFilter(){

        waitForSelector(BTN_FILTER,10000);
        click(BTN_FILTER);
        click(BTN_CLEAR_ALL);
        page.waitForTimeout(3000);
        click(BTN_FILTER);
        click(FILTER_EMPLOYMENT_TYPE);
        click(CK_SALARY);
        click(CK_BUSINESS);
        click(BTN_APPLYFILTER);
//    waitForSelector(EMPLOYMENT_TYPE_VALUE,10000);
    }

    public Boolean employmentValues(){
        waitForSelector(EMPLOYMENT_TYPE_VALUE,10000);
        textContent(EMPLOYMENT_TYPE_VALUE);
        Locator empTypeCells = page.locator(EMPLOYMENT_TYPE_VALUE);
        boolean flag=false;
        int count = empTypeCells.count();
        for (int i = 0; i < count; i++) {
            String empType = empTypeCells.nth(i).innerText().trim();
            logger.info(empType);
            if (empType.equals("Salaried")||empType.equals("Business")) {
                flag=true;
                break;
            }
        }

        return flag;

    }



    public void CIBILUpto500(){
        // page.waitForSelector(BTN_FILTER, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(30000));
        waitForSelector(BTN_FILTER,7000);
        click(BTN_FILTER);
        click(BTN_CLEAR_ALL);
        page.waitForTimeout(3000);
        click(BTN_FILTER);
        click(FILTER_CIBIL_SCORE);
        click(CK_UP_TO_500);
        click(BTN_APPLYFILTER);
        // waitForSelector(CIVIL_VALUE,7000);

    }

    public Boolean validateCIBILUpTo500() {
        waitForSelector(CIVIL_VALUE,10000);
        textContent(CIVIL_VALUE);
        Locator civilValueCells = page.locator(CIVIL_VALUE);
        boolean flag = false;
        int count = civilValueCells.count();

        for (int i = 0; i < count; i++) {
            String civil = civilValueCells.nth(i).innerText().trim();
            logger.info(civil);
            if (Integer.parseInt(civil)>300&&Integer.parseInt(civil)<=500 ) {
                flag = true;
            }
            else{
                flag=false;
            }
        }

        return flag;
    }


    public void CIBILUpTo700(){
        waitForSelector(BTN_FILTER,7000);
        click(BTN_FILTER);
        click(BTN_CLEAR_ALL);
        page.waitForTimeout(3000);
        click(BTN_FILTER);
        click(FILTER_CIBIL_SCORE);
        click(CK_UP_TO_700);
        click(BTN_APPLYFILTER);
//        waitForSelector(CIVIL_VALUE,7000);
    }

    public Boolean validateCIBILUpTo700() {
        waitForSelector(CIVIL_VALUE,7000);
        textContent(CIVIL_VALUE);
        Locator civilValueCells = page.locator(CIVIL_VALUE);
        boolean flag = false;
        int count = civilValueCells.count();

        for (int i = 0; i < count; i++) {
            String civil = civilValueCells.nth(i).innerText().trim();
            logger.info(civil);
            if (Integer.parseInt(civil)>500&&Integer.parseInt(civil)<700 ) {
                flag = true;
            }
            else{
                flag=false;
            }
        }

        return flag;
    }


    public void CIBILUpTo850(){
        //page.waitForSelector(BTN_FILTER, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(30000));
        waitForSelector(BTN_FILTER,7000);
        click(BTN_FILTER);
        click(BTN_CLEAR_ALL);
        page.waitForTimeout(3000);
        click(BTN_FILTER);
        click(FILTER_CIBIL_SCORE);
        click(CK_UP_TO_850);
        click(BTN_APPLYFILTER);
        // waitForSelector(CIVIL_VALUE,7000);
    }

    public Boolean validateCIBILUpTo850() {

        textContent(CIVIL_VALUE);
        Locator civilValueCells = page.locator(CIVIL_VALUE);
        boolean flag = false;
        int count = civilValueCells.count();

        for (int i = 0; i < count; i++) {
            String civil = civilValueCells.nth(i).innerText().trim();
            logger.info(civil);
            if (Integer.parseInt(civil)>701&&Integer.parseInt(civil)<850 ) {
                flag = true;
            }
            else{
                flag=false;
            }
        }

        return flag;
    }

    public void sourceFilter(){
        waitForSelector(BTN_FILTER,10000);
        click(BTN_FILTER);
        click(BTN_CLEAR_ALL);
        page.waitForTimeout(3000);
        click(BTN_FILTER);
        click(BTN_FILTER_SOURCE);
        click(CK_FACEBOOK);
        click(CK_WEBSITE);
        click(CK_INSTAGRAM);
        click(CK_WHATSAPP);
        click(CK_CRM);
        click(BTN_APPLYFILTER);
//        waitForSelector(SOURCE_VALUE,30000);

    }


    public Boolean sourceValues(){
        waitForSelector(SOURCE_VALUE,30000);
        textContent(SOURCE_VALUE);
        Locator sourceCells = page.locator(SOURCE_VALUE);
        boolean flag=false;
        int count = sourceCells.count();
        for (int i = 0; i < count; i++) {
            String source = sourceCells.nth(i).innerText().trim();
            logger.info(source);
            if (source.equals("Instagram")||source.equals("Facebook")) {
                flag=true;
                break;
            }
            if (source.equals("Whatsapp")|| source.equals("Website")|| source.equals("CRM")){
                flag=true;
                break;
            }
        }

        return flag;

    }


    public void addLead(){
        click(BTN_ADDLEAD);
        waitForSelector(BTN_LEAD,10000);
        click(BTN_LEAD);
    }

    public String emptyName(){

        return textContent(EMPTY_NAME);
    }

    public String emptyNumber(){

        return textContent(EMPTY_NUMBER);
    }

    public String emptyId(){

        return    textContent(EMT_ID);
    }

    public void partialData(String name, String number , String id){
        fill(TEXT_NAME,name);
        fill(TEXT_NUMBER,number);
        fill(TEXT_ID,id);
        click(BTN_LEAD);

    }

    public String partialName(){
        return   textContent(PARTIAL_NAME_MSG);
    }

    public String partialNumber(){

        return   textContent(PARTIAL_NUMBER_MSG);
    }
    public String partialId(){

        return   textContent(PARTIAL_ID_MSG);
    }

    public void existingNumber(String name, String number , String id){
        //click(BTN_ADDLEAD);
        fill(TEXT_NAME,name);
        fill(TEXT_NUMBER,number);
        fill(TEXT_ID,id);
        click(BTN_LEAD);
    }
    public void validData(String name, String number , String id){
        page.locator(TEXT_NAME).clear();
        fill(TEXT_NAME,name);
        page.locator(TEXT_NUMBER).clear();
        fill(TEXT_NUMBER,number);
        page.locator(TEXT_ID);
        fill(TEXT_ID,id);
        click(BTN_LEAD);
        page.reload();
        waitForSelector(USER_NUMBER,10000);

    }

    public String userNumber(){
        return textContent(USER_NUMBER);
    }

    public void cancelButton(String name, String number , String id){
        click(BTN_ADDLEAD);
        fill(TEXT_NAME,name);
        fill(TEXT_NUMBER,number);
        fill(TEXT_ID,id);
        click(BTN_CANCEL);
        waitForSelector(USER_NUMBER,40000);
    }
    public String notification(){

        return textContent(NOTIFICATION_TOGGLE);
    }

    public void searchData(String searchText ){
        //page.waitForSelector(BTN_FILTER, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(7000));
        waitForSelector(BTN_FILTER,7000);
        click(BTN_FILTER);
        click(BTN_CLEAR_ALL);
        click(SEARCH_BAR);
        fill(SEARCH_BAR,searchText);
        click(SEARCH_ICON);
        waitForSelector(SEARCH_USER,30000);
//        page.waitForSelector(SEARCH_USER, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(10000));

    }

    public String searchUser(){
        waitForSelector(SEARCH_USER,30000);
        return textContent(SEARCH_USER);
    }


    public void saveColumnOptions(){
        click(BTN_COLUMNS);
        click(LOAN_AMOUNT_TOGGLE);
        click(LEAD_SOURCE_TOGGLE);
        click(LOAN_TYPE_TOGGLE);
        click(EMPLOYMENT_TYPE_TOGGLE);
        click(CIBIL_SCORE_TOGGLE);
        click(ADDED_ON_TOGGLE);
        click(COLUMN_SAVE_BTN);


    }


    public void cancelColumnOptions(){
        click(BTN_COLUMNS);
        click(LOAN_AMOUNT_TOGGLE);
        click(LEAD_SOURCE_TOGGLE);
        click(LOAN_TYPE_TOGGLE);
        click(EMPLOYMENT_TYPE_TOGGLE);
        click(CIBIL_SCORE_TOGGLE);
        click(ADDED_ON_TOGGLE);
        click(COLUMN_CANCEL_BTN);

    }

    public void toClearEditProfile(){
        click(BTN_EYE_ICON);
        click(BTN_EDIT_PROFILE);
        page.locator(EP_USER_NAME).clear();
        page.locator(EP_USER_ID).clear();
    }

    public String userText(){
        return textContent(USER_NAME_TEXT);
    }

    public String validationMessageName(){
        return textContent(VALIDATION_MESSAGE);
    }


    public String validationMessageId(){
        return textContent(VALIDATION_MESSAGE);
    }

    public void toEditProfile(String name, String id){
        page.locator(EP_USER_NAME).clear();
        fill(EP_USER_NAME,name);
        page.locator(EP_USER_ID).clear();
        fill(EP_USER_ID, id);
        click(BTN_UPDATE_LEAD);
        page.waitForTimeout(8000);
        //waitForSelector(USER_NAME_TEXT,30000);
    }
    public void toReeditProfile(String name, String id){
        click(BTN_EDIT_PROFILE);
        fill(EP_USER_NAME,name);
        fill(EP_USER_ID,id);
        click(BTN_UPDATE_LEAD);
        page.waitForTimeout(10000);
        // waitForSelector(USER_NAME_TEXT,30000);
    }
    public void invalidUser(String name, String id){
        fill(EP_USER_NAME,name);
        fill(EP_USER_ID,id);
    }

    public String invalidName(){
        return textContent(EP_INVALID_NAME);
    }

    public String invalidId(){
        return textContent(EP_INVALID_ID);
    }

    public void notesFunction(String note){
        click(BTN_NOTES);
        waitForSelector(BTN_ADD_NOTES,7000);
        click(BTN_ADD_NOTES);
        click(TXTBOX_ADD_NOTE);
        fill(TXTBOX_ADD_NOTE,note);
        click(BTN_ADDNOTE);
        //page.waitForTimeout(8000);
        waitForSelector(TXT_CONTENT,20000);
    }

    public String txtContent(){
        return textContent(TXT_CONTENT);
    }

    public void cancelAddedNotes(String note){
        click(BTN_ADD_NOTES);
        click(TXTBOX_ADD_NOTE);
        fill(TXTBOX_ADD_NOTE,note);
        click(CANCEL_ADDED_NOTES);
        //page.waitForTimeout(8000);
        waitForSelector(TXT_CONTENT,20000);

    }

    public void emptyDocumentUpload(){
        click(BTN_DOCUMENT);
        waitForSelector(BTN_UPLOAD_DOCUMENT,7000);
        click(BTN_UPLOAD_DOCUMENT);
        click(BTN_IMPORT);
    }

    public String docValidation(){
        return textContent(EMPTY_DOC_VALIDATION_MSG);
    }


    public void uploadDocument(String Aadhar){
        waitForSelector(DOCUMENT_DROPDOWN,30000);
        click(DOCUMENT_DROPDOWN);
        page.locator((ALL_DROPDOWN_ELEMENTS), new Page.LocatorOptions().setHasText(Aadhar)).click();
        click(CHOOSE_FILE);
        page.setInputFiles("input[type='file']", Paths.get("src/SaralCRM_test/resources/test-data/PRAYAS_JAIN__CIBIL_Report.pdf"));
        click(BTN_IMPORT);
    }































}
