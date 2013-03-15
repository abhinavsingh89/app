package org.concordia.kingdoms;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.concordia.kingdoms.board.Board;
import org.concordia.kingdoms.board.ICoordinate;
import org.concordia.kingdoms.board.Score;
import org.concordia.kingdoms.board.TDCoordinate;
import org.concordia.kingdoms.domain.Castle;
import org.concordia.kingdoms.domain.Coin;
import org.concordia.kingdoms.domain.CoinType;
import org.concordia.kingdoms.domain.Color;
import org.concordia.kingdoms.domain.Component;
import org.concordia.kingdoms.domain.Tile;
import org.concordia.kingdoms.exceptions.GameRuleException;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * @author Team K
 * @since 1.0
 * 
 */
public class Player<T extends ICoordinate> {

	private String name;

	private Color chosenColor;

	private List<Score> scores;

	private Tile startingTile;

	private Map<CoinType, List<Coin>> coins;

	private Map<Integer, List<Castle>> castles;

	private Board<T> board;

	/**
	 * Constructor for player
	 * 
	 * @param name
	 *            - Name of the player
	 * @param chosenColors
	 *            - Different colors chosen by player
	 * 
	 */
	private Player(String name, final Color chosenColor) {
		this.name = name;
		this.chosenColor = chosenColor;
		this.scores = Lists.newArrayList();
		this.startingTile = null;
		this.coins = Maps.newHashMap();
		this.castles = Maps.newHashMap();
	}

	/**
	 * method used to put tile
	 * 
	 * @param tile
	 *            - which type of tile player want to put
	 * @param row
	 *            - row number
	 * @param column
	 *            - row column
	 */
	public void putTile(Tile tile, T coordinate) throws GameRuleException {
		this.board.putComponent(tile, coordinate);
	}

	/**
	 * method used to put castle
	 * 
	 * @param castle
	 *            - which type of castle player want to put
	 * @param row
	 *            - row number
	 * @param column
	 *            - row column
	 */
	public void putCastle(Castle castle, T coordinate) throws GameRuleException {
		if (!this.castles.get(castle.getValue()).contains(castle)) {
			throw new GameRuleException("Castle " + castle
					+ " not available with this player");
		}
		this.board.putComponent((Component) castle, coordinate);
		this.castles.get(castle.getValue()).remove(castle);
	}

	/**
	 * add castles to the player
	 * 
	 * @param rank
	 *            all kastles belong to this rank
	 * @param kastles
	 *            list of kastles appended to the existing list
	 */

	public void addCastle(int rank, final List<Castle> kastles) {
		if (kastles == null || kastles.isEmpty()) {
			return;
		}
		final List<Castle> rankCastles = this.castles.get(rank);
		if (rankCastles != null) {
			rankCastles.addAll(kastles);
		} else {
			this.castles.put(rank, kastles);
		}

	}

	public Castle removeCastle(int rank) throws GameRuleException {

		final List<Castle> castlesList = this.castles.get(rank);
		if (castlesList != null && !castlesList.isEmpty()) {
			return castlesList.remove(0);
		} else {
			throw new GameRuleException("No castle with rank " + rank
					+ "available with this player");
		}
	}

	public Castle getCastle(int rank) {
		final List<Castle> rankCastles = this.castles.get(rank);
		if (rankCastles != null && !rankCastles.isEmpty()) {
			return rankCastles.get(0);
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getChosenColor() {
		return this.chosenColor;
	}

	public Tile getStartingTile() {
		return startingTile;
	}

	public void setStartingTile(Tile startingTile) {
		this.startingTile = startingTile;
	}

	public Map<CoinType, List<Coin>> getCoins() {
		return coins;
	}

	public void setCoins(Map<CoinType, List<Coin>> coins) {
		this.coins = coins;
	}

	public Board<T> getBoard() {
		return this.board;
	}

	public void setBoard(Board<T> board) {
		this.board = board;
	}

	public static Player<TDCoordinate> newPlayer(String name,
			final Color chosenColor) {
		return new Player<TDCoordinate>(name, chosenColor);
	}

	/**
	 * castles associated with tihs player
	 * 
	 * @return unmodifiable map this includes even the list of castles are also
	 *         not modifiable
	 */
	public Map<Integer, List<Castle>> getCastles() {
		Iterator<Integer> rankItr = this.castles.keySet().iterator();
		Map<Integer, List<Castle>> retMap = Maps.newHashMap();
		while (rankItr.hasNext()) {
			Integer rank = rankItr.next();
			retMap.put(rank,
					Collections.unmodifiableList(this.castles.get(rank)));
		}
		return Collections.unmodifiableMap(retMap);
	}

	public void addNewScore(Score newScore) {
		this.scores.add(newScore);
	}

	public int getTotalScore() {
		int total = 50;
		for (Score score : scores) {
			total += score.score();
		}
		return total;
	}

	public boolean hasAnyCastleAvailable() {

		Iterator<Integer> itr = this.castles.keySet().iterator();
		while (itr.hasNext()) {
			Integer rank = itr.next();
			if (!this.castles.get(rank).isEmpty()) {
				return true;
			}
		}

		return false;
	}

	public static class PlayerComparator implements Comparator<Player<?>> {

		public static final PlayerComparator INSTANCE = new PlayerComparator();

		public int compare(Player<?> o1, Player<?> o2) {
			return o2.getTotalScore() - o1.getTotalScore();
		}
	}
}
