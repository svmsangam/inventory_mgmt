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

}
