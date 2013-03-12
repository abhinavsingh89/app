package org.concordia.kingdoms;

import junit.framework.TestCase;

import org.concordia.kingdoms.board.Entry;
import org.concordia.kingdoms.board.TDCoordinate;
import org.concordia.kingdoms.domain.Tile;
import org.concordia.kingdoms.domain.TileType;
import org.concordia.kingdoms.exceptions.GameRuleException;

public class EntryTest extends TestCase {

	public void testCannotPlaceComponentIfAlreadyFilled() {

		Entry<TDCoordinate> entry = new Entry<TDCoordinate>(
				TDCoordinate.newInstance(0, 0), null);

		try {

			entry.setComponent(Tile.newTile(TileType.DRAGON,

			TileType.DRAGON.name(), 0));

			entry.setComponent(Tile.newTile(TileType.GOLDMINE,

			TileType.GOLDMINE.name(), 0));

			fail();

		} catch (GameRuleException ex) {

		}

	}

}