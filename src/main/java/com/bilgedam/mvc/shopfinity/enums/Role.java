package com.bilgedam.mvc.shopfinity.enums;

public enum Role {
	ROLE_ADMIN("Yönetici"), ROLE_USER("Kullanıcı");

	private final String displayName;

	Role(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
