package org.concordia.kingdoms;

import java.util.List;
import java.util.Map;

import org.concordia.kingdoms.board.Board;
import org.concordia.kingdoms.tokens.Castle;
import org.concordia.kingdoms.tokens.Coin;
import org.concordia.kingdoms.tokens.CoinType;
import org.concordia.kingdoms.tokens.Color;
import org.concordia.kingdoms.tokens.Tile;
import org.concordia.kingdoms.tokens.TileType;

import com.google.common.collect.Maps;

public class Player {

	private String name;

	private Color[] chosenColors;

	private int score;

	private Tile startingTile;

	private Map<CoinType, List<Coin>> coins;

	private Map<Integer, Map<Color, List<Castle>>> castles;

	private Map<TileType, List<Tile>> tiles;

	private Board board;

	private Player(String name, final Color[] chosenColors) {
		this.name = name;
		this.chosenColors = chosenColors;
		this.score = 0;
		this.startingTile = null;
		this.coins = Maps.newHashMap();
		this.castles = Maps.newHashMap();
	}

	public void putTile(Tile tile, int row, int column) throws Exception {
		if (!this.tiles.get(tile.getType()).contains(tile)) {
			throw new RuntimeException("Tile not available with this player");
		}
		this.board.putComponent(tile, row, column);
		this.tiles.get(tile.getType()).remove(tile);
	}

	public void putCastle(Castle castle, int row, int column) {
		if (!this.castles.get(castle.getRank()).get(castle.getColor())
				.contains(castle)) {
			throw new RuntimeException("Castle " + castle
					+ " not available with this player");
		}
		this.board.putComponent(castle, row, column);
		this.castles.get(castle.getRank()).remove(castle);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color[] getChosenColors() {
		return chosenColors;
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

	public void addCastle(int rank, Color color, final List<Castle> kastles) {
		if (kastles == null || kastles.isEmpty()) {
			return;
		}
		Map<Color, List<Castle>> kastleMap = this.castles.get(rank);
		if (kastleMap != null) {
			if (kastleMap.get(color) != null) {
				kastleMap.get(color).addAll(kastles);
			} else {
				kastleMap.put(color, kastles);
			}
		} else {
			kastleMap = Maps.newHashMap();
			kastleMap.put(color, kastles);
			this.castles.put(rank, kastleMap);
		}
	}

	public Castle removeCastle(int rank, Color color) {

		if (this.castles.get(rank) != null) {
			final List<Castle> castlesList = this.castles.get(rank).get(color);
			if (castlesList != null && castlesList.size() > 0) {
				return castlesList.remove(0);
			} else {
				throw new RuntimeException("No castle with color " + color
						+ " available with this player");
			}
		} else {
			throw new RuntimeException("No castle with rank " + rank
					+ "available with this player");
		}
	}

	public Castle getCastle(int rank, Color color) {
		if (this.castles.get(rank) != null) {
			final List<Castle> colorCastles = this.castles.get(rank).get(color);
			if (colorCastles != null && !colorCastles.isEmpty()) {
				return colorCastles.get(0);
			}
		}
		return null;
	}

	public static Player newPlayer(String name, final Color[] chosenColors) {
		return new Player(name, chosenColors);
	}
}
