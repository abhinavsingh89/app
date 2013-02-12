package org.concordia.kingdoms.tokens;

public enum Color {

	RED("R"), YELLOW("Y"), BLUE("B"), GREEN("G");

	private String code;

	private Color(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
