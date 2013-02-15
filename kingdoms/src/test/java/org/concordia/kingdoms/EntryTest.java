package org.concordia.kingdoms;

import junit.framework.TestCase;

import org.concordia.kingdoms.board.Entry;

import org.concordia.kingdoms.exceptions.GameRuleException;

import org.concordia.kingdoms.tokens.Tile;

import org.concordia.kingdoms.tokens.TileType;

public class EntryTest extends TestCase {

public void testCannotPlacwComponentIfAlreadyFilled() {

Entry entry = Entry.newEntry(0, 0);

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