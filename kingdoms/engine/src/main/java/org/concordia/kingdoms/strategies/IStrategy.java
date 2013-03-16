package org.concordia.kingdoms.strategies;

import java.util.List;

import org.concordia.kingdoms.board.Entry;
import org.concordia.kingdoms.board.TDCoordinate;
import org.concordia.kingdoms.domain.Castle;
import org.concordia.kingdoms.domain.Tile;

public interface IStrategy {

	Entry getEntry(List<Tile> tiles, List<Castle> castles,
			List<TDCoordinate> emptyCoordinates);
}
