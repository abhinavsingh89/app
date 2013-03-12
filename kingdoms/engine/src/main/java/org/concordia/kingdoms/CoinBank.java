/**
 * coin reserve
 * @author Team K
 * @since 1.0
 */
package org.concordia.kingdoms;

import java.util.List;
import java.util.Map;

import org.concordia.kingdoms.domain.Coin;
import org.concordia.kingdoms.domain.CoinType;

import com.google.common.collect.Maps;

public class CoinBank {

	private Map<CoinType, List<Coin>> coins;

	/**
	 * initializes the coin bank
	 */
	private CoinBank() {
		this.coins = Maps.newHashMap();
	}

	/**
	 * method for returning the coin map
	 * 
	 * @return coins
	 */
	public Map<CoinType, List<Coin>> getCoins() {
		return this.coins;
	}
}
