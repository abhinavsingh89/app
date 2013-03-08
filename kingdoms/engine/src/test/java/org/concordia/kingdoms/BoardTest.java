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

import com.google.common.collect.Lists;

public class BoardTest extends TestCase {

	public void testHasAnyEmptySpace() {
		BoardBuilder<TDCoordinate> boardBuilder = new TDBoardBuilder();
		Board<TDCoordinate> board = null;

		List<Player<TDCoordinate>> players4 = Lists.newArrayList();
		players4.add(Player.newPlayer("testPlayer1", Color.BLUE));
		players4.add(Player.newPlayer("testPlayer2", Color.GREEN));
		players4.add(Player.newPlayer("testPlayer3", Color.YELLOW));
		players4.add(Player.newPlayer("testPlayer3", Color.RED));

		try {
			board = boardBuilder
					.buildBoard(TDCoordinate.newInstance(board.MAX_ROWS,
							board.MAX_COLUMNS), players4);
			Player<TDCoordinate> player2 = players4.get(1);
			players4.get(1).putCastle(player2.getCastle(2),
					TDCoordinate.newInstance(0, 0));
			try {
				players4.get(1).putCastle(player2.getCastle(1),
						TDCoordinate.newInstance(0, 0));
				fail();
			} catch (GameRuleException ex) {

			}
		} catch (GameException e) {
			fail();
		}
		board.resetBoard();
	}

	public void testIsEntryEmpty() {
		BoardBuilder<TDCoordinate> boardBuilder = new TDBoardBuilder();
		Board<TDCoordinate> board = null;

		List<Player<TDCoordinate>> players4 = Lists.newArrayList();
		players4.add(Player.newPlayer("testPlayer1", Color.BLUE));
		players4.add(Player.newPlayer("testPlayer2", Color.GREEN));
		players4.add(Player.newPlayer("testPlayer3", Color.YELLOW));
		players4.add(Player.newPlayer("testPlayer3", Color.RED));

		try {
			board = boardBuilder
					.buildBoard(TDCoordinate.newInstance(board.MAX_ROWS,
							board.MAX_COLUMNS), players4);
			Player<TDCoordinate> player2 = players4.get(1);
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
		board.resetBoard();
	}

	public void testIsValidPosition() {
		List<Player<TDCoordinate>> players4 = Lists.newArrayList();
		players4.add(Player.newPlayer("testPlayer1", Color.BLUE));
		players4.add(Player.newPlayer("testPlayer2", Color.GREEN));
		players4.add(Player.newPlayer("testPlayer3", Color.YELLOW));
		players4.add(Player.newPlayer("testPlayer3", Color.RED));

		BoardBuilder<TDCoordinate> boardBuilder = new TDBoardBuilder();
		Board<TDCoordinate> board = null;
		try {

			board = boardBuilder
					.buildBoard(TDCoordinate.newInstance(board.MAX_ROWS,
							board.MAX_COLUMNS), players4);
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
