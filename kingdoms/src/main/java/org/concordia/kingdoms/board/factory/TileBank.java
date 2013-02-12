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

	private Map<TileType, List<Tile>> tilesMap;

	private TileBank() {
		final List<TileType> tileTypes = Lists.newArrayList(TileType.DRAGON,
				TileType.GOLDMINE, TileType.HAZARD, TileType.MOUNTAIN,
				TileType.RESOURCE, TileType.WIZARD);
		this.tilesMap = Maps.newHashMap();
		for (final TileType type : tileTypes) {
			this.tilesMap.put(type, GameBox.getGameBox().getTiles(type));
		}
	}

	public Tile getTile(TileType type, String name, Integer tileValue) {
		final List<Tile> tiles = this.tilesMap.get(type);
		Tile retTile = null;
		for (final Tile tile : tiles) {
			if (name.equals(tile.getName())) {
				if (tileValue == null && tile.getValue() == null) {
					retTile = tile;
				} else {
					if (tileValue.equals(tile.getValue())) {
						retTile = tile;
					}
				}
			}
		}
		if (retTile == null) {
			return null;
		}
		tiles.remove(retTile);
		return retTile;
	}

	public static TileBank getTileBank() {
		if (INSTANCE == null) {
			INSTANCE = new TileBank();
		}
		return INSTANCE;
	}
}
