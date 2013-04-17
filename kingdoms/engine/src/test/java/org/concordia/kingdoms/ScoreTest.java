package org.concordia.kingdoms;

import java.util.Map;

import junit.framework.TestCase;

import org.concordia.kingdoms.board.Score;
import org.concordia.kingdoms.board.TDCoordinate;
import org.concordia.kingdoms.board.TDMatrix;
import org.concordia.kingdoms.domain.Castle;
import org.concordia.kingdoms.domain.Color;
import org.concordia.kingdoms.domain.Tile;
import org.concordia.kingdoms.domain.TileType;
import org.concordia.kingdoms.exceptions.GameRuleException;


public class ScoreTest extends TestCase{
	/**
	 * Function for testing full board score
	 */
	public void testFullBoardScore() {

		TDMatrix matrix = new TDMatrix(5, 6);

		try {

			// 0th row

			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "resource", 3),

			TDCoordinate.newInstance(0, 0));

			matrix.putComponent(Castle.newCastle(1, Color.RED),

			TDCoordinate.newInstance(0, 1));

			matrix.putComponent(Castle.newCastle(4, Color.YELLOW),

			TDCoordinate.newInstance(0, 2));

			matrix.putComponent(Castle.newCastle(1, Color.BLUE),

			TDCoordinate.newInstance(0, 3));

			matrix.putComponent(Tile.newTile(TileType.HAZARD, "HAZARD", -6),

			TDCoordinate.newInstance(0, 4));

			matrix.putComponent(Castle.newCastle(1, Color.YELLOW),

			TDCoordinate.newInstance(0, 5));

			// 1st row

			matrix.putComponent(Castle.newCastle(2, Color.BLUE),

			TDCoordinate.newInstance(1, 0));

