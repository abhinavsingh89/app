/**
 * class used for initializing the castle and it implements ComponentFactory.
 * @author Team K
 * @since 1.0
 */package org.concordia.kingdoms.board.factory;

import org.concordia.kingdoms.tokens.Castle;
import org.concordia.kingdoms.tokens.Color;
import org.concordia.kingdoms.tokens.Tile;
import org.concordia.kingdoms.tokens.TileType;

public class KingdomComponentFactory implements ComponentFactory {

	/**
	 * method used for creating the castle.
	 * @return castle
	 */
	public Castle createCastle(final Integer rank, Color color) {
		return Castle.newCastle(rank, color);
	}

	/**
	 * method used for creating the tile
	 * @return tile.
	 */
	public Tile createTile(TileType type, String name, int value) {
		return Tile.newTile(type, name, value);
	}
}
