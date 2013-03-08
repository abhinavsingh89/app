/**
 * class used for initializing the Kingdoms boards and implements BoardBuilder
 * @author Team K
 * @since 1.0
 */
package org.concordia.kingdoms.board.factory;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.concordia.kingdoms.GameBox;
import org.concordia.kingdoms.Player;
import org.concordia.kingdoms.board.Board;
import org.concordia.kingdoms.board.ICoordinate;
import org.concordia.kingdoms.board.IMatrix;
import org.concordia.kingdoms.board.TDCoordinate;
import org.concordia.kingdoms.board.TDMatrix;
import org.concordia.kingdoms.domain.Coin;
import org.concordia.kingdoms.domain.CoinType;
import org.concordia.kingdoms.domain.Color;
import org.concordia.kingdoms.exceptions.GameException;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class TDBoardBuilder implements BoardBuilder<TDCoordinate> {

	public IMatrix<TDCoordinate> buildEmptyBoard(final TDCoordinate coordinate) {
		final IMatrix<TDCoordinate> matrix = new TDMatrix(coordinate.getRow(),
				coordinate.getColumn());
		return matrix;
	}

	/**
	 * method used returning tile bank
	 * 
	 * @return tilebank
	 */
	public TileBank buildTileBank() {
		return TileBank.getTileBank();
	}

	/**
	 * method used for returning tilebank
	 * 
	 * @return coinbank
	 */
	public CoinBank buildCoinBank() {
		return CoinBank.getCoinBank();
	}

	public Player<TDCoordinate> buildPlayer(final String name,
			final Color chosenColor) {
		return Player.newPlayer(name, chosenColor);
	}

	public Board<TDCoordinate> buildBoard(final TDCoordinate coordinate,
			final List<Player<TDCoordinate>> players) throws GameException {
		isPlayersColorUnique(players);
		final Board<TDCoordinate> board = new Board<TDCoordinate>(
				this.buildEmptyBoard(coordinate));
		board.setTileBank(this.buildTileBank());
		board.setCoinBank(this.buildCoinBank());
		board.setPlayers(players);
		initPlayers(board, players);
		return board;
	}

	/**
	 * method used for checking if the color is not already selected.
	 * 
	 * @param players
	 * @return true/false
	 * @throws GameException
	 */
	private boolean isPlayersColorUnique(
			final List<Player<TDCoordinate>> players) throws GameException {
		final Set<Color> colors = Sets.newHashSet();
		for (final Player<? extends ICoordinate> player : players) {
			final Color color = player.getChosenColor();
			final int sizeBefore = colors.size();
			colors.add(color);
			int sizeAfter = colors.size();
			if (sizeBefore == sizeAfter) {
				throw new GameException(
						"Player(s) chosen same color, Each Player has to choose unique color(s)");
			}
		}

		return true;
	}

	/**
	 * method used for initializing the players
	 * 
	 * @param board
	 * @param players
	 * @throws GameException
	 */
	private void initPlayers(Board<TDCoordinate> board,
			final List<Player<TDCoordinate>> players) throws GameException {
		// each player must have the board to put component
		for (final Player<TDCoordinate> player : players) {
			player.setBoard(board);
			final Map<CoinType, List<Coin>> coinMap = Maps.newHashMap();
			coinMap.put(CoinType.GOLD_50,
					GameBox.getGameBox().takeCoins(CoinType.GOLD_50, 1));
			player.setCoins(coinMap);
			GameBox.getGameBox().assignRankOneCastles(player, players.size());
			GameBox.getGameBox().assignCastles(player, players.size());
		}

	}
}