			matrix.putComponent(

			Tile.newTile(TileType.GOLDMINE, "GOLDMINE", null),

			TDCoordinate.newInstance(1, 1));

			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "RESOURCE", 4),

			TDCoordinate.newInstance(1, 2));

			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "RESOURCE", 1),

			TDCoordinate.newInstance(1, 3));

			matrix.putComponent(

			Tile.newTile(TileType.MOUNTAIN, "MOUNTAIN", null),

			TDCoordinate.newInstance(1, 4));

			matrix.putComponent(Tile.newTile(TileType.WIZARD, "WIZARD", null),

			TDCoordinate.newInstance(1, 5));
			// 2nd row
			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "resource", 2),
					TDCoordinate.newInstance(2, 0));
			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "resource", 4),
					TDCoordinate.newInstance(2, 1));
			matrix.putComponent(Castle.newCastle(2, Color.RED),
					TDCoordinate.newInstance(2, 2));

			matrix.putComponent(Tile.newTile(TileType.HAZARD, "hazard", -5),
					TDCoordinate.newInstance(2, 3));
			matrix.putComponent(Tile.newTile(TileType.HAZARD, "hazard", -2),
					TDCoordinate.newInstance(2, 4));
			matrix.putComponent(Castle.newCastle(1, Color.BLUE),
					TDCoordinate.newInstance(2, 5));
			// 3rd row

			matrix.putComponent(Castle.newCastle(2, Color.YELLOW),

			TDCoordinate.newInstance(3, 0));

			matrix.putComponent(Tile.newTile(TileType.DRAGON, "DRAGON", null),

			TDCoordinate.newInstance(3, 1));

			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "RESOURCE", 3),

			TDCoordinate.newInstance(3, 2));

			matrix.putComponent(

			Tile.newTile(TileType.MOUNTAIN, "MOUNTAIN", null),

			TDCoordinate.newInstance(3, 3));

			matrix.putComponent(Castle.newCastle(1, Color.BLUE),

			TDCoordinate.newInstance(3, 4));

			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "RESOURCE", 5),

			TDCoordinate.newInstance(3, 5));
			// 4th row

			matrix.putComponent(Tile.newTile(TileType.HAZARD, "hazard", -1),
					TDCoordinate.newInstance(4, 0));
			matrix.putComponent(Tile.newTile(TileType.HAZARD, "hazard", -3),
					TDCoordinate.newInstance(4, 1));

			matrix.putComponent(Castle.newCastle(1, Color.BLUE),
					TDCoordinate.newInstance(4, 2));
			matrix.putComponent(Castle.newCastle(3, Color.YELLOW),
					TDCoordinate.newInstance(4, 3));
			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "resource", 6),
					TDCoordinate.newInstance(4, 4));
			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "resource", 2),
					TDCoordinate.newInstance(4, 5));

			Map<Color, Score> finalScore = matrix.score();

			Score blueScore = finalScore.get(Color.BLUE);

			assertEquals(blueScore.getRowScore(), 24);

			assertEquals(blueScore.getColumnScore(), 29);

			assertEquals(blueScore.score(), 53);

			Score yellowScore = finalScore.get(Color.YELLOW);

			assertEquals(yellowScore.getRowScore(), -6);

			assertEquals(yellowScore.getColumnScore(), 50);

			assertEquals(yellowScore.score(), 44);
			Score redScore = finalScore.get(Color.RED);

			assertEquals(redScore.getRowScore(), -5);

			assertEquals(redScore.getColumnScore(), 8);

			assertEquals(redScore.score(), 3);

		} catch (GameRuleException e) {

			e.printStackTrace();

			fail();

		}

	}

	public void testFullBoardScore1() {
		TDMatrix matrix = new TDMatrix(5, 6);

		try {

			// 0th row

			matrix.putComponent(Castle.newCastle(1, Color.BLUE),
					TDCoordinate.newInstance(0, 0));

			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "RESOURCE", 2),

			TDCoordinate.newInstance(0, 1));

			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "RESOURCE", 3),

			TDCoordinate.newInstance(0, 2));

			matrix.putComponent(Tile.newTile(TileType.HAZARD, "HAZARD", -1),
					TDCoordinate.newInstance(0, 3));

			matrix.putComponent(Tile.newTile(TileType.HAZARD, "HAZARD", -2),

			TDCoordinate.newInstance(0, 4));

			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "RESOURCE", 3),

			TDCoordinate.newInstance(0, 5));

			// 1st row

			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "RESOURCE", 5),

			TDCoordinate.newInstance(1, 0));

			matrix.putComponent(Tile.newTile(TileType.HAZARD, "hazard", -1),

			TDCoordinate.newInstance(1, 1));

			matrix.putComponent(Tile.newTile(TileType.HAZARD, "hazard", -1),

			TDCoordinate.newInstance(1, 2));

			matrix.putComponent(Tile.newTile(TileType.HAZARD, "hazard", -3),

			TDCoordinate.newInstance(1, 3));

			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "RESOURCE", 2),

			TDCoordinate.newInstance(1, 4));

			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "RESOURCE", 1),

			TDCoordinate.newInstance(1, 5));
			// 2nd row
			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "resource", 6),
					TDCoordinate.newInstance(2, 0));
			matrix.putComponent(Castle.newCastle(1, Color.RED),
					TDCoordinate.newInstance(2, 1));
			matrix.putComponent(Tile.newTile(TileType.HAZARD, "hazard", -2),
					TDCoordinate.newInstance(2, 2));

			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "resource", 1),
					TDCoordinate.newInstance(2, 3));
			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "resource", 1),
					TDCoordinate.newInstance(2, 4));
			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "resource", 4),
					TDCoordinate.newInstance(2, 5));
			// 3rd row

			matrix.putComponent(Tile.newTile(TileType.HAZARD, "hazard", -6),

			TDCoordinate.newInstance(3, 0));

			matrix.putComponent(Tile.newTile(TileType.HAZARD, "hazard", -3),

			TDCoordinate.newInstance(3, 1));

			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "RESOURCE", 2),

			TDCoordinate.newInstance(3, 2));


			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "RESOURCE", 3),

			TDCoordinate.newInstance(3, 3));

			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "RESOURCE", 3),

			TDCoordinate.newInstance(3, 4));

			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "RESOURCE", 3),

			TDCoordinate.newInstance(3, 5));
			// 4th row

			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "RESOURCE", 4),
					TDCoordinate.newInstance(4, 0));
			matrix.putComponent(Tile.newTile(TileType.HAZARD, "hazard", -4),
					TDCoordinate.newInstance(4, 1));

			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "RESOURCE", 1),
					TDCoordinate.newInstance(4, 2));
			matrix.putComponent(Tile.newTile(TileType.HAZARD, "hazard", -4),
					TDCoordinate.newInstance(4, 3));
			matrix.putComponent(Tile.newTile(TileType.HAZARD, "hazard", -1),
					TDCoordinate.newInstance(4, 4));
			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "resource", 2),
					TDCoordinate.newInstance(4, 5));

			Map<Color, Score> finalScore = matrix.score();

			Score blueScore = finalScore.get(Color.BLUE);

			assertEquals(blueScore.getRowScore(), 5);

			assertEquals(blueScore.getColumnScore(), 9);

			assertEquals(blueScore.score(), 14);

						Score redScore = finalScore.get(Color.RED);

			assertEquals(redScore.getRowScore(), 10);

			assertEquals(redScore.getColumnScore(), -6);

			assertEquals(redScore.score(), 4);

		} catch (GameRuleException e) {

			e.printStackTrace();

			fail();

		}

	}

}
