package com.bilgedam.mvc.shopfinity.enums;

public enum QuantityType {

	LITRE("quantitytype.litre"), ADET("quantitytype.adet"), KG("quantitytype.kg"), TON("quantitytype.ton"),
	DUZINE("quantitytype.duzine"), DESTE("quantitytype.deste");

	private final String type;

	private QuantityType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
