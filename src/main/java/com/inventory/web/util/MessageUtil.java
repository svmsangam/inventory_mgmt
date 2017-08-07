package com.inventory.web.util;

public class MessageUtil {
	
	public static String getProductAddErrorMessage(String productName,String exception){
		return "ProductInfo "+productName +" could not be added because of "+exception;
	}

	public static String getAssingedToAddErrorMessage(String username,String exception){
		return "User "+username +" could not be added because of "+exception;
	}
}
