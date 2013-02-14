package org.concordia.kingdoms.tokens;
/**
 * Declared Enum for all types of coins which has fixed value
 * @author PVRA
 * @version 1.0-SNAPSHOT
 *
 */
public enum CoinType {

	COPPER_1(1), COPPER_5(5), SILVER_10(10), GOLD_50(50), GOLD_100(100);

	private int value;
	/**
	 * constructor for CoinType
	 * @param value
	 */
	private CoinType(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}
}
