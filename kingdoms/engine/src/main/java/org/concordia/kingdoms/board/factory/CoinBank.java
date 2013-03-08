/**
 * class for maintaining 
 * @author Team K
 * @since 1.0
 */
package org.concordia.kingdoms.board.factory;

import java.util.List;
import java.util.Map;

import org.concordia.kingdoms.domain.Coin;
import org.concordia.kingdoms.domain.CoinType;

import com.google.common.collect.Maps;

public class CoinBank {

	private static CoinBank INSTANCE;

	private Map<CoinType, List<Coin>> coins;

	/**
	 * initializes the coin bank
	 */
	private CoinBank() {
		this.coins = Maps.newHashMap();
	}

	/**
	 * method used for returning the coin map
	 * @return coins
	 */
	public Map<CoinType, List<Coin>> getCoins() {
		return this.coins;
	}

	/**
	 * method used for returning the coinbank instance
	 * @return instance
	 */
	public static CoinBank getCoinBank() {
		if (INSTANCE == null) {
			INSTANCE = new CoinBank();
		}
		return INSTANCE;
	}
}
