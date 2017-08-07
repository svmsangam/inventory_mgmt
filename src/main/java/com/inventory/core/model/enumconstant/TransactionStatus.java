package com.inventory.core.model.enumconstant;

public enum TransactionStatus {

	CREATED("Created"), COMPLETED("Completed"), HOLD("Hold"), CANCELLED_WITH_REFUND(
			"Cancelled With Refund"), CANCELLED_WITHOUT_REFUND("Cancelled Without Refund"), APPROVED(
					"Approved"), PAID("Paid"), INITIATED("Initiated"), LOCK("Lock");

	private final String value;

	TransactionStatus(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	public String getValue() {
		return value;
	}

	public static TransactionStatus getEnum(String value) {
		if (value == null)
			throw new IllegalArgumentException();
		for (TransactionStatus v : values())
			if (value.equalsIgnoreCase(v.getValue()))
				return v;
		throw new IllegalArgumentException();
	}
}
