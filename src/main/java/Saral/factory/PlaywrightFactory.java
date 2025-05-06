package Saral.factory;

import Saral.util.LoggerUtils;

import com.microsoft.playwright.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Properties;



public class PlaywrightFactory {
    private static final LoggerUtils logger = new LoggerUtils(PlaywrightFactory.class);

    // Thread local storage for playwright instances
    private  final ThreadLocal<PlaywrightFactory> FACTORY_THREAD_LOCAL = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<Playwright> playwright = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<Browser> browser = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<BrowserContext> browserContext = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<Page> page = ThreadLocal.withInitial(() -> null);
    private final ThreadLocal<Properties> props = ThreadLocal.withInitial(() -> null);

    /**
     * Gets the thread-local instance of PlaywrightFactory.
     *
     * @return the thread-local instance of PlaywrightFactory
     */
    public  PlaywrightFactory getInstance() {
        if (FACTORY_THREAD_LOCAL.get() == null) {
            synchronized (PlaywrightFactory.class) {
                if (FACTORY_THREAD_LOCAL.get() == null) {
                    FACTORY_THREAD_LOCAL.set(new PlaywrightFactory());
                }
            }
        }
        return FACTORY_THREAD_LOCAL.get();
    }

    /**
     * Initializes the browser with the provided properties.
     *
     * @param props the properties for browser initialization
     * @return the initialized Page object
     */
    public Page initBrowser(Properties props) {
        if (page.get() == null) {
            this.props.set(props);
            String browserName = props.getProperty("browser");
            logger.info("[Thread: %d] Initializing browser: %s".formatted(Thread.currentThread().getId(), browserName));

            playwright.set(Playwright.create());


            browser.set(switch (browserName.toLowerCase()) {
                case "chromium" -> playwright.get().chromium()
                        .launch(new BrowserType.LaunchOptions().setHeadless(true));
                case "firefox" -> playwright.get().firefox()
                        .launch(new BrowserType.LaunchOptions().setHeadless(true));
                case "safari" -> playwright.get().webkit()
                        .launch(new BrowserType.LaunchOptions().setHeadless(true));
                case "chrome" -> playwright.get().chromium()
                        .launch(new BrowserType.LaunchOptions()
                                .setChannel("chrome")
                                .setHeadless(true));
                default -> {
                    logger.error("[Thread: %d] Invalid browser name: %s"
                            .formatted(Thread.currentThread().getId(), browserName));
                    yield playwright.get().chromium()
                            .launch(new BrowserType.LaunchOptions().setHeadless(true));
                }
            });

            browserContext.set(createContextWithSavedState());
            page.set(browserContext.get().newPage());
            page.get().navigate(props.getProperty("url"));
        }
        return page.get();
    }

    /**
     * Closes the browser for current thread.
     */
    public void closeBrowser() {
        Browser currentBrowser = browser.get();
        Playwright currentPlaywright = playwright.get();

        if (currentBrowser != null) {
            logger.info("[Thread: %d] Closing the browser.".formatted(Thread.currentThread().getId()));
            currentBrowser.close();

            if (currentPlaywright != null) {
                currentPlaywright.close();
            }

            // Clean up thread local variables
            browser.remove();
            page.remove();
            browserContext.remove();
            playwright.remove();
        }
    }

    /**
     * Gets the initialized Page object for current thread.
     *
     * @return the Page object
     */
    public Page getPage() {
        return this.page.get();
    }

    /**
     * Initializes the properties from the configuration file.
     *
     * @return the initialized Properties object
     */
    public Properties initProperties() {
        Properties properties = new Properties();
        String configPath = System.getProperty("ENV_LOCATION", "src/main/resources/config/config.properties");

        try (var files = new FileInputStream(configPath)) {
            logger.info("[Thread: %d] Loading properties from file: %s"
                    .formatted(Thread.currentThread().getId(), configPath));
            properties.load(files);
            props.set(properties);
        } catch (IOException e) {
            logger.error("[Thread: %d] Failed to load properties: %s"
                    .formatted(Thread.currentThread().getId(), e.getMessage()), e);
        }
        return properties;
    }

    /**
     * Takes a screenshot of the current page.
     *
     * @return the screenshot as a Base64 encoded string
     */
    public String takeScreenshot() {
        Page currentPage = this.page.get();
        if (currentPage == null) {
            logger.error("[Thread: %d] Page is not initialized, cannot take screenshot."
                    .formatted(Thread.currentThread().getId()));
            return null;
        }
        byte[] screenshotBytes = currentPage.screenshot(new Page.ScreenshotOptions().setFullPage(true));
        return Base64.getEncoder().encodeToString(screenshotBytes);
    }

    /**
     * Saves the session state to a thread-specific file.
     */
    public void saveSession() {
        BrowserContext currentContext = browserContext.get();
        if (currentContext == null) {
            logger.error("[Thread: %d] Browser context is not initialized, cannot save session."
                    .formatted(Thread.currentThread().getId()));
            return;
        }

        try {
            long threadId = Thread.currentThread().getId();
            Path sessionPath = Path.of("session_%d.json".formatted(threadId));
            currentContext.storageState(new BrowserContext.StorageStateOptions().setPath(sessionPath));
            logger.info("[Thread: %d] Session saved successfully."
                    .formatted(Thread.currentThread().getId()));
        } catch (Exception e) {
            logger.error("[Thread: %d] Failed to save session: %s"
                    .formatted(Thread.currentThread().getId(), e.getMessage()), e);
        }
    }

    private BrowserContext createContextWithSavedState() {
        long threadId = Thread.currentThread().getId();
        String sessionFileName = "session_%d.json".formatted(threadId);
        File sessionFile = new File(sessionFileName);
        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions();

        if (sessionFile.exists()) {
            contextOptions.setStorageStatePath(sessionFile.toPath());
            logger.info("[Thread: %d] Session state loaded from %s."
                    .formatted(Thread.currentThread().getId(), sessionFileName));
        } else {
            logger.info("[Thread: %d] No session state found, creating new context."
                    .formatted(Thread.currentThread().getId()));
        }

        return browser.get().newContext(contextOptions);
    }

    /**
     * Cleans up all thread local resources
     */
    public  void cleanAllThreadLocals() {
        logger.info("Cleaning up all thread local resources");
        FACTORY_THREAD_LOCAL.remove();
    }
}

