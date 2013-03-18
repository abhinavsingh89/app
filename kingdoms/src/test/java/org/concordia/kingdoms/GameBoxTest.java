/**
 * Gamebox TestCase class
 * @author Team K
 * @since 1.0
 */

package org.concordia.kingdoms;

import java.util.List;

import junit.framework.TestCase;

import org.concordia.kingdoms.tokens.Tile;
import org.concordia.kingdoms.tokens.TileType;

import com.google.common.collect.Lists;

public class GameBoxTest extends TestCase 
{
	/**
	 * Function to test if tiles are initialized.
	 */
	public void testTilesInitialized() {
		GameBox gameBox = GameBox.newGameBox();
		for (TileType type : TileType.values()) {
			List<Tile> tiles = gameBox.getTiles(type);
			assertEquals(tiles.isEmpty(), false);
			for (Tile tile : tiles) {
				assertNotNull(tile.getType());
			}
		}
	}

	/**
	 * Function to test for tiles count.
	 */
	public void testTilesCount() {
		GameBox gameBox = GameBox.newGameBox();
		List<Tile> allTiles = Lists.newArrayList();
		for (TileType type : TileType.values()) {
			List<Tile> tiles = gameBox.getTiles(type);
			switch (type) {
			case RESOURCE:
				assertEquals(tiles.size(), 12);
				break;
			case DRAGON:
				assertEquals(tiles.size(), 1);
				break;
			case GOLDMINE:
				assertEquals(tiles.size(), 1);
				break;
			case HAZARD:
				assertEquals(tiles.size(), 6);
				break;
			case MOUNTAIN:
				assertEquals(tiles.size(), 2);
				break;
			case WIZARD:
				assertEquals(tiles.size(), 1);
				break;
			default:
				fail();
			}
			allTiles.addAll(tiles);
		}
		assertEquals(allTiles.size(), 23);
	}

}
