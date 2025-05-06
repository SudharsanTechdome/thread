package com.techdome.base;

import Saral.factory.PlaywrightFactory;
import com.microsoft.playwright.Page;
import Saral.util.LoggerUtils;
import com.techdome.saralCRM.Pages.LeadsPage;
import com.techdome.saralCRM.Pages.LoginPage;
import com.techdome.saralCRM.util.AppConstant;
import org.testng.annotations.*;

import java.io.File;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class BaseTest {
    private static final LoggerUtils logger = new LoggerUtils(BaseTest.class);
    private static final ConcurrentHashMap<Long, String> threadSessionFiles = new ConcurrentHashMap<>();

    protected PlaywrightFactory playwrightFactory;
    protected Page browserPage;
    protected Properties properties;
    protected LoginPage loginPage;
    protected LeadsPage leadsPage;
    protected AppConstant apkConstant;
    /**
     * Sets up the test environment before each test method.
     * Changed from @BeforeClass to @BeforeMethod for parallel execution.
     */
    @BeforeClass
    public void setup() {
        long threadId = Thread.currentThread().getId();
        logger.info("[Thread: %d] Setting up the test environment.".formatted(threadId));

        try {
            // Initialize Playwright Factory and properties
           // laywrightFactory =
            playwrightFactory=new PlaywrightFactory();
            playwrightFactory.getInstance();
            properties = playwrightFactory.initProperties();

            if (properties == null) {
                throw new RuntimeException("Properties file is not loaded. Cannot proceed with test setup.");
            }

            logger.info("[Thread: %d] Properties initialized successfully".formatted(threadId));

            // Initialize browser and page objects
            browserPage = playwrightFactory.initBrowser(properties);

            if (browserPage == null) {
                throw new RuntimeException("Failed to initialize the browser.");
            }

            // Record the session file for this thread
            String sessionFileName = "session_%d.json".formatted(threadId);
            threadSessionFiles.put(threadId, sessionFileName);

            // Initialize page objects
            loginPage = new LoginPage(browserPage);
            leadsPage=new LeadsPage(browserPage);
            apkConstant = new AppConstant();

            logger.info("[Thread: %d] Test setup completed successfully".formatted(threadId));

        } catch (Exception e) {
            logger.error("[Thread: %d] Error during test setup: %s".formatted(threadId, e.getMessage()), e);
            throw new RuntimeException("Test setup failed: " + e.getMessage(), e);
        }
    }

    /**
     * Tears down the test environment after each test method.
     * Changed from @AfterClass to @AfterMethod for parallel execution.
     */
    @AfterClass
    public void tearDown() {
        long threadId = Thread.currentThread().getId();
        logger.info("[Thread: %d] Tearing down the test environment.".formatted(threadId));

        if (playwrightFactory != null) {
            playwrightFactory.closeBrowser();
        }
    }

    /**
     * Clean up all session files after the test suite is complete
     */
    @AfterSuite
    public void deleteSessionFiles() {
        logger.info("Cleaning up all session files");

        // Delete all session files created by different threads
        threadSessionFiles.forEach((threadId, fileName) -> {
            File sessionFile = new File(fileName);
            if (sessionFile.exists()) {
                boolean deleted = sessionFile.delete();
                if (deleted) {
                    logger.info("Session file %s deleted for thread %d".formatted(fileName, threadId));
                } else {
                    logger.error("Failed to delete session file %s for thread %d".formatted(fileName, threadId));
                }
            }
        });

        // Also check for the default session file
        File defaultSessionFile = new File("session.json");
        if (defaultSessionFile.exists()) {
            boolean deleted = defaultSessionFile.delete();
            if (deleted) {
                logger.info("Default session.json file deleted after the test suite.");
            } else {
                logger.error("Failed to delete default session.json file.");
            }
        }

        // Clean up the thread local storage
        playwrightFactory.cleanAllThreadLocals();
    }

    /**
     * Helper method to check if a page object is instantiated
     */
    protected void ensurePageObjectsAreInitialized() {
        if (browserPage == null) {
            throw new IllegalStateException("Browser page is not initialized");
        }

        if (loginPage == null) {
            loginPage = new LoginPage(browserPage);
        }
        if (leadsPage == null) {
            leadsPage = new LeadsPage(browserPage);
        }

        if (apkConstant == null) {
            apkConstant = new AppConstant();
        }
    }

    /**
     * Helper method to navigate to base URL
     */
    protected void navigateToBaseUrl() {
        if (browserPage != null && properties != null) {
            String baseUrl = properties.getProperty("url");
            browserPage.navigate(baseUrl);
            logger.info("[Thread: %d] Navigated to base URL: %s".formatted(
                    Thread.currentThread().getId(), baseUrl));
        }
    }

    /**
     * Reset browser state between tests if needed
     */
    protected void resetBrowserState() {
        if (browserPage != null) {
            browserPage.evaluate("localStorage.clear();");
            browserPage.evaluate("sessionStorage.clear();");
            browserPage.navigate("about:blank");
            logger.info("[Thread: %d] Browser state reset".formatted(Thread.currentThread().getId()));
        }
    }
}