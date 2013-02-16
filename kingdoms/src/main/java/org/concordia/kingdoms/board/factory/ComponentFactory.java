/**
 * interface class ComponentFactory
 * @author Team K
 * @since 1.0
 */
package org.concordia.kingdoms.board.factory;

import org.concordia.kingdoms.tokens.Castle;
import org.concordia.kingdoms.tokens.Color;
import org.concordia.kingdoms.tokens.Tile;
import org.concordia.kingdoms.tokens.TileType;

public interface ComponentFactory {

	Castle createCastle(final Integer rank, Color color);

	Tile createTile(TileType tileType, String name, int value);

}
