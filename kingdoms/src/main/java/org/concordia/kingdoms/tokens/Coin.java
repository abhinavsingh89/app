package org.concordia.kingdoms.tokens;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.concordia.kingdoms.board.factory.CoinBank;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.google.common.collect.Lists;

/**
 * coins are used to track how much worth each player has
 * 
 * @author Team K
 * @since 1.0
 * @see CoinBank
 */
@AutoProperty
@XmlRootElement(name = "coin")
public class Coin {

	@XmlAttribute
	private CoinType type;

	@XmlAttribute
	private int value;

	private Coin() {
		this(null, 0);
	}

	/**
	 * Constructor for a coin
	 * 
	 * @param type
	 *            - chosen type of the coin
	 * @param value
	 *            - value of the coin
	 * @return coin
	 */
	private Coin(CoinType type, int value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * @return value associated with this coin
	 */
	public int getValue() {
		return this.value;
	}

	/**
	 * each coin is categorized into one of the types in {@linkplain CoinType}
	 * 
	 * @return @see CoinType
	 */

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

	/**
	 * A factory method to create a list of coins for the given {@link CoinType}
	 * 
	 * @param type
	 *            {@link CoinType}
	 * @param size
	 *            total number of {@link Coin}to be created
	 * @return Collection of {@link Coin}(s)
	 */
	public static List<Coin> newCoins(CoinType type, int size) {
		final List<Coin> coins = Lists.newArrayList();
		for (int i = 0; i < size; i++) {
			coins.add(Coin.newCoin(type));
		}
		return coins;
	}

	/**
	 * A factory method to create a new {@link Coin} for the given
	 * {@link CoinType}. The default {@link #value} of the coin will be the
	 * value of the {@link CoinType}
	 * 
	 * @param type
	 *            {@link CoinType}
	 * 
	 * @return A new {@link Coin}
	 */
	public static Coin newCoin(CoinType type) {
		return new Coin(type, type.getValue());
	}

}
