package com.techdome.saralCRM.util;



import Saral.util.LoggerUtils;
import com.microsoft.playwright.Page;

import com.microsoft.playwright.Selectors;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.techdome.saralCRM.Pages.LeadsPage;
import com.techdome.base.BaseTest;
import com.techdome.saralCRM.Pages.LeadsPage;
//import org.apache.xmlbeans.impl.xb.xsdschema.SelectorDocument;

import java.nio.channels.Selector;

public class WrapperClass {
    private static  Page page;
    //private final LoggerUtils logger = new LoggerUtils(LeadsPage.class);
    public  WrapperClass(Page page) {

        this.page = page;
    }
    public static void click(String selectors){
        page.click(selectors);
    }

    public static void fill(String selectors ,String value){
        page.fill(selectors,value);
    }

    public static void waitForSelector(String selectors, Integer timeout){
        page.waitForSelector(selectors, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(timeout));
    }

    public static String textContent(String selector){
        return page.textContent(selector);
    }







































}
