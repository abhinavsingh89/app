package org.concordia.kingdoms.strategies;

import java.util.List;
import java.util.Random;

import org.concordia.kingdoms.board.Board;
import org.concordia.kingdoms.board.Entry;
import org.concordia.kingdoms.board.TDCoordinate;
import org.concordia.kingdoms.domain.Castle;
import org.concordia.kingdoms.domain.Component;
import org.concordia.kingdoms.domain.Tile;

public class RandomStrategy implements IStrategy {

	private Board board;

	public RandomStrategy(Board board) {
		this.board = board;
	}

	public Entry getEntry(List<Tile> tiles, List<Castle> castles,
			List<TDCoordinate> emptyCoordinates) {
		int totalTiles = tiles.size();
		int totalCastles = castles.size();

		Component component = null;

		if (totalTiles == 0 && totalCastles == 0) {
			return null;
		}

		if (totalTiles > 0) {
			int rand = new Random().nextInt(totalTiles);
			component = tiles.get(rand);
		}
		if (totalCastles > 0) {
			int rand = new Random().nextInt(totalCastles);
			component = castles.get(rand);
		}

		int totalEmptyCoordinates = emptyCoordinates.size();
		if (totalEmptyCoordinates == 0) {
			return null;
		}

		int rand = new Random().nextInt(totalEmptyCoordinates);
		TDCoordinate coordinate = emptyCoordinates.get(rand);

		return new Entry(coordinate, component);

	}
}
