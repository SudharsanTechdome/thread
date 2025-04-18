package Saral.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logger utility class to provide logging functionality.
 */
public class LoggerUtils {
    private final Logger logger;

    /**
     * Constructor to initialize the logger with the provided class.
     *
     * @param clazz the class for which the logger is created
     */
    public LoggerUtils(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    /**
     * Logs a debug message.
     *
     * @param message the message to log
     */
    public void debug(String message) {
        logger.debug(message);
    }

    /**
     * Logs an info message.
     *
     * @param message the message to log
     */
    public void info(String message) {
        logger.info(message);
    }

    /**
     * Logs an error message.
     *
     * @param message the message to log
     */
    public void error(String message) {
        logger.error(message);
    }

    /**
     * Logs an error message with an exception.
     *
     * @param message   the message to log
     * @param throwable the exception to log
     */
    public void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }
}