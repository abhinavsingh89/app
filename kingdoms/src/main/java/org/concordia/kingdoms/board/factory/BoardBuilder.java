/**
 * class used for initializing the board
 * @author Team K
 * @since 1.0
 */
package org.concordia.kingdoms.board.factory;

import java.util.List;

import org.concordia.kingdoms.Player;
import org.concordia.kingdoms.board.Board;
import org.concordia.kingdoms.board.Entry;
import org.concordia.kingdoms.exceptions.GameException;
import org.concordia.kingdoms.tokens.Color;

public interface BoardBuilder {

	Entry[][] buildEmptyBoard(int rows, int columns);

	TileBank buildTileBank();

	CoinBank buildCoinBank();

	Player buildPlayer(String name, Color chosenColor);

	Board buildBoard(int rows, int columns, List<Player> players)
			throws GameException;

}
