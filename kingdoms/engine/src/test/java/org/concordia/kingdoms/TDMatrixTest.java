package org.concordia.kingdoms;

import java.util.List;
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

import com.google.common.collect.Lists;

public class TDMatrixTest extends TestCase {
	// M=Mountain,G=GoldMine,R=ResourceTile,H=HazardTile,C=Castle,W=Wizard
	public void testRHMRHD() {
		TDMatrix matrix = new TDMatrix(5, 6);
		try {
			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "test", 1),
					TDCoordinate.newInstance(0, 0));
			matrix.putComponent(Tile.newTile(TileType.HAZARD, "hazard", -6),
					TDCoordinate.newInstance(0, 1));
			matrix.putComponent(
					Tile.newTile(TileType.MOUNTAIN, "mountain", null),
					TDCoordinate.newInstance(0, 2));

			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "test", 1),
					TDCoordinate.newInstance(0, 3));
			matrix.putComponent(Tile.newTile(TileType.HAZARD, "hazard", -5),
					TDCoordinate.newInstance(0, 4));
			matrix.putComponent(Tile.newTile(TileType.DRAGON, "dragon", null),
					TDCoordinate.newInstance(0, 5));

			List<Integer> scores = Lists.newArrayList();
			matrix.getScore(0, 6, 0, true, scores);
			assertEquals(Integer.valueOf(-5), scores.get(0));
			assertEquals(Integer.valueOf(-5), scores.get(1));

		} catch (GameRuleException e) {
			e.printStackTrace();
			fail();
		}
	}

	public void testRHMRMD() {
		TDMatrix matrix = new TDMatrix(5, 6);
		try {
			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "test", 1),
					TDCoordinate.newInstance(0, 0));
			matrix.putComponent(Tile.newTile(TileType.HAZARD, "hazard", -6),
					TDCoordinate.newInstance(0, 1));
			matrix.putComponent(
					Tile.newTile(TileType.MOUNTAIN, "mountain", null),
					TDCoordinate.newInstance(0, 2));

			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "test", 1),
					TDCoordinate.newInstance(0, 3));
			matrix.putComponent(
					Tile.newTile(TileType.MOUNTAIN, "mountain", null),
					TDCoordinate.newInstance(0, 4));
			matrix.putComponent(Tile.newTile(TileType.DRAGON, "dragon", null),
					TDCoordinate.newInstance(0, 5));

			List<Integer> scores = Lists.newArrayList();
			matrix.getScore(0, 6, 0, true, scores);
			assertEquals(Integer.valueOf(-5), scores.get(0));
			assertEquals(Integer.valueOf(1), scores.get(1));
			assertEquals(Integer.valueOf(0), scores.get(2));
		} catch (GameRuleException e) {
			e.printStackTrace();
			fail();
		}
	}

	public void testMRHRDM() {

		TDMatrix matrix = new TDMatrix(5, 6);
		try {
			matrix.putComponent(
					Tile.newTile(TileType.MOUNTAIN, "mountain", null),
					TDCoordinate.newInstance(0, 0));
			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "resource", 1),
					TDCoordinate.newInstance(0, 1));
			matrix.putComponent(Tile.newTile(TileType.HAZARD, "hazard", -5),
					TDCoordinate.newInstance(0, 2));

			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "test", 6),
					TDCoordinate.newInstance(0, 3));
			matrix.putComponent(Tile.newTile(TileType.DRAGON, "dragon", null),
					TDCoordinate.newInstance(0, 4));
			matrix.putComponent(
					Tile.newTile(TileType.MOUNTAIN, "mountain", null),
					TDCoordinate.newInstance(0, 5));

			List<Integer> scores = Lists.newArrayList();
			matrix.getScore(0, 6, 0, true, scores);
			assertEquals(Integer.valueOf(-5), scores.get(0));
		} catch (GameRuleException e) {
			e.printStackTrace();
			fail();
		}

	}

	public void testMRHRWM() {

		TDMatrix matrix = new TDMatrix(5, 6);
		try {
			matrix.putComponent(
					Tile.newTile(TileType.MOUNTAIN, "mountain", null),
					TDCoordinate.newInstance(0, 0));
			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "resource", 1),
					TDCoordinate.newInstance(0, 1));
			matrix.putComponent(Tile.newTile(TileType.HAZARD, "hazard", -5),
					TDCoordinate.newInstance(0, 2));

			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "test", 6),
					TDCoordinate.newInstance(0, 3));
			matrix.putComponent(Tile.newTile(TileType.WIZARD, "wizard", null),
					TDCoordinate.newInstance(0, 4));
			matrix.putComponent(
					Tile.newTile(TileType.MOUNTAIN, "mountain", null),
					TDCoordinate.newInstance(0, 5));

			List<Integer> scores = Lists.newArrayList();
			matrix.getScore(0, 6, 0, true, scores);
			assertEquals(Integer.valueOf(2), scores.get(0));
		} catch (GameRuleException e) {
			e.printStackTrace();
			fail();
		}

	}

	public void testRRHRDM() {

		TDMatrix matrix = new TDMatrix(5, 6);
		try {
			matrix.putComponent(
					Tile.newTile(TileType.MOUNTAIN, "mountain", null),
					TDCoordinate.newInstance(0, 0));
			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "resource", 1),
					TDCoordinate.newInstance(0, 1));
			matrix.putComponent(Tile.newTile(TileType.HAZARD, "hazard", -5),
					TDCoordinate.newInstance(0, 2));

			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "test", 6),
					TDCoordinate.newInstance(0, 3));
			matrix.putComponent(Tile.newTile(TileType.DRAGON, "dragon", null),
					TDCoordinate.newInstance(0, 4));
			matrix.putComponent(
					Tile.newTile(TileType.MOUNTAIN, "mountain", null),
					TDCoordinate.newInstance(0, 5));

			List<Integer> scores = Lists.newArrayList();
			matrix.getScore(0, 6, 0, true, scores);
			assertEquals(Integer.valueOf(-5), scores.get(0));
		} catch (GameRuleException e) {
			e.printStackTrace();
			fail();
		}

	}

	public void testMRHRDR() {

		TDMatrix matrix = new TDMatrix(5, 6);
		try {
			matrix.putComponent(
					Tile.newTile(TileType.MOUNTAIN, "mountain", null),
					TDCoordinate.newInstance(0, 0));
			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "resource", 1),
					TDCoordinate.newInstance(0, 1));
			matrix.putComponent(Tile.newTile(TileType.HAZARD, "hazard", -5),
					TDCoordinate.newInstance(0, 2));

			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "test", 6),
					TDCoordinate.newInstance(0, 3));
			matrix.putComponent(Tile.newTile(TileType.DRAGON, "dragon", null),
					TDCoordinate.newInstance(0, 4));
			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "resource", 5),
					TDCoordinate.newInstance(0, 5));

			List<Integer> scores = Lists.newArrayList();
			matrix.getScore(0, 6, 0, true, scores);
			assertEquals(Integer.valueOf(-5), scores.get(0));
		} catch (GameRuleException e) {
			e.printStackTrace();
			fail();
		}

	}

	public void testMGRMDH() {

		TDMatrix matrix = new TDMatrix(5, 6);
		try {
			matrix.putComponent(
					Tile.newTile(TileType.MOUNTAIN, "mountain", null),
					TDCoordinate.newInstance(0, 0));
			matrix.putComponent(
					Tile.newTile(TileType.GOLDMINE, "goldmine", null),
					TDCoordinate.newInstance(0, 1));
			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "resource", 1),
					TDCoordinate.newInstance(0, 2));

			matrix.putComponent(
					Tile.newTile(TileType.MOUNTAIN, "mountain", null),
					TDCoordinate.newInstance(0, 3));
			matrix.putComponent(Tile.newTile(TileType.DRAGON, "dragon", null),
					TDCoordinate.newInstance(0, 4));
			matrix.putComponent(Tile.newTile(TileType.HAZARD, "hazard", -5),
					TDCoordinate.newInstance(0, 5));

			List<Integer> scores = Lists.newArrayList();
			matrix.getScore(0, 6, 0, true, scores);
			assertEquals(Integer.valueOf(2), scores.get(0));
			assertEquals(Integer.valueOf(-5), scores.get(1));
		} catch (GameRuleException e) {
			e.printStackTrace();
			fail();
		}

	}

	public void testMGRHDH() {

		TDMatrix matrix = new TDMatrix(5, 6);
		try {
			matrix.putComponent(
					Tile.newTile(TileType.MOUNTAIN, "mountain", null),
					TDCoordinate.newInstance(0, 0));
			matrix.putComponent(
					Tile.newTile(TileType.GOLDMINE, "goldmine", null),
					TDCoordinate.newInstance(0, 1));
			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "resource", 1),
					TDCoordinate.newInstance(0, 2));

			matrix.putComponent(Tile.newTile(TileType.HAZARD, "hazard", -1),
					TDCoordinate.newInstance(0, 3));
			matrix.putComponent(Tile.newTile(TileType.DRAGON, "dragon", null),
					TDCoordinate.newInstance(0, 4));
			matrix.putComponent(Tile.newTile(TileType.HAZARD, "hazard", -5),
					TDCoordinate.newInstance(0, 5));

			List<Integer> scores = Lists.newArrayList();
			matrix.getScore(0, 6, 0, true, scores);

			assertEquals(Integer.valueOf(-12), scores.get(0));
		} catch (GameRuleException e) {
			e.printStackTrace();
			fail();
		}

	}

	public void testMMRRHH() {

		TDMatrix matrix = new TDMatrix(5, 6);
		try {
			matrix.putComponent(
					Tile.newTile(TileType.MOUNTAIN, "mountain", null),
					TDCoordinate.newInstance(0, 0));
			matrix.putComponent(
					Tile.newTile(TileType.MOUNTAIN, "mountain", null),
					TDCoordinate.newInstance(0, 1));
			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "resource", 1),
					TDCoordinate.newInstance(0, 2));

			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "resource", 2),
					TDCoordinate.newInstance(0, 3));
			matrix.putComponent(Tile.newTile(TileType.HAZARD, "hazard", -1),
					TDCoordinate.newInstance(0, 4));
			matrix.putComponent(Tile.newTile(TileType.HAZARD, "hazard", -2),
					TDCoordinate.newInstance(0, 5));

			List<Integer> scores = Lists.newArrayList();
			matrix.getScore(0, 6, 0, true, scores);

			assertEquals(Integer.valueOf(0), scores.get(0));
		} catch (GameRuleException e) {
			e.printStackTrace();
			fail();
		}

	}

	public void testRCCCHC() {

		TDMatrix matrix = new TDMatrix(5, 6);
		try {
			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "resource", 3),
					TDCoordinate.newInstance(0, 0));
			matrix.putComponent(Castle.newCastle(1, Color.RED),
					TDCoordinate.newInstance(0, 1));
			matrix.putComponent(Castle.newCastle(3, Color.YELLOW),
					TDCoordinate.newInstance(0, 2));

			matrix.putComponent(Castle.newCastle(1, Color.BLUE),
					TDCoordinate.newInstance(0, 3));
			matrix.putComponent(Tile.newTile(TileType.HAZARD, "hazard", -6),
					TDCoordinate.newInstance(0, 4));
			matrix.putComponent(Castle.newCastle(1, Color.YELLOW),
					TDCoordinate.newInstance(0, 5));

			List<Integer> scores = Lists.newArrayList();
			matrix.getScore(0, 6, 0, true, scores);

			assertEquals(Integer.valueOf(-3), scores.get(0));
		} catch (GameRuleException e) {
			e.printStackTrace();
			fail();
		}

	}

	public void testCGRRMW() {

		TDMatrix matrix = new TDMatrix(5, 6);
		try {
			matrix.putComponent(Castle.newCastle(2, Color.BLUE),
					TDCoordinate.newInstance(1, 0));
			matrix.putComponent(
					Tile.newTile(TileType.GOLDMINE, "goldmine", null),
					TDCoordinate.newInstance(1, 1));
			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "resource", 4),
					TDCoordinate.newInstance(1, 2));

			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "resource", 1),
					TDCoordinate.newInstance(1, 3));
			matrix.putComponent(
					Tile.newTile(TileType.MOUNTAIN, "mountain", null),
					TDCoordinate.newInstance(1, 4));
			matrix.putComponent(Tile.newTile(TileType.WIZARD, "wizard", null),
					TDCoordinate.newInstance(1, 5));

			List<Integer> scores = Lists.newArrayList();
			matrix.getScore(0, 6, 1, true, scores);

			assertEquals(Integer.valueOf(10), scores.get(0));
			assertEquals(Integer.valueOf(0), scores.get(1));
		} catch (GameRuleException e) {
			e.printStackTrace();
			fail();
		}

	}

	public void testRRCHHC() {

		TDMatrix matrix = new TDMatrix(5, 6);
		try {
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

			List<Integer> scores = Lists.newArrayList();
			matrix.getScore(0, 6, 2, true, scores);

			assertEquals(Integer.valueOf(-1), scores.get(0));
		} catch (GameRuleException e) {
			e.printStackTrace();
			fail();
		}

	}

	public void testCDRMCR() {
		TDMatrix matrix = new TDMatrix(5, 6);
		try {
			matrix.putComponent(Castle.newCastle(2, Color.YELLOW),
					TDCoordinate.newInstance(3, 0));
			matrix.putComponent(Tile.newTile(TileType.DRAGON, "dragon", null),
					TDCoordinate.newInstance(3, 1));

			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "resource", 3),
					TDCoordinate.newInstance(3, 2));
			matrix.putComponent(
					Tile.newTile(TileType.MOUNTAIN, "mountain", null),
					TDCoordinate.newInstance(3, 3));
			matrix.putComponent(Castle.newCastle(1, Color.BLUE),
					TDCoordinate.newInstance(3, 4));
			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "resource", 5),
					TDCoordinate.newInstance(3, 5));
			List<Integer> scores = Lists.newArrayList();
			matrix.getScore(0, 6, 3, true, scores);

			assertEquals(Integer.valueOf(0), scores.get(0));
			assertEquals(Integer.valueOf(5), scores.get(1));
		} catch (GameRuleException e) {
			e.printStackTrace();
			fail();
		}

	}

	public void testHHCCRR() {

		TDMatrix matrix = new TDMatrix(5, 6);
		try {
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
			List<Integer> scores = Lists.newArrayList();
			matrix.getScore(0, 6, 4, true, scores);
			assertEquals(Integer.valueOf(4), scores.get(0));
		} catch (GameRuleException e) {
			e.printStackTrace();
			fail();
		}

	}

	public void testColumnRCRCH() {

		TDMatrix matrix = new TDMatrix(5, 6);
		try {
			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "resource", 3),
					TDCoordinate.newInstance(0, 0));
			matrix.putComponent(Castle.newCastle(2, Color.BLUE),
					TDCoordinate.newInstance(1, 0));
			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "resource", 2),
					TDCoordinate.newInstance(2, 0));
			matrix.putComponent(Castle.newCastle(2, Color.YELLOW),
					TDCoordinate.newInstance(3, 0));
			matrix.putComponent(Tile.newTile(TileType.HAZARD, "hazard", -1),
					TDCoordinate.newInstance(4, 0));
			List<Integer> scores = Lists.newArrayList();
			matrix.getScore(0, 5, 0, false, scores);
			assertEquals(Integer.valueOf(4), scores.get(0));
		} catch (GameRuleException e) {
			e.printStackTrace();
			fail();
		}

	}

	public void testColumnCGRDH() {
		TDMatrix matrix = new TDMatrix(5, 6);
		try {
			matrix.putComponent(Castle.newCastle(2, Color.RED),
					TDCoordinate.newInstance(0, 1));
			matrix.putComponent(
					Tile.newTile(TileType.GOLDMINE, "goldmine", null),
					TDCoordinate.newInstance(1, 1));
			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "resource", 4),
					TDCoordinate.newInstance(2, 1));
			matrix.putComponent(Tile.newTile(TileType.DRAGON, "dragon", null),
					TDCoordinate.newInstance(3, 1));
			matrix.putComponent(Tile.newTile(TileType.HAZARD, "hazard", -3),
					TDCoordinate.newInstance(4, 1));
			List<Integer> scores = Lists.newArrayList();
			matrix.getScore(0, 5, 1, false, scores);

			assertEquals(Integer.valueOf(-6), scores.get(0));
		} catch (GameRuleException e) {
			e.printStackTrace();
			fail();
		}

	}

	public void testColumnCRCRC() {

		TDMatrix matrix = new TDMatrix(5, 6);
		try {
			matrix.putComponent(Castle.newCastle(4, Color.YELLOW),
					TDCoordinate.newInstance(0, 2));
			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "resource", 4),
					TDCoordinate.newInstance(1, 2));
			matrix.putComponent(Castle.newCastle(2, Color.RED),
					TDCoordinate.newInstance(2, 2));
			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "RESOURCE", 3),
					TDCoordinate.newInstance(3, 2));
			matrix.putComponent(Castle.newCastle(1, Color.BLUE),
					TDCoordinate.newInstance(4, 2));
			List<Integer> scores = Lists.newArrayList();
			matrix.getScore(0, 5, 2, false, scores);
			assertEquals(Integer.valueOf(7), scores.get(0));
		} catch (GameRuleException e) {
			e.printStackTrace();
			fail();
		}

	}

	public void testColumnCRHMC() {

		TDMatrix matrix = new TDMatrix(5, 6);
		try {
			matrix.putComponent(Castle.newCastle(1, Color.BLUE),
					TDCoordinate.newInstance(0, 3));
			matrix.putComponent(Tile.newTile(TileType.RESOURCE, "resource", 1),
					TDCoordinate.newInstance(1, 3));
			matrix.putComponent(Tile.newTile(TileType.HAZARD, "hazard", -5),
					TDCoordinate.newInstance(2, 3));
			matrix.putComponent(
					Tile.newTile(TileType.MOUNTAIN, "mountain", null),
					TDCoordinate.newInstance(3, 3));
			matrix.putComponent(Castle.newCastle(3, Color.YELLOW),
					TDCoordinate.newInstance(4, 3));
			List<Integer> scores = Lists.newArrayList();
			matrix.getScore(0, 5, 3, false, scores);
			assertEquals(Integer.valueOf(-4), scores.get(0));
			assertEquals(Integer.valueOf(0), scores.get(1));
		} catch (GameRuleException e) {
			e.printStackTrace();
			fail();
		}

	}

	public void testColumnHMHCR() {
		TDMatrix matrix = new TDMatrix(5, 6);
		try {
			matrix.putComponent(Tile.newTile(TileType.HAZARD, "hazard", -6),
					TDCoordinate.newInstance(0, 4));
			matrix.putComponent(
					Tile.newTile(TileType.MOUNTAIN, "mountain", null),
					TDCoordinate.newInstance(1, 4));
			matrix.putComponent(Tile.newTile(TileType.HAZARD, "hazard", -2),
					TDCoordinate.newInstance(2, 4));
			matrix.putComponent(Castle.newCastle(1, Color.BLUE),
					TDCoordinate.newInstance(3, 4));
			matrix.putComponent(
					Tile.newTile(TileType.RESOURCE, "resource", +6),
					TDCoordinate.newInstance(4, 4));
			List<Integer> scores = Lists.newArrayList();
			matrix.getScore(0, 5, 4, false, scores);
			assertEquals(Integer.valueOf(-6), scores.get(0));
			assertEquals(Integer.valueOf(4), scores.get(1));
		} catch (GameRuleException e) {
			e.printStackTrace();
			fail();
		}

	}

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

			List<Integer> scores = Lists.newArrayList();

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

}
