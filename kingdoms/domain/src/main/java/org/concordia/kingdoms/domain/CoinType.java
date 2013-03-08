package org.concordia.kingdoms.domain;

/**
 * Declared enum for all types of coins which has fixed value
 * 
 * @author Team K
 * @since 1.0
 * @see Coin
 */
public enum CoinType {

	COPPER_1(1), COPPER_5(5), SILVER_10(10), GOLD_50(50), GOLD_100(100);

	private int value;

	private CoinType(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}
}
