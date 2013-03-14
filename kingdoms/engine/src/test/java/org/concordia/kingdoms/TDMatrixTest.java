package org.concordia.kingdoms;

import java.util.List;

import junit.framework.TestCase;

import org.concordia.kingdoms.board.TDCoordinate;
import org.concordia.kingdoms.board.TDMatrix;
import org.concordia.kingdoms.domain.Castle;
import org.concordia.kingdoms.domain.Color;
import org.concordia.kingdoms.domain.Tile;
import org.concordia.kingdoms.domain.TileType;
import org.concordia.kingdoms.exceptions.GameRuleException;

import com.google.common.collect.Lists;

public class TDMatrixTest extends TestCase {

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
			matrix.putComponent(
					Tile.newTile(TileType.DRAGON, "dragon", null),
					TDCoordinate.newInstance(0, 5));

			List<Integer> scores = Lists.newArrayList();
			matrix.getScore(0, 6, 0, true, scores);
			assertEquals(Integer.valueOf(-5),scores.get(0));
			assertEquals(Integer.valueOf(-5),scores.get(1));
			
		} catch (GameRuleException e) {
			e.printStackTrace();
			fail();
		}
	}
public void testRHMRMD()
{
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
		matrix.putComponent(Tile.newTile(TileType.MOUNTAIN, "mountain",null),
				TDCoordinate.newInstance(0, 4));
		matrix.putComponent(
				Tile.newTile(TileType.DRAGON, "dragon", null),
				TDCoordinate.newInstance(0, 5));

		List<Integer> scores = Lists.newArrayList();
		matrix.getScore(0, 6, 0, true, scores);
		assertEquals(Integer.valueOf(-5),scores.get(0));
		assertEquals(Integer.valueOf(1),scores.get(1));
		assertEquals(Integer.valueOf(0),scores.get(2));
			} catch (GameRuleException e) {
		e.printStackTrace();
		fail();
	}
}
public void testMRHRDM()
{

	TDMatrix matrix = new TDMatrix(5, 6);
	try {
		matrix.putComponent(Tile.newTile(TileType.MOUNTAIN, "mountain", null),
				TDCoordinate.newInstance(0, 0));
		matrix.putComponent(Tile.newTile(TileType.RESOURCE, "resource", 1),
				TDCoordinate.newInstance(0, 1));
		matrix.putComponent(
				Tile.newTile(TileType.HAZARD, "hazard", -5),
				TDCoordinate.newInstance(0, 2));

		matrix.putComponent(Tile.newTile(TileType.RESOURCE, "test", 6),
				TDCoordinate.newInstance(0, 3));
		matrix.putComponent(Tile.newTile(TileType.DRAGON, "dragon",null),
				TDCoordinate.newInstance(0, 4));
		matrix.putComponent(
				Tile.newTile(TileType.MOUNTAIN, "mountain", null),
				TDCoordinate.newInstance(0, 5));

		List<Integer> scores = Lists.newArrayList();
		matrix.getScore(0, 6, 0, true, scores);
		assertEquals(Integer.valueOf(0),scores.get(0));
		assertEquals(Integer.valueOf(-5),scores.get(1));
		assertEquals(Integer.valueOf(0),scores.get(2));
			} catch (GameRuleException e) {
		e.printStackTrace();
		fail();
	}

}
public void testMGRMDH()
{


	TDMatrix matrix = new TDMatrix(5, 6);
	try {
		matrix.putComponent(Tile.newTile(TileType.MOUNTAIN, "mountain", null),
				TDCoordinate.newInstance(0, 0));
		matrix.putComponent(Tile.newTile(TileType.GOLDMINE, "goldmine", null),
				TDCoordinate.newInstance(0, 1));
		matrix.putComponent(
				Tile.newTile(TileType.RESOURCE, "resource", 1),
				TDCoordinate.newInstance(0, 2));

		matrix.putComponent(Tile.newTile(TileType.MOUNTAIN, "mountain", null),
				TDCoordinate.newInstance(0, 3));
		matrix.putComponent(Tile.newTile(TileType.DRAGON, "dragon",null),
				TDCoordinate.newInstance(0, 4));
		matrix.putComponent(
				Tile.newTile(TileType.HAZARD, "hazard", -5),
				TDCoordinate.newInstance(0, 5));

		List<Integer> scores = Lists.newArrayList();
		matrix.getScore(0, 6, 0, true, scores);
		assertEquals(Integer.valueOf(0),scores.get(0));
		assertEquals(Integer.valueOf(2),scores.get(1));
		assertEquals(Integer.valueOf(-5),scores.get(2));
	} catch (GameRuleException e) {
		e.printStackTrace();
		fail();
	}
	


}
public void testMGRHDH()
{



	TDMatrix matrix = new TDMatrix(5, 6);
	try {
		matrix.putComponent(Tile.newTile(TileType.MOUNTAIN, "mountain", null),
				TDCoordinate.newInstance(0, 0));
		matrix.putComponent(Tile.newTile(TileType.GOLDMINE, "goldmine", null),
				TDCoordinate.newInstance(0, 1));
		matrix.putComponent(
				Tile.newTile(TileType.RESOURCE, "resource", 1),
				TDCoordinate.newInstance(0, 2));

		matrix.putComponent(Tile.newTile(TileType.HAZARD, "hazard", -1),
				TDCoordinate.newInstance(0, 3));
		matrix.putComponent(Tile.newTile(TileType.DRAGON, "dragon",null),
				TDCoordinate.newInstance(0, 4));
		matrix.putComponent(
				Tile.newTile(TileType.HAZARD, "hazard", -5),
				TDCoordinate.newInstance(0, 5));

		List<Integer> scores = Lists.newArrayList();
		matrix.getScore(0, 6, 0, true, scores);
		for (Integer score : scores) {
			System.out.println(score);
		}
		assertEquals(Integer.valueOf(0),scores.get(0));
		assertEquals(Integer.valueOf(-12),scores.get(1));
			} catch (GameRuleException e) {
		e.printStackTrace();
		fail();
	}
	
}
public void testMMRRHH()
{

	TDMatrix matrix = new TDMatrix(5, 6);
	try {
		matrix.putComponent(Tile.newTile(TileType.MOUNTAIN, "mountain", null),
				TDCoordinate.newInstance(0, 0));
		matrix.putComponent(Tile.newTile(TileType.MOUNTAIN, "mountain", null),
				TDCoordinate.newInstance(0, 1));
		matrix.putComponent(
				Tile.newTile(TileType.RESOURCE, "resource", 1),
				TDCoordinate.newInstance(0, 2));

		matrix.putComponent(Tile.newTile(TileType.RESOURCE, "resource", 2),
				TDCoordinate.newInstance(0, 3));
		matrix.putComponent(Tile.newTile(TileType.HAZARD, "hazard",-1),
				TDCoordinate.newInstance(0, 4));
		matrix.putComponent(
				Tile.newTile(TileType.HAZARD, "hazard", -2),
				TDCoordinate.newInstance(0, 5));

		List<Integer> scores = Lists.newArrayList();
		matrix.getScore(0, 6, 0, true, scores);
		for (Integer score : scores) {
			System.out.println(score);
		}
		assertEquals(Integer.valueOf(0),scores.get(0));
		assertEquals(Integer.valueOf(0),scores.get(1));
		assertEquals(Integer.valueOf(0),scores.get(2));
			} catch (GameRuleException e) {
		e.printStackTrace();
		fail();
	}
	

}
}



