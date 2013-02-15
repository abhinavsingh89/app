package org.concordia.kingdoms;

import java.util.List;
import java.util.Map;

import org.concordia.kingdoms.board.Board;
import org.concordia.kingdoms.exceptions.GameRuleException;
import org.concordia.kingdoms.tokens.Castle;
import org.concordia.kingdoms.tokens.Coin;
import org.concordia.kingdoms.tokens.CoinType;
import org.concordia.kingdoms.tokens.Color;
import org.concordia.kingdoms.tokens.Tile;

import com.google.common.collect.Maps;

/**
 * 
 * @author Team K
 * @version 1.0-SNAPSHOT
 * 
 */
public class Player {

	private String name;

	private Color chosenColor;

	private int score;

	private Tile startingTile;

	private Map<CoinType, List<Coin>> coins;

	private Map<Integer, List<Castle>> castles;

	private Board board;

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
		this.score = 0;
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
	public void putTile(Tile tile, int row, int column)
			throws GameRuleException {
		this.board.putComponent(tile, row, column);
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
	public void putCastle(Castle castle, int row, int column)
			throws GameRuleException {
		if (!this.castles.get(castle.getRank()).contains(castle)) {
			throw new GameRuleException("Castle " + castle
					+ " not available with this player");
		}
		this.board.putComponent(castle, row, column);
		this.castles.get(castle.getRank()).remove(castle);
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
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

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public static Player newPlayer(String name, final Color chosenColor) {
		return new Player(name, chosenColor);
	}
}
