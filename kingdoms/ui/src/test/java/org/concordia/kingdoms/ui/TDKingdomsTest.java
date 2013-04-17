                                                                     
                                                                     
                                                                     
                                             
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
import org.concordia.kingdoms.board.Board;
import org.concordia.kingdoms.board.DisasterType;
import org.concordia.kingdoms.board.IDisaster;
import org.concordia.kingdoms.board.TDCoordinate;
import org.concordia.kingdoms.board.factory.TDBoardBuilder;
import org.concordia.kingdoms.domain.Color;
import org.concordia.kingdoms.domain.TileType;
import org.concordia.kingdoms.exceptions.GameException;
import org.concordia.kingdoms.exceptions.GameRuleException;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class TDKingdomsTest extends TestCase {
	static List<Player<TDCoordinate>> players4;
	static Kingdoms<TDCoordinate> kingdoms;

	static {
		players4 = Lists.newArrayList();
		players4.add(Player.<TDCoordinate> newPlayer("testPlayer1", Color.BLUE));
		players4.add(Player
				.<TDCoordinate> newPlayer("testPlayer2", Color.GREEN));
		players4.add(Player.<TDCoordinate> newPlayer("testPlayer3",
				Color.YELLOW));
		players4.add(Player.<TDCoordinate> newPlayer("testPlayer3", Color.RED));

		try {
			kingdoms = new TDKingdoms(new TDBoardBuilder(), 6) {
				@Override
				protected List<IDisaster<TDCoordinate>> getDisasters() {
					return Lists.newArrayList();
				}
			};
			try {
				kingdoms.start(players4);
			} catch (GameException e) {
				fail();
			}
		} catch (IOException e) {
			fail();
		}
	}

	public void testHasThreeSpecialTilesOnBoard() {
		Player<TDCoordinate> player = kingdoms.getPlayers().get(0);
		int componentsOnBoard = player.getBoard().getComponentsOnBoard();
		assertEquals(3, componentsOnBoard);
	}

	public void testEarthQuakeReturnsComponent() {
		Player<TDCoordinate> player = kingdoms.getPlayers().get(0);
		Board<TDCoordinate> board = player.getBoard();
		List<TDCoordinate> availableCoordinates = board
				.getAvailableCoordinates();
		TDCoordinate availableCoordinate = availableCoordinates.get(0);
		int componentsOnBoard = board.getComponentsOnBoard();
		assertEquals(3, componentsOnBoard);
		try {
			player.putCastle(player.getCastle(1), availableCoordinate);
			componentsOnBoard = board.getComponentsOnBoard();
			assertEquals(4, componentsOnBoard);
		} catch (GameRuleException e) {
			fail();
		}

		board.returnComponent(Sets.newHashSet(availableCoordinate), Lists
				.newArrayList(TileType.DRAGON, TileType.GOLDMINE,
						TileType.WIZARD), DisasterType.EARTHQUAKE);
		componentsOnBoard = board.getComponentsOnBoard();
		assertEquals(3, componentsOnBoard);

	}

	public void testMudslideDoesNotRemoveComponentFromBoard() {

		Player<TDCoordinate> player = kingdoms.getPlayers().get(0);
		Board<TDCoordinate> board = player.getBoard();
		List<TDCoordinate> availableCoordinates = board
				.getAvailableCoordinates();
		TDCoordinate availableCoordinate = availableCoordinates.get(0);
		int componentsOnBoard = board.getComponentsOnBoard();
		assertEquals(3, componentsOnBoard);
		try {
			player.putTile(player.drawTopTile(), availableCoordinate);
			componentsOnBoard = board.getComponentsOnBoard();
			assertEquals(4, componentsOnBoard);
		} catch (GameRuleException e) {
			fail();
		}

		try {
			board.shuffle(Sets.newHashSet(availableCoordinate),
					DisasterType.MUDSLIDE);
		} catch (GameRuleException e) {
			fail();
		}
		componentsOnBoard = board.getComponentsOnBoard();
		assertEquals(4, componentsOnBoard);
		board.returnComponent(Sets.newHashSet(availableCoordinate), Lists
				.newArrayList(TileType.DRAGON, TileType.GOLDMINE,
						TileType.WIZARD), DisasterType.EARTHQUAKE);
		componentsOnBoard = board.getComponentsOnBoard();
		assertEquals(3, componentsOnBoard);
	}

	public void testPlayerPossessThreeTiles() {
		Player<TDCoordinate> player = kingdoms.getPlayers().get(0);
		int totalPossessedTiles = player.getPossessedTiles().size();
		assertEquals(3, totalPossessedTiles);
	}

	public void testEarthQuakeDoesnotEffectSpecialTiles() {

		Player<TDCoordinate> player = kingdoms.getPlayers().get(0);
		Board<TDCoordinate> board = player.getBoard();
		List<TDCoordinate> availableCoordinates = board
				.getAvailableCoordinates();
		int componentsOnBoard = board.getComponentsOnBoard();
		assertEquals(3, componentsOnBoard);

		board.returnComponent(Sets.newHashSet(availableCoordinates), Lists
				.newArrayList(TileType.DRAGON, TileType.GOLDMINE,
						TileType.WIZARD), DisasterType.EARTHQUAKE);
		assertEquals(3, componentsOnBoard);
	}
}