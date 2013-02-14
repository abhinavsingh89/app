package org.concordia.kingdoms.tokens;
/**
 * Declared Enum for colors which has a fixed value of R,Y,B,G.
 * @author PVRA
 * @version 1.0-SNAPSHOT
 *
 */
public enum Color {

	RED("R"), YELLOW("Y"), BLUE("B"), GREEN("G");

	private String code;
	/**
	 * constructor for color
	 * @param code - The player choose the color
	 */
	private Color(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
