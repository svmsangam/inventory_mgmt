package com.inventory.core.model.enumconstant;
/**
 * @author Uttam 
 *
 * 
 */
public enum UserType {

	SYSTEM("System"),SUPERADMIN("Super Admin"), ADMIN("Admin"), USER("User"), GUEST("Guest");

	private final String value;

	UserType(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	public String getValue() {
		return value;
	}

	public static UserType getEnum(String value) {
		if (value == null)
			throw new IllegalArgumentException();
		for (UserType v : values())
			if (value.equalsIgnoreCase(v.getValue()))
				return v;
		throw new IllegalArgumentException();
	}
}