package com.inventory.core.api.iapi;

/**
 * Created by dhiraj on 1/18/18.
 */
public interface ISendMailSSL {

    void sendMail(String from , String to , String msg , String sub);

    void sendHtmlMail(String from, String to, String msg, String sub);
}
