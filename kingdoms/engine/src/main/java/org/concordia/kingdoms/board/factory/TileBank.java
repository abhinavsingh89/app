/**
 * class used for maintaining the tile bank
 * @author Team K
 * @since 1.0
 */
package org.concordia.kingdoms.board.factory;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.concordia.kingdoms.GameBox;
import org.concordia.kingdoms.domain.Tile;
import org.concordia.kingdoms.domain.TileType;

import com.google.common.collect.Lists;

public class TileBank {

	private static TileBank INSTANCE;

	private List<Tile> tiles;

	/**
	 * private method used for initializing the tile bank
	 */
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

	/**
	 * method to pick a tile from tilebank
	 * 
	 * @return tile
	 */
	public Tile pickTile() {
		// get a random tile
		int randomindex = new Random().nextInt(tiles.size());
		return tiles.remove(randomindex);
	}

	/**
	 * method used to return the tilebank instance
	 * 
	 * @return instance
	 */
	public static TileBank getTileBank() {
		if (INSTANCE == null) {
			INSTANCE = new TileBank();
		}
		return INSTANCE;
	}

	/**
	 * return Unmodifiable list of tiles being held with this tilebank
	 * 
	 * @return Unmodifiable list
	 */
	public List<Tile> getTiles() {
		return Collections.unmodifiableList(this.tiles);
	}
}
