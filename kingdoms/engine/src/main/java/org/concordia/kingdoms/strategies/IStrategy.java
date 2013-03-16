package org.concordia.kingdoms.strategies;

import java.util.List;

import org.concordia.kingdoms.Player;
import org.concordia.kingdoms.board.Entry;
import org.concordia.kingdoms.board.ICoordinate;
import org.concordia.kingdoms.domain.Castle;
import org.concordia.kingdoms.domain.Tile;
import org.concordia.kingdoms.exceptions.GameRuleException;

public interface IStrategy<T extends ICoordinate> {

	Entry<T> getEntry(Player<T> player, List<Tile> tiles, List<Castle> castles,
			List<T> emptyCoordinates) throws GameRuleException;
}
