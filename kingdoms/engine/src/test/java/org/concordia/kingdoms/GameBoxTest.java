/**
 *Class for Gamebox Test.
 *@author Team K
 *@since version 1.0
*/
package org.concordia.kingdoms;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.concordia.kingdoms.board.TileBank;
import org.concordia.kingdoms.domain.Tile;
import org.concordia.kingdoms.domain.TileType;
import org.concordia.kingdoms.spring.SpringContainer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class GameBoxTest extends TestCase {

	public void testTilesInitialized() {

		GameBox gameBox = SpringContainer.INSTANCE.getBean("gameBox",
				GameBox.class);
		TileBank tileBank = SpringContainer.INSTANCE.getBean("tileBank",
				TileBank.class);
		tileBank.init();
		gameBox.assignTiles(tileBank);
		Iterator<Tile> itr = tileBank.getTiles().iterator();
		while (itr.hasNext()) {
			Tile tile = itr.next();
			assertNotNull(tile);
		}
	}

	public void testTilesCount() {
		GameBox gameBox = SpringContainer.INSTANCE.getBean("gameBox",
				GameBox.class);
		TileBank tileBank = SpringContainer.INSTANCE.getBean("tileBank",
				TileBank.class);
		tileBank.init();
		gameBox.assignTiles(tileBank);
		List<Tile> tiles = tileBank.getTiles();

		Map<TileType, List<Tile>> tilesMap = Maps.newHashMap();

		Iterator<Tile> itr = tiles.iterator();

		while (itr.hasNext()) {
			Tile tile = itr.next();
			if (tilesMap.containsKey(tile.getType())) {
				tilesMap.get(tile.getType()).add(tile);
			} else {
				tilesMap.put(tile.getType(), Lists.newArrayList(tile));
			}
		}

		for (TileType type : TileType.values()) {
			int count = tilesMap.get(type).size();
			switch (type) {
			case RESOURCE:
				assertEquals(count, 12);
				break;
			case DRAGON:
				assertEquals(count, 1);
				break;
			case GOLDMINE:
				assertEquals(count, 1);
				break;
			case HAZARD:
				assertEquals(count, 6);
				break;
			case MOUNTAIN:
				assertEquals(count, 2);
				break;
			case WIZARD:
				assertEquals(count, 1);
				break;
			default:
				fail();
			}
		}
	}
}
