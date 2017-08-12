package com.inventory.core.model.enumconstant;


public enum ResponseStatus {

	SUCCESS("SAN000", "Success"),
	
	FAILURE("SAN001", "Failure"),

	INTERNAL_SERVER_ERROR("SAN002", "Internal Server Error. Please contact Administrator."),

	INVALID_SESSION("SAN003", "Session Invalid"),

	BAD_REQUEST("SAN004", "Bad Request"),
	
	UNAUTHORIZED_USER("SAN005","Un-Authorized User"),
	
	NO_PACKAGE_AVAILABLE("SAN006", "No Packages Available"),
	
	UNIDENTIFIED_PACKAGE( "SAN007","No Package Available with given details"),
	
	BLOCKED_USER("SAN007", "User is Curently Blocked"),
	
	INACTIVE_USER( "SAN008", "User is Inactive"),
	
	DELETED_USER("SAN009", "User is Deleted"),
	
	REGISTRATION_FAILED("SAN010", "Registration Failed with Some Errors."),
	
	EMPTY_USERNAME("SAN011", "Empty User Name. Please provide your Username"),
	
	NO_USER_FOUND("SAN012", "Could Not Find User"),
	
	INVALID_TOKEN("SAN013", "Invalid Token"), 
	
	EMPTY_CONTACTS("SAN014", "No Any Contacts were found."),
	
	GROUP_NOT_FOUND("SAN015", "No Any Groups were found."),
	
	VALIDATION_FAILED("SAN016", "Validation Failed"),
	
	USERNAME_VALIDATION_FAILED("SAN018", "Kindly provide 10 digit numbers"),
	
	EMPTY("SAN019", "Empty"),
	
	RESOURCE_CODE_EXPIRED("SAN017", "Resource Code has expired"),
	
	DUBLICATE("SAN020", "Dublicate Entry");
	

	ResponseStatus() {

	}
	
	private String key;

	private String value;

	ResponseStatus(String key, String value) {
		this.key=key;
		this.value = value;
	}
	
	public String getKey(){
		return key;
	}
	public String getValue() {
		return value;
	}

	public static ResponseStatus getEnumByValue(String value) {
		if (value == null)
			throw new IllegalArgumentException();
		for (ResponseStatus v : values())
			if (value.equalsIgnoreCase(v.getValue()))
				return v;
		throw new IllegalArgumentException();
	}
	
	public static ResponseStatus getEnumByKey(String key) {
		if (key == null)
			throw new IllegalArgumentException();
		for (ResponseStatus v : values())
			if (key.equalsIgnoreCase(v.getKey()))
				return v;
		throw new IllegalArgumentException();
	}

}
