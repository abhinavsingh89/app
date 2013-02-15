package org.concordia.kingdoms.tokens;

/**
 * Declared enum for colors which has a fixed value of R,Y,B,G.
 * 
 * @author
 * @since 1.0
 * 
 */
public enum Color {

	RED("R"), YELLOW("Y"), BLUE("B"), GREEN("G");

	private String code;

	/**
	 * constructor for color
	 * 
	 * @param code
	 *            - color code
	 */
	private Color(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

}
