//package org.concordia.kingdoms.ui;
//
//import java.io.IOException;
//import java.util.List;
//
//import junit.framework.TestCase;
//
//import org.concordia.kingdoms.Kingdoms;
//import org.concordia.kingdoms.Player;
//import org.concordia.kingdoms.board.Board;
//import org.concordia.kingdoms.board.DisasterType;
//import org.concordia.kingdoms.board.IDisaster;
//import org.concordia.kingdoms.board.TDCoordinate;
//import org.concordia.kingdoms.board.factory.BoardBuilder;
//import org.concordia.kingdoms.board.factory.TDBoardBuilder;
//import org.concordia.kingdoms.domain.Color;
//import org.concordia.kingdoms.domain.TileType;
//import org.concordia.kingdoms.exceptions.GameException;
//import org.concordia.kingdoms.exceptions.GameRuleException;
//
//import com.google.common.collect.Lists;
//import com.google.common.collect.Sets;
//
//public class EarthQuakeTest extends TestCase {
//
//	public void testEarthQuakeRemovesEligibleComponentFromBoard() {
//		List<Player<TDCoordinate>> players4 = Lists.newArrayList();
//		players4.add(Player.<TDCoordinate> newPlayer("testPlayer1", Color.BLUE));
//		players4.add(Player
//				.<TDCoordinate> newPlayer("testPlayer2", Color.GREEN));
//		players4.add(Player.<TDCoordinate> newPlayer("testPlayer3",
//				Color.YELLOW));
//		players4.add(Player.<TDCoordinate> newPlayer("testPlayer3", Color.RED));
//
//		BoardBuilder boardBuilder = new TDBoardBuilder();
//		try {
//			Kingdoms<TDCoordinate> kingdoms = new TDKingdoms(boardBuilder, 6) {
//				@Override
//				protected List<IDisaster<TDCoordinate>> getDisasters() {
//					return Lists.newArrayList();
//				}
//			};
//			try {
//				kingdoms.start(players4);
//			} catch (GameException e) {
//				e.printStackTrace();
//				fail();
//			}
//			Player<TDCoordinate> player = players4.get(0);
//			Board<TDCoordinate> board = player.getBoard();
//			TDCoordinate availableCoordinate = board.getAvailableCoordinates()
//					.get(0);
//			try {
//				assertEquals(3, board.getComponentsOnBoard());
//				board.putComponent(player.drawTopTile(), availableCoordinate);
//				assertEquals(4, board.getComponentsOnBoard());
//				player.getBoard().returnComponent(
//						Sets.newHashSet(availableCoordinate),
//						Lists.newArrayList(TileType.DRAGON, TileType.GOLDMINE,
//								TileType.MOUNTAIN), DisasterType.EARTHQUAKE);
//
//				board.putComponent(player.drawTopTile(), availableCoordinate);
//				assertEquals(4, board.getComponentsOnBoard());
//
//			} catch (GameRuleException e) {
//				e.printStackTrace();
//				fail();
//			}
//
//		} catch (IOException e) {
//			fail();
//			e.printStackTrace();
//		}
//	}
//
//}
