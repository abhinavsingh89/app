/**
 *Class for testing of Kingdoms Board.
 *@author Team K
 *@since version 1.0
*/
package org.concordia.kingdoms;

import java.util.List;

import junit.framework.TestCase;

import org.concordia.kingdoms.board.Board;
import org.concordia.kingdoms.board.factory.BoardBuilder;
import org.concordia.kingdoms.board.factory.KingdomBoardBuilder;
import org.concordia.kingdoms.exceptions.GameException;
import org.concordia.kingdoms.exceptions.GameRuleException;
import org.concordia.kingdoms.tokens.Color;

import com.google.common.collect.Lists;

public class BoardTest extends TestCase {
/**
 * Function to check for empty space on board.
*/
	public void testHasAnyEmptySpace() {
		BoardBuilder boardBuilder = KingdomBoardBuilder
				.newKingdomBoardBuilder();
		Board board = null;

		List<Player> players4 = Lists.newArrayList();
		players4.add(Player.newPlayer("testPlayer1", Color.BLUE));
		players4.add(Player.newPlayer("testPlayer2", Color.GREEN));
		players4.add(Player.newPlayer("testPlayer3", Color.YELLOW));
		players4.add(Player.newPlayer("testPlayer3", Color.RED));

		try {
			board = boardBuilder.buildBoard(Board.MAX_ROWS, Board.MAX_COLUMNS,
					players4);
			Player player2 = players4.get(1);
			players4.get(1).putCastle(player2.getCastle(2), 0, 0);
			try {
				players4.get(1).putCastle(player2.getCastle(1), 0, 0);
				fail();
			} catch (GameRuleException ex) {

			}
		} catch (GameException e) {
			fail();
		}
		board.resetBoard();
	}
	
	/**
	 * Function for entry empty testing
	 */

	public void testIsEntryEmpty() {
		BoardBuilder boardBuilder = KingdomBoardBuilder
				.newKingdomBoardBuilder();
		Board board = null;

		List<Player> players4 = Lists.newArrayList();
		players4.add(Player.newPlayer("testPlayer1", Color.BLUE));
		players4.add(Player.newPlayer("testPlayer2", Color.GREEN));
		players4.add(Player.newPlayer("testPlayer3", Color.YELLOW));
		players4.add(Player.newPlayer("testPlayer3", Color.RED));

		try {
			board = boardBuilder.buildBoard(Board.MAX_ROWS, Board.MAX_COLUMNS,
					players4);
			Player player2 = players4.get(1);
			boolean isEntryEmpty = board.isEntryEmpty(0, 0);
			assertEquals(isEntryEmpty, true);
			players4.get(1).putCastle(player2.getCastle(2), 0, 0);
			isEntryEmpty = board.isEntryEmpty(0, 0);
			assertEquals(isEntryEmpty, false);

		} catch (GameException e) {
			fail();
		}
		board.resetBoard();
	}
	
	/**
	 * Function for testing of valid position entered by user.
	 */

	public void testIsValidPosition() {
		List<Player> players4 = Lists.newArrayList();
		players4.add(Player.newPlayer("testPlayer1", Color.BLUE));
		players4.add(Player.newPlayer("testPlayer2", Color.GREEN));
		players4.add(Player.newPlayer("testPlayer3", Color.YELLOW));
		players4.add(Player.newPlayer("testPlayer3", Color.RED));

		BoardBuilder boardBuilder = KingdomBoardBuilder
				.newKingdomBoardBuilder();
		Board board = null;
		try {
			board = boardBuilder.buildBoard(Board.MAX_ROWS, Board.MAX_COLUMNS,
					players4);
		} catch (GameException e) {
			fail();
		}
		boolean isValidCoordinate = board.isValidCoordinate(Board.MAX_ROWS,
				Board.MAX_COLUMNS);
		assertEquals(isValidCoordinate, false);
		isValidCoordinate = board.isValidCoordinate(Board.MAX_ROWS - 1,
				Board.MAX_COLUMNS - 1);
		assertEquals(isValidCoordinate, true);
		board.resetBoard();
	}

}
