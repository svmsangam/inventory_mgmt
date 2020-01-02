package com.inventory.web.util;

import org.apache.log4j.Logger;

import java.util.Arrays;

public class LoggerUtil {

    private static Logger LOGGER = Logger.getLogger(LoggerUtil.class);

    public static void logException(Class className, Exception exception) {
        LOGGER.debug("Exception class " + className);
        LOGGER.debug("Error class " + exception.getClass());
        LOGGER.debug("Error cause " + exception.getCause());
        LOGGER.debug("Error message " + exception.getMessage());
        LOGGER.debug("StatckTrace " + Arrays.toString(exception.getStackTrace()));
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
