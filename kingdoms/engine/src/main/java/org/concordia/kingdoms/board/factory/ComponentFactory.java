/**
 * interface class ComponentFactory
 * @author Team K
 * @since 1.0
 */
package org.concordia.kingdoms.board.factory;

import org.concordia.kingdoms.domain.Castle;
import org.concordia.kingdoms.domain.Color;
import org.concordia.kingdoms.domain.Tile;
import org.concordia.kingdoms.domain.TileType;

public interface ComponentFactory {

	Castle createCastle(final Integer rank, Color color);

	Tile createTile(TileType tileType, String name, int value);

}
