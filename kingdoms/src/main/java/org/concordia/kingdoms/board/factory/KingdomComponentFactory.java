package org.concordia.kingdoms.board.factory;

import org.concordia.kingdoms.tokens.Castle;
import org.concordia.kingdoms.tokens.Color;
import org.concordia.kingdoms.tokens.Tile;
import org.concordia.kingdoms.tokens.TileType;

public class KingdomComponentFactory implements ComponentFactory {

	public Castle createCastle(final Integer rank, Color color) {
		return Castle.newCastle(rank, color);
	}

	public Tile createTile(TileType type, String name, int value) {
		return Tile.newTile(type, name, value);
	}
}
