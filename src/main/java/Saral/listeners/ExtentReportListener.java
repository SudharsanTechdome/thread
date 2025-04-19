package Saral.listeners;

import Saral.factory.PlaywrightFactory;
import Saral.util.LoggerUtils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class ExtentReportListener implements ITestListener {
    private static final LoggerUtils logger = new LoggerUtils(ExtentReportListener.class);
    private static final String OUTPUT_FOLDER = "./build/reports/";
    private static final String FILE_NAME = "TestExecutionReport.html";

    // Thread-safe map to store test instances for each thread
    private static final ConcurrentHashMap<Long, ExtentTest> testMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Long, PlaywrightFactory> factoryMap = new ConcurrentHashMap<>();

    // Initialize extent reports only once for all threads
    private static final ExtentReports extent = initReports();

    /**
     * Initialize ExtentReports
     */
    private static ExtentReports initReports() {
        try {
            // Create directories if they don't exist
            Path path = Paths.get(OUTPUT_FOLDER);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                logger.info("Directory created at: " + path.toAbsolutePath());
            }

            // Get timestamp for unique report naming
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            String reportFileName = "TestReport_" + timeStamp + ".html";

            // Initialize ExtentReports
            ExtentReports extentReports = new ExtentReports();
            ExtentSparkReporter reporter = new ExtentSparkReporter(OUTPUT_FOLDER + reportFileName);
            reporter.config().setReportName("Alphametricx Automation Test Results");
            reporter.config().setDocumentTitle("Test Automation Report");

            extentReports.attachReporter(reporter);
            extentReports.setSystemInfo("System", System.getProperty("os.name"));
            extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
            extentReports.setSystemInfo("Author", "Ayush");
            extentReports.setSystemInfo("Build#", "1.1");
            extentReports.setSystemInfo("Team", "Team QA");
            extentReports.setSystemInfo("Customer Name", "Alphametricx");

            return extentReports;
        } catch (IOException e) {
            logger.error("Failed to initialize ExtentReports", e);
            throw new RuntimeException("Failed to initialize ExtentReports", e);
        }
    }

    @Override
    public void onStart(ITestContext context) {
        logger.info("Test Suite started: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("Test Suite finished: " + context.getName());

        // Flush extent reports only once at the end
        extent.flush();

        // Clean up resources for all threads
        testMap.clear();

        // Close browsers for all threads and clean up
        factoryMap.forEach((threadId, factory) -> {
            try {
                factory.closeBrowser();
            } catch (Exception e) {
                logger.error("Error closing browser for thread " + threadId, e);
            }
        });
        factoryMap.clear();
    }

    @Override
    public void onTestStart(ITestResult result) {
        long threadId = Thread.currentThread().getId();
        logger.info("[Thread: " + threadId + "] Test started: " + result.getMethod().getMethodName());

        String methodName = result.getMethod().getMethodName();
        String qualifiedName = result.getMethod().getQualifiedName();
        int last = qualifiedName.lastIndexOf(".");
        int mid = qualifiedName.substring(0, last).lastIndexOf(".");
        String className = qualifiedName.substring(mid + 1, last);

        // Create test for this thread
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),
                result.getMethod().getDescription());

        extentTest.assignCategory(result.getTestContext().getSuite().getName());
        extentTest.assignCategory(className);
        extentTest.assignDevice("Thread-" + threadId);

        // Store in thread map
        testMap.put(threadId, extentTest);

        // Initialize PlaywrightFactory for this thread if needed
        if (!factoryMap.containsKey(threadId)) {
            PlaywrightFactory factory = PlaywrightFactory.getInstance();
            Properties props = factory.initProperties();
            //factory.initBrowser(props);
            factoryMap.put(threadId, factory);
        }

        extentTest.getModel().setStartTime(getTime(result.getStartMillis()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        long threadId = Thread.currentThread().getId();
        logger.info("[Thread: " + threadId + "] Test passed: " + result.getMethod().getMethodName());

        ExtentTest test = testMap.get(threadId);
        if (test != null) {
            test.pass("Test passed");

            PlaywrightFactory factory = factoryMap.get(threadId);
            if (factory != null) {
                String screenshot = factory.takeScreenshot();
                if (screenshot != null) {
                    test.pass(MediaEntityBuilder.createScreenCaptureFromBase64String(
                            screenshot, result.getMethod().getMethodName()).build());
                }
            }

            test.getModel().setEndTime(getTime(result.getEndMillis()));
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        long threadId = Thread.currentThread().getId();
        logger.error("[Thread: " + threadId + "] Test failed: " + result.getMethod().getMethodName());

        ExtentTest test = testMap.get(threadId);
        if (test != null) {
            test.fail(result.getThrowable());

            PlaywrightFactory factory = factoryMap.get(threadId);
            if (factory != null) {
                String screenshot = factory.takeScreenshot();
                if (screenshot != null) {
                    test.fail(MediaEntityBuilder.createScreenCaptureFromBase64String(
                            screenshot, "Failure Screenshot").build());
                }
            }

            test.getModel().setEndTime(getTime(result.getEndMillis()));
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        long threadId = Thread.currentThread().getId();
        logger.info("[Thread: " + threadId + "] Test skipped: " + result.getMethod().getMethodName());

        ExtentTest test = testMap.get(threadId);
        if (test != null) {
            test.skip(result.getThrowable());

            PlaywrightFactory factory = factoryMap.get(threadId);
            if (factory != null) {
                String screenshot = factory.takeScreenshot();
                if (screenshot != null) {
                    test.skip(MediaEntityBuilder.createScreenCaptureFromBase64String(
                            screenshot, "Skipped Test Screenshot").build());
                }
            }

            test.getModel().setEndTime(getTime(result.getEndMillis()));
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // This method is rarely used but implemented for completeness
        long threadId = Thread.currentThread().getId();
        logger.info("[Thread: " + threadId + "] Test failed but within success percentage: "
                + result.getMethod().getMethodName());
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    /**
     * Helper method to get current ExtentTest instance for the calling thread
     */
    public static ExtentTest getTest() {
        return testMap.get(Thread.currentThread().getId());
    }

    /**
     * Helper method to log info to the report from test classes
     */
    public static void logInfo(String message) {
        ExtentTest test = getTest();
        if (test != null) {
            test.info(message);
        }
    }

    /**
     * Helper method to add screenshots to the report from test classes
     */
    public static void addScreenshot(String base64Image, String title) {
        ExtentTest test = getTest();
        if (test != null && base64Image != null) {
            test.info(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image, title).build());
        }
    }
}
