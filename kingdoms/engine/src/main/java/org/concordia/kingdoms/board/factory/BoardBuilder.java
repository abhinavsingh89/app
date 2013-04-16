/**
 * basic builder for initializing with an empty board,the tile bank, the coin bank and the players
 * @author Team K
 * @since 1.0
 */
package org.concordia.kingdoms.board.factory;

import java.util.List;

import org.concordia.kingdoms.CoinBank;
import org.concordia.kingdoms.GameState;
import org.concordia.kingdoms.Player;
import org.concordia.kingdoms.board.Board;
import org.concordia.kingdoms.board.ICoordinate;
import org.concordia.kingdoms.board.IMatrix;
import org.concordia.kingdoms.board.TileBank;
import org.concordia.kingdoms.domain.Color;
import org.concordia.kingdoms.exceptions.GameException;

public interface BoardBuilder<T extends ICoordinate> {

	/**
	 * builds an empty board with the given coordinates initialized with empty
	 * Entries
	 * 
	 * @return - Matrix holding a data structure to put all entries
	 */
	IMatrix<T> buildEmptyBoard(T coordinate);

	/**
	 * builds a tile bank
	 * 
	 * @return TileBank
	 */
	TileBank buildTileBank();

	/**
	 * builds a coin bank
	 * 
	 * @return CoinBank
	 */
	CoinBank buildCoinBank();

	/**
	 * builds a player
	 * 
	 * @param name
	 *            - each player name
	 * @param chosenColor
	 *            - each player chosen color
	 * @return Player
	 */
	Player<T> buildPlayer(String name, Color chosenColor);

	Board<T> buildBoard(T coordinate, List<Player<T>> players)
			throws GameException;

	Board<T> buildBoard(final T coordinate, GameState<T> gameState)
			throws GameException;

}
