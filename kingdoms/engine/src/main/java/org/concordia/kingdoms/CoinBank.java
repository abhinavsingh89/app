/**
 * coin reserve
 * @author Team K
 * @since 1.0
 */
package org.concordia.kingdoms;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.concordia.kingdoms.domain.Coin;
import org.concordia.kingdoms.domain.CoinType;

import com.google.common.collect.Maps;

/**
 * A repository containing all the coins
 * 
 * @author Team K
 * @since 1.0
 */
public class CoinBank {

	private Map<CoinType, List<Coin>> coins;

	/**
	 * initializes the coin bank
	 */
	private CoinBank() {
		this.coins = Maps.newHashMap();
	}

	/**
	 * returns an immutable map
	 * 
	 * @return coins
	 */
	public Map<CoinType, List<Coin>> getCoins() {
		return Collections.unmodifiableMap(this.coins);
	}
}
