package com.inventory.web.util;

public class UIUtil {

    public static String addStoreMessage(){
        StringBuilder builder = new StringBuilder();

        builder.append("Please add store first &nbsp;");
        builder.append("<a href='#' type='button' class='addStore' data-toggle='modal' data-target='#modal-add' > click here </a>");

        return builder.toString();
    }
}
