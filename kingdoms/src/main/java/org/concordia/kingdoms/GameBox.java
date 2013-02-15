package org.concordia.kingdoms;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.concordia.kingdoms.board.factory.ComponentFactory;
import org.concordia.kingdoms.board.factory.KingdomComponentFactory;
import org.concordia.kingdoms.exceptions.GameException;
import org.concordia.kingdoms.tokens.Castle;
import org.concordia.kingdoms.tokens.Coin;
import org.concordia.kingdoms.tokens.CoinType;
import org.concordia.kingdoms.tokens.Color;
import org.concordia.kingdoms.tokens.Tile;
import org.concordia.kingdoms.tokens.TileType;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class GameBox {

	private static GameBox INSTANCE;

	private Map<TileType, List<Tile>> tiles = Maps.newHashMap();

	private Map<CoinType, List<Coin>> coins = Maps.newHashMap();

	private Map<Integer, Integer> rankOneCastlesPerPlayer = Maps.newHashMap();

	private Map<Integer, Map<Color, List<Castle>>> castles = Maps.newHashMap();

	private ComponentFactory componentFactory = new KingdomComponentFactory();

	public GameBox() {
		// tiles
		fillTiles();
		// coins
		fillCoins();
		// castles
		fillCastles();

		fillRankOneCastlesPerPlayer();
	}

	private void fillRankOneCastlesPerPlayer() {
		this.rankOneCastlesPerPlayer.put(2, 4);
		this.rankOneCastlesPerPlayer.put(3, 3);
		this.rankOneCastlesPerPlayer.put(4, 2);
	}

	private void fillTiles() {

		// resource tiles
		final List<Tile> resourceTiles = Lists.newArrayList();
		resourceTiles.add(this.componentFactory.createTile(TileType.RESOURCE,
				NameUtils.RESOURCE_CITIES, 6));
		resourceTiles.add(this.componentFactory.createTile(TileType.RESOURCE,
				NameUtils.RESOURCE_CITIES, 5));
		resourceTiles.add(this.componentFactory.createTile(TileType.RESOURCE,
				NameUtils.RESOURCE_CITIES, 4));
		resourceTiles.add(this.componentFactory.createTile(TileType.RESOURCE,
				NameUtils.RESOURCE_CITIES, 5));

		resourceTiles.add(this.componentFactory.createTile(TileType.RESOURCE,
				NameUtils.RESOURCE_VILLAGES, 6));
		resourceTiles.add(this.componentFactory.createTile(TileType.RESOURCE,
				NameUtils.RESOURCE_VILLAGES, 5));
		resourceTiles.add(this.componentFactory.createTile(TileType.RESOURCE,
				NameUtils.RESOURCE_VILLAGES, 1));
		resourceTiles.add(this.componentFactory.createTile(TileType.RESOURCE,
				NameUtils.RESOURCE_VILLAGES, 2));

		resourceTiles.add(this.componentFactory.createTile(TileType.RESOURCE,
				NameUtils.RESOURCE_FARMS, 3));
		resourceTiles.add(this.componentFactory.createTile(TileType.RESOURCE,
				NameUtils.RESOURCE_FARMS, 2));
		resourceTiles.add(this.componentFactory.createTile(TileType.RESOURCE,
				NameUtils.RESOURCE_FARMS, 1));
		resourceTiles.add(this.componentFactory.createTile(TileType.RESOURCE,
				NameUtils.RESOURCE_FARMS, 5));

		this.tiles.put(TileType.RESOURCE, resourceTiles);

		// hazard tiles
		final List<Tile> hazardTiles = Lists.newArrayList();

		hazardTiles.add(this.componentFactory.createTile(TileType.HAZARD,
				NameUtils.HAZARD_BEAR, -6));
		hazardTiles.add(this.componentFactory.createTile(TileType.HAZARD,
				NameUtils.HAZARD_HYNA, -5));
		hazardTiles.add(this.componentFactory.createTile(TileType.HAZARD,
				NameUtils.HAZARD_LION, -4));
		hazardTiles.add(this.componentFactory.createTile(TileType.HAZARD,
				NameUtils.HAZARD_TIGER, -3));
		hazardTiles.add(this.componentFactory.createTile(TileType.HAZARD,
				NameUtils.HAZARD_VULTURE, -2));
		hazardTiles.add(this.componentFactory.createTile(TileType.HAZARD,
				NameUtils.HAZARD_THUNDER, -1));

		this.tiles.put(TileType.HAZARD, hazardTiles);

		// mountain tile
		final List<Tile> mountainTiles = Lists.newArrayList();

		mountainTiles.add(this.componentFactory.createTile(TileType.MOUNTAIN,
				NameUtils.MOUNTAIN_EVEREST, 0));
		mountainTiles.add(this.componentFactory.createTile(TileType.MOUNTAIN,
				NameUtils.MOUNTAIN_ALPES, 0));

		this.tiles.put(TileType.MOUNTAIN, mountainTiles);

		// dragon tile
		this.tiles.put(TileType.DRAGON, Lists
				.newArrayList(this.componentFactory.createTile(TileType.DRAGON,
						NameUtils.DRAGON_RED, 0)));
		// goldmine tile
		this.tiles.put(TileType.GOLDMINE, Lists
				.newArrayList(this.componentFactory.createTile(
						TileType.GOLDMINE, NameUtils.GOLDMINE, 0)));
		// wizard tile
		this.tiles.put(TileType.WIZARD, Lists
				.newArrayList(this.componentFactory.createTile(TileType.WIZARD,
						NameUtils.WIZARD, 0)));
	}

	private void fillCoins() {
		this.coins.put(CoinType.COPPER_1, Coin.newCoins(CoinType.COPPER_1, 19));
		this.coins.put(CoinType.COPPER_5, Coin.newCoins(CoinType.COPPER_5, 12));
		this.coins.put(CoinType.SILVER_10,
				Coin.newCoins(CoinType.SILVER_10, 20));
		this.coins.put(CoinType.GOLD_50, Coin.newCoins(CoinType.GOLD_50, 8));
		this.coins.put(CoinType.GOLD_100, Coin.newCoins(CoinType.GOLD_100, 4));
	}

	private void fillCastles() {
		// 40 Plastic Castles, including
		for (final Color color : Color.values()) {
			// 16 Rank 1 Castles (4 per color)
			loadCastles(1, color, 4);
			// 12 Rank 2 Castles (3 per color)
			loadCastles(2, color, 3);
			// 8 Rank 3 Castles (2 per color)
			loadCastles(3, color, 2);
			// 4 Rank 4 Castles (1 per color)
			loadCastles(4, color, 1);
		}
	}

	private void loadCastles(int rank, Color color, int size) {
		if (this.castles.containsKey(rank)) {
			this.castles.get(rank).put(color, newCastles(rank, color, size));
		} else {
			final Map<Color, List<Castle>> colorCastles = Maps.newHashMap();
			colorCastles.put(color, newCastles(rank, color, size));
			this.castles.put(rank, colorCastles);
		}
	}

	private List<Castle> newCastles(int rank, Color color, int size) {
		final List<Castle> castles = Lists.newArrayList();
		for (int i = 0; i < size; i++) {
			castles.add(this.componentFactory.createCastle(rank, color));
		}
		return castles;
	}

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
	 * @formatter:on Each player chooses a color and takes all the rank 2, 3,
	 *               and 4 castles of his color
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

	public List<Tile> getTiles(TileType type) {
		// FIXME : check the tiles pulled from this gamebox should no longer be
		// available here
		return this.tiles.remove(type);
	}

	public static GameBox getGameBox() {
		if (INSTANCE != null) {
			return INSTANCE;
		}
		INSTANCE = new GameBox();
		return INSTANCE;
	}

	public static GameBox newGameBox() {
		return new GameBox();
	}

	public static GameBox reloadGameBox(File file) {
		GameBox.INSTANCE = new GameBox();
		return GameBox.INSTANCE;
	}
}
