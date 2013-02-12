package org.concordia.kingdoms.board.factory;

import java.util.List;
import java.util.Map;

import org.concordia.kingdoms.tokens.Coin;
import org.concordia.kingdoms.tokens.CoinType;

import com.google.common.collect.Maps;

public class CoinBank {

	private static CoinBank INSTANCE;

	private Map<CoinType, List<Coin>> coins;

	private CoinBank() {
		this.coins = Maps.newHashMap();
	}

	public Map<CoinType, List<Coin>> getCoins() {
		return this.coins;
	}

	public static CoinBank getCoinBank() {
		if (INSTANCE == null) {
			INSTANCE = new CoinBank();
		}
		return INSTANCE;
	}
}
