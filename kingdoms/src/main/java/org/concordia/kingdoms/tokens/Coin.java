package org.concordia.kingdoms.tokens;

import java.util.List;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.google.common.collect.Lists;

@AutoProperty
public class Coin {

	private CoinType type;

	private int value;

	private Coin(CoinType type, int value) {
		this.type = type;
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public CoinType getType() {
		return this.type;
	}

	@Override
	public boolean equals(Object o) {
		return Pojomatic.equals(this, o);
	}

	@Override
	public int hashCode() {
		return Pojomatic.hashCode(this);
	}

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

	public static List<Coin> newCoins(CoinType type, int size) {
		final List<Coin> coins = Lists.newArrayList();
		for (int i = 0; i < size; i++) {
			coins.add(Coin.newCoin(type));
		}
		return coins;
	}

	public static Coin newCoin(CoinType type) {
		return new Coin(type, type.getValue());
	}

}
