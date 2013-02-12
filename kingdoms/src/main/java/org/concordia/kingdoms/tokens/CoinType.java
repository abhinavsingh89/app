package org.concordia.kingdoms.tokens;

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
