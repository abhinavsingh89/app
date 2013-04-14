/**
 * class used for maintaining the tile bank
 * @author Team K
 * @since 1.0
 */
package org.concordia.kingdoms.board;

import java.util.Collections;
import java.util.List;

import org.concordia.kingdoms.GameBox;
import org.concordia.kingdoms.domain.Tile;
import org.concordia.kingdoms.domain.TileType;
import org.concordia.kingdoms.spring.SpringContainer;

import com.google.common.collect.Lists;

public class TileBank {

	private List<Tile> tiles;
	public static final List<TileType> TILE_TYPES = Lists.newArrayList(
			TileType.DRAGON, TileType.GOLDMINE, TileType.HAZARD,
			TileType.MOUNTAIN, TileType.RESOURCE, TileType.WIZARD);

	/**
	 * private method used for initializing the tile bank
	 */
	public TileBank() {
		this.tiles = Lists.newArrayList();
	}

	public void init() {

		final GameBox gameBox = SpringContainer.INSTANCE.getBean(GameBox.class);
		gameBox.assignTiles(this);
		Collections.shuffle(this.tiles);
	}

	public void addTiles(List<Tile> tiles) {
		if (tiles != null && !tiles.isEmpty()) {
			this.tiles.addAll(tiles);
		}
	}

	/**
	 * method to pick a tile from tilebank
	 * 
	 * @return tile
	 */
	public Tile drawTile() {
		if (tiles.isEmpty()) {
			return null;
		}
		return tiles.remove(tiles.size() - 1);
	}

	/**
	 * return Unmodifiable list of tiles being held with this tilebank
	 * 
	 * @return Unmodifiable list
	 */
	public List<Tile> getTiles() {
		return Collections.unmodifiableList(this.tiles);
	}

	/**
	 * true if the tile bank ran out of all tiles
	 */

	public boolean isEmpty() {
		return this.tiles.isEmpty();
	}

	Tile drawTile(TileType type) {
		Tile retTile = null;
		for (Tile tile : tiles) {
			if (type.equals(tile.getType())) {
				retTile = tile;
				break;
			}
		}
		if (retTile != null) {
			this.tiles.remove(retTile);
		}
		return retTile;
	}

	public void shuffleTiles() {
		Collections.shuffle(tiles);
	}
}
