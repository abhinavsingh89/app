package org.concordia.kingdoms.strategies;

import java.util.List;
import java.util.Random;

import org.concordia.kingdoms.Player;
import org.concordia.kingdoms.board.Entry;
import org.concordia.kingdoms.board.TDCoordinate;
import org.concordia.kingdoms.domain.Castle;
import org.concordia.kingdoms.domain.Component;
import org.concordia.kingdoms.domain.Tile;
import org.concordia.kingdoms.exceptions.GameRuleException;

/**
 * This class is used to implement strategy of the random player
 * 
 * @author Team K
 * @since 1.1
 */
public class RandomStrategy implements IStrategy<TDCoordinate> {

	public Entry<TDCoordinate> getEntry(Player<TDCoordinate> player,
			List<Tile> tiles, List<Castle> castles,
			List<TDCoordinate> emptyCoordinates) throws GameRuleException {

		int tileOrCastle = new Random().nextInt(3);

		Component retComponent = null;

		// if tile is to be chosen
		if (tileOrCastle == 0) {
			// if tiles are available
			if (tiles.size() != 0) {
				retComponent = tiles.get(0);
			} else {
				// if no tiles are available , check with castles
				if (castles.size() != 0) {
					retComponent = castles.get(new Random().nextInt(castles
							.size()));
				} else {
					// if no tiles or castles are available, then draw a tile
					// from tilebank
					List<Tile> possessedTiles = player.getPossessedTiles();
					if (!possessedTiles.isEmpty()) {
						retComponent = possessedTiles.get(new Random()
								.nextInt(possessedTiles.size()));
					}
				}
			}
		} else if (tileOrCastle == 1) {// if castle is to be chosen
			// if castles are available
			if (castles.size() != 0) {
				retComponent = castles
						.get(new Random().nextInt(castles.size()));
			} else {
				// if castles are not available
				// if tiles are avaialble
				if (tiles.size() != 0) {
					retComponent = tiles.get(0);
				} else {
					// if no tiles or castles are available, then draw a tile
					// from tilebank
					List<Tile> possessedTiles = player.getPossessedTiles();
					if (!possessedTiles.isEmpty()) {
						retComponent = possessedTiles.get(new Random()
								.nextInt(possessedTiles.size()));
					}
				}
			}
		} else {
			List<Tile> possessedTiles = player.getPossessedTiles();
			if (!possessedTiles.isEmpty()) {
				retComponent = possessedTiles.get(new Random()
						.nextInt(possessedTiles.size()));
			}
		}

		// random coordinate
		int rand = new Random().nextInt(emptyCoordinates.size());

		TDCoordinate coordinate = emptyCoordinates.get(rand);

		return new Entry<TDCoordinate>(coordinate, retComponent);

	}
}
