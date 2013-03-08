package org.concordia.kingdoms;

import java.util.List;

import junit.framework.TestCase;

import org.concordia.kingdoms.board.Board;
import org.concordia.kingdoms.board.TDCoordinate;
import org.concordia.kingdoms.board.factory.BoardBuilder;
import org.concordia.kingdoms.board.factory.TDBoardBuilder;
import org.concordia.kingdoms.domain.Color;
import org.concordia.kingdoms.exceptions.GameException;

import com.google.common.collect.Lists;

public class CastlesPerPlayerTest extends TestCase {
	/**
	 * @formatter:off
	 * Number of Players Rank 1 Castles Rank 2, 3, & 4 Castles
		2 					4 				all
		3 					3 				all
		4 					2 				all
	 */
	public void testPlayerHasLimitedCastlesForEachRank() {

		List<Player<TDCoordinate>> players2 = Lists.newArrayList();
		players2.add(Player.newPlayer("testPlayer1", Color.BLUE));
		players2.add(Player.newPlayer("testPlayer2", Color.RED));
		
		testPlayerAsssignedEnoughCastles(4,players2);
		
		
		List<Player<TDCoordinate>> players3 = Lists.newArrayList();
		players3.add(Player.newPlayer("testPlayer1", Color.BLUE));
		players3.add(Player.newPlayer("testPlayer2", Color.GREEN));
		players3.add(Player.newPlayer("testPlayer3", Color.YELLOW));
		
		testPlayerAsssignedEnoughCastles(3, players3);
		
		List<Player<TDCoordinate>> players4 = Lists.newArrayList();
		players4.add(Player.newPlayer("testPlayer1", Color.BLUE));
		players4.add(Player.newPlayer("testPlayer2", Color.GREEN));
		players4.add(Player.newPlayer("testPlayer3", Color.YELLOW));
		players4.add(Player.newPlayer("testPlayer3", Color.RED));
		
		testPlayerAsssignedEnoughCastles(2,players4);
	}

	private void testPlayerAsssignedEnoughCastles(int rankOneCastles, List<Player<TDCoordinate>> players) {

		BoardBuilder<TDCoordinate> boardBuilder = new TDBoardBuilder();
		Board<TDCoordinate> board = null;

		try {
			board = boardBuilder
					.buildBoard(TDCoordinate.newInstance(board.MAX_ROWS,
							board.MAX_COLUMNS), players);


			// trying to put all the castles on the board sequentially starting
			// from rank 2 3 4 and 1 on the board

			final Player<TDCoordinate> testPlayer1 = players.get(0);
			// test player has 3 rank 2 castles
			for (int i = 0; i < 3; i++) {
				testPlayer1.putCastle(testPlayer1.getCastle(2), TDCoordinate.newInstance(0, i));
			}
			assertNull(testPlayer1.getCastle(2));

			// test player has 2 rank 3 castles
			for (int i = 0; i < 2; i++) {
				testPlayer1.putCastle(testPlayer1.getCastle(3), TDCoordinate.newInstance(0, i + 3));
			}
			assertNull(testPlayer1.getCastle(3));

			// test player has 2 rank 4 castles
			testPlayer1.putCastle(testPlayer1.getCastle(4), TDCoordinate.newInstance(0, 5));
			assertNull(testPlayer1.getCastle(4));

			// test player has 4 rank 1 castles since there are only 2 players playing
			for (int i = 0; i < rankOneCastles; i++) {
				testPlayer1.putCastle(testPlayer1.getCastle(1), TDCoordinate.newInstance(1, i));
				
			}
			assertNull(testPlayer1.getCastle(1));
			
		} catch (GameException e) {
			fail();
		}
		board.resetBoard();
	}

}
