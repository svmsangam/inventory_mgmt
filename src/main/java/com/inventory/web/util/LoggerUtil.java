package com.inventory.web.util;

import com.inventory.core.model.entity.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

public class LoggerUtil {

    private static Logger LOGGER = Logger.getLogger(LoggerUtil.class);

    public static void logException(Class className, Exception exception) {

        changeMailSubject();

        User currentUser = AuthenticationUtil.getCurrentUserInfo();

        String username = "N/A";

        if (currentUser != null){
            username = currentUser.getUsername();
        }

        String error = "Exception class " + className + " \n username : " + username;
        error += "\n Error class " + exception.getClass();
        error += "\nError cause " + exception.getCause();
        error += "\nError message " + exception.getMessage();
        error += "\nStatckTrace " + Arrays.toString(exception.getStackTrace());

        LOGGER.error(error);
    }

    public static void logMessage(Class className, String message) {
        LOGGER.debug("Message class " + className);
        LOGGER.debug("Message " + message);
    }

    public static void logInfo(String msg){
        LOGGER.debug(msg);
    }

    public static void logDebug(String msg){
        LOGGER.debug(msg);
    }

    public static void changeMailSubject(){
        Properties props = new Properties();
        try {
            InputStream configStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("/log4j.properties");
            if (configStream == null) {
                throw new RuntimeException();
            }
            props.load(configStream);
            configStream.close();
        } catch(Throwable e) {
            System.out.println("Error: Cannot load log4j configuration file ");
        }

        props.setProperty("log4j.appender.mailAppender.Subject", "Exception in Inventory Application on " + new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));

        LogManager.resetConfiguration();
        PropertyConfigurator.configure(props);
    }

    public static void main(String[] a){
        LOGGER.debug("Error new message ");
        logMessage(LoggerUtil.class , "main class");
        logException(LoggerUtil.class , new Exception("this is test"));
    }

}
