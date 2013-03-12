package org.concordia.kingdoms;

import java.util.List;
import java.util.Map;

import org.concordia.kingdoms.domain.Castle;
import org.concordia.kingdoms.domain.Coin;
import org.concordia.kingdoms.domain.CoinType;
import org.concordia.kingdoms.domain.Color;
import org.concordia.kingdoms.domain.Tile;
import org.concordia.kingdoms.domain.TileType;
import org.concordia.kingdoms.exceptions.GameException;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * This class is useful when the game board is initialized. It consist of all
 * the tiles, castles, coins.
 * 
 * @author Team K
 * @since 1.0
 * 
 */
public class GameBox {

	private static GameBox INSTANCE;

	private Map<TileType, List<Tile>> tiles = Maps.newHashMap();

	private Map<CoinType, List<Coin>> coins = Maps.newHashMap();

	private Map<Integer, Integer> rankOneCastlesPerPlayer = Maps.newHashMap();

	private Map<Integer, Map<Color, List<Castle>>> castles = Maps.newHashMap();

	public GameBox(Map<TileType, List<Tile>> tiles,
			Map<CoinType, List<Coin>> coins,
			Map<Integer, Integer> rankOneCastlesPerPlayer,
			Map<Integer, Map<Color, List<Castle>>> castles) {
		this.tiles = tiles;
		this.coins = coins;
		this.rankOneCastlesPerPlayer = rankOneCastlesPerPlayer;
		this.castles = castles;
	}

	// create 'size' number of new castles with given properties of castle
	public static List<Castle> newCastles(int rank, Color color, int size) {
		final List<Castle> castles = Lists.newArrayList();
		for (int i = 0; i < size; i++) {
			castles.add(Castle.newCastle(rank, color));
		}
		return castles;
	}

	/**
	 * method allows to take coins from the gamebox, this actaully removes coins
	 * from the game box, so make sure you use it appropriately
	 */
	public List<Coin> takeCoins(CoinType type, int size) throws GameException {
		final List<Coin> coinList = this.coins.get(type);
		if (size > coinList.size()) {
			throw new GameException(size + " coins of type " + type
					+ " are not available");
		}
		final List<Coin> retCoins = Lists.newArrayList();
		for (int i = 0; i < size; i++) {
			retCoins.add(coinList.remove(i));
		}
		return retCoins;
	}

	/**
	 * Each player chooses a color and takes all the rank 2, 3, and 4 castles of
	 * his color
	 * 
	 * @param player
	 * @param totalPlayers
	 */
	public void assignCastles(final Player player, int totalPlayers) {
		for (int rank = 2; rank <= 4; rank++) {
			final Map<Color, List<Castle>> castlesMap = this.castles.get(rank);
			final Color color = player.getChosenColor();
			player.addCastle(rank, castlesMap.remove(color));
		}
	}

	/**
	 * @formatter:off
	 * number of rank 1 castles determined by the number of players 
	 * Number of Players Rank 1 Castles Rank 2, 3, & 4 Castles 
	 * 2 					4 			all 
	 * 3 					3 			all 
	 * 4 					2 			all
	 * 
	 * @param player
	 * @param totalPlayers
	 */
	public void assignRankOneCastles(final Player player, int totalPlayers) {
		final Color color = player.getChosenColor();
		final List<Castle> colorRankCastles = this.castles.get(1).get(color);
		int size = colorRankCastles.size();
		final List<Castle> retCastles = Lists.newArrayList();
		for (int i = 0; i < rankOneCastlesPerPlayer.get(totalPlayers)
				&& size >= 1; i++, size--) {
			retCastles.add(colorRankCastles.remove(0));
		}
		player.addCastle(1, retCastles);
	}

	public void assignTiles(TileBank tileBank) {
		/*
		 * the tiles pulled from this gamebox should no longer be available here
		 */
		for(TileType tileType: TileBank.TILE_TYPES){
			tileBank.addTiles(this.tiles.remove(tileType));
		}
	}

}
