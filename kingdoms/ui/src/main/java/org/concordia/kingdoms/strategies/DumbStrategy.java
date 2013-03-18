package org.concordia.kingdoms.strategies;

import java.util.List;
import java.util.Random;

import org.concordia.kingdoms.Player;
import org.concordia.kingdoms.board.Entry;
import org.concordia.kingdoms.board.TDCoordinate;
import org.concordia.kingdoms.domain.Castle;
import org.concordia.kingdoms.domain.Tile;
import org.concordia.kingdoms.exceptions.GameRuleException;

public class DumbStrategy implements IStrategy<TDCoordinate> {

	public Entry<TDCoordinate> getEntry(Player<TDCoordinate> player,
			List<Tile> tiles, List<Castle> castles,
			List<TDCoordinate> emptyCoordinates) throws GameRuleException {

		int rand = new Random().nextInt(emptyCoordinates.size());
		TDCoordinate coordinate = emptyCoordinates.get(rand);
		if (!castles.isEmpty()) {
			Castle castle = castles.remove(castles.size());
			return new Entry<TDCoordinate>(coordinate, castle);
		}
		if (!tiles.isEmpty()) {
			if (!player.isStartingTileUsed()) {
				return new Entry<TDCoordinate>(coordinate,
						player.getStartingTile());
			}
		}
		Tile tile = player.drawTile();
		return new Entry<TDCoordinate>(coordinate, tile);
	}
}
