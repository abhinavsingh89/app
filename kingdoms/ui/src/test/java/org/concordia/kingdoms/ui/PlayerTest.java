/**
 *Class for Player test.
 *@author Team K
 *@since version 1.0
 */
package org.concordia.kingdoms.ui;

import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;

import org.concordia.kingdoms.Kingdoms;
import org.concordia.kingdoms.Player;
import org.concordia.kingdoms.board.IDisaster;
import org.concordia.kingdoms.board.TDCoordinate;
import org.concordia.kingdoms.board.factory.BoardBuilder;
import org.concordia.kingdoms.board.factory.TDBoardBuilder;
import org.concordia.kingdoms.domain.Color;
import org.concordia.kingdoms.exceptions.GameException;
import org.concordia.kingdoms.exceptions.GameRuleException;

import com.google.common.collect.Lists;

public class PlayerTest extends TestCase {

	public void testPlayerPossessThreeTiles() {
		List<Player<TDCoordinate>> players4 = Lists.newArrayList();
		players4.add(Player.<TDCoordinate> newPlayer("testPlayer1", Color.BLUE));
		players4.add(Player
				.<TDCoordinate> newPlayer("testPlayer2", Color.GREEN));
		players4.add(Player.<TDCoordinate> newPlayer("testPlayer3",
				Color.YELLOW));
		players4.add(Player.<TDCoordinate> newPlayer("testPlayer3", Color.RED));

		BoardBuilder boardBuilder = new TDBoardBuilder();
		try {
			Kingdoms<TDCoordinate> kingdoms = new TDKingdoms(boardBuilder, 6) {
				@Override
				protected List<IDisaster<TDCoordinate>> getDisasters() {
					return Lists.newArrayList();
				}
			};
			try {
				kingdoms.start(players4);
			} catch (GameException e) {
				e.printStackTrace();
				fail();
			}
			Player<TDCoordinate> player = players4.get(0);
			assertEquals(3, player.getPossessedTiles().size());

		} catch (IOException e) {
			fail();
			e.printStackTrace();
		}
	}

}