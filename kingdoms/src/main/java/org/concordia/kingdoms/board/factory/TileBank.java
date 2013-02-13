package org.concordia.kingdoms.board.factory;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.concordia.kingdoms.GameBox;
import org.concordia.kingdoms.tokens.Tile;
import org.concordia.kingdoms.tokens.TileType;

import com.google.common.collect.Lists;

public class TileBank {

	private static TileBank INSTANCE;

	private List<Tile> tiles;

	private TileBank() {
		final List<TileType> tileTypes = Lists.newArrayList(TileType.DRAGON,
				TileType.GOLDMINE, TileType.HAZARD, TileType.MOUNTAIN,
				TileType.RESOURCE, TileType.WIZARD);
		this.tiles = Lists.newArrayList();
		for (final TileType type : tileTypes) {
			this.tiles.addAll((GameBox.getGameBox().getTiles(type)));
		}
		Collections.shuffle(this.tiles);
	}

	public Tile getTile() {
		// get a random tile
		int randomindex = new Random().nextInt(tiles.size());
		return tiles.get(randomindex);
	}

	public static TileBank getTileBank() {
		if (INSTANCE == null) {
			INSTANCE = new TileBank();
		}
		return INSTANCE;
	}
}
