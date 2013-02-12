package org.concordia.kingdoms.board.factory;

import java.util.List;
import java.util.Map;

import org.concordia.kingdoms.GameBox;
import org.concordia.kingdoms.tokens.Tile;
import org.concordia.kingdoms.tokens.TileType;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class TileBank {

	private static TileBank INSTANCE;

	private Map<TileType, List<Tile>> tiles;

	private TileBank() {
		final List<TileType> tileTypes = Lists.newArrayList(TileType.DRAGON,
				TileType.GOLDMINE, TileType.HAZARD, TileType.MOUNTAIN,
				TileType.RESOURCE, TileType.WIZARD);
		this.tiles = Maps.newHashMap();
		for (final TileType type : tileTypes) {
			this.tiles.put(type, GameBox.getGameBox().getTiles(type));
		}
	}

	public Map<TileType, List<Tile>> getTiles() {
		return this.tiles;
	}

	public static TileBank getTileBank() {
		if (INSTANCE == null) {
			INSTANCE = new TileBank();
		}
		return INSTANCE;
	}
}
