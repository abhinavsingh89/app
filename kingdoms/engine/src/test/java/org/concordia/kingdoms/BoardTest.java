/**
 *Class for testing of Kingdoms Board.
 *@author Team K
 *@since version 1.0
*/
package org.concordia.kingdoms;

import java.util.List;

import junit.framework.TestCase;

import org.concordia.kingdoms.board.Board;
import org.concordia.kingdoms.board.TDCoordinate;
import org.concordia.kingdoms.board.factory.BoardBuilder;
import org.concordia.kingdoms.board.factory.TDBoardBuilder;
import org.concordia.kingdoms.domain.Color;
import org.concordia.kingdoms.exceptions.GameException;
import org.concordia.kingdoms.exceptions.GameRuleException;
import org.junit.Test;

import com.google.common.collect.Lists;

public class BoardTest extends TestCase {

	static BoardBuilder boardBuilder = null;
	static Board board = null;
	static List<Player> players4;

	static {
		boardBuilder = new TDBoardBuilder();

		players4 = Lists.newArrayList();
		players4.add(Player.newPlayer("testPlayer1", Color.BLUE));
		players4.add(Player.newPlayer("testPlayer2", Color.GREEN));
		players4.add(Player.newPlayer("testPlayer3", Color.YELLOW));
		players4.add(Player.newPlayer("testPlayer3", Color.RED));

		try {
			board = boardBuilder.buildBoard(
					TDCoordinate.newInstance(Board.MAX_ROWS, Board.MAX_COLUMNS),
					players4);
		} catch (GameException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testHasAnyEmptySpace() {

		try {
			Player player2 = players4.get(1);
			players4.get(1).putCastle(player2.getCastle(2),
					TDCoordinate.newInstance(0, 1));
			try {
				players4.get(1).putCastle(player2.getCastle(1),
						TDCoordinate.newInstance(0, 1));
				fail();
			} catch (GameRuleException ex) {

			}
		} catch (GameException e) {
			fail();
		}
	}

	@Test
	public void testIsEntryEmpty() {
		try {
			Player player2 = players4.get(1);
			boolean isEntryEmpty = board.isEntryEmpty(TDCoordinate.newInstance(
					0, 0));
			assertEquals(isEntryEmpty, true);
			players4.get(1).putCastle(player2.getCastle(2),
					TDCoordinate.newInstance(0, 0));
			isEntryEmpty = board.isEntryEmpty(TDCoordinate.newInstance(0, 0));
			assertEquals(isEntryEmpty, false);

		} catch (GameException e) {
			fail();
		}
	}

	public void testIsValidPosition() {
		List<Player> players4 = Lists.newArrayList();
		players4.add(Player.newPlayer("testPlayer1", Color.BLUE));
		players4.add(Player.newPlayer("testPlayer2", Color.GREEN));
		players4.add(Player.newPlayer("testPlayer3", Color.YELLOW));
		players4.add(Player.newPlayer("testPlayer3", Color.RED));

		BoardBuilder boardBuilder = new TDBoardBuilder();
		Board board = null;
		try {
			board = boardBuilder
					.buildBoard(TDCoordinate.newInstance(Board.MAX_ROWS,
							Board.MAX_COLUMNS), players4);
		} catch (GameException e) {
			fail();
		}
		boolean isValidCoordinate = board.isValidCoordinate(Board.MAX_ROWS,
				Board.MAX_COLUMNS);
		assertEquals(isValidCoordinate, false);
		isValidCoordinate = board.isValidCoordinate(Board.MAX_ROWS - 1,
				Board.MAX_COLUMNS - 1);
		assertEquals(isValidCoordinate, true);
	}

}
