package com.inventory.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(LoggerUtil.class);

    public static void logException(Class className, Exception exception) {
        LOGGER.debug("Exception class " + className);
        LOGGER.debug("Error class " + exception.getClass());
        LOGGER.debug("Error cause " + exception.getCause());
        LOGGER.debug("Error message " + exception.getMessage());
    }

    public static void logMessage(Class className, String message) {
        LOGGER.debug("Message class " + className);
        LOGGER.debug("Message " + message);
    }

    public static void main(String[] a){
        LOGGER.debug("Error new message ");
        logMessage(LoggerUtil.class , "main class");
    }

}
