/**
 * class used for initializing the Kingdoms boards and implements BoardBuilder
 * @author Team K
 * @since 1.0
 */
package org.concordia.kingdoms.board.factory;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.concordia.kingdoms.CoinBank;
import org.concordia.kingdoms.GameBox;
import org.concordia.kingdoms.GameState;
import org.concordia.kingdoms.Player;
import org.concordia.kingdoms.TileBank;
import org.concordia.kingdoms.board.Board;
import org.concordia.kingdoms.board.Entry;
import org.concordia.kingdoms.board.ICoordinate;
import org.concordia.kingdoms.board.IMatrix;
import org.concordia.kingdoms.board.TDCoordinate;
import org.concordia.kingdoms.board.TDMatrix;
import org.concordia.kingdoms.domain.Coin;
import org.concordia.kingdoms.domain.CoinType;
import org.concordia.kingdoms.domain.Color;
import org.concordia.kingdoms.exceptions.GameException;
import org.concordia.kingdoms.spring.SpringContainer;

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
		final TileBank tileBank = SpringContainer.INSTANCE.getBean("tileBank",
				TileBank.class);
		tileBank.init();
		return tileBank;
	}

	/**
	 * method used for returning coinbank
	 * 
	 * @return coinbank
	 */
	public CoinBank buildCoinBank() {
		return SpringContainer.INSTANCE.getBean(CoinBank.class);
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

	public Board<TDCoordinate> buildBoard(final TDCoordinate coordinate,
			GameState<TDCoordinate> gameState) throws GameException {
		List<Player<TDCoordinate>> players = gameState.getPlayers();

		isPlayersColorUnique(players);
		final Board<TDCoordinate> board = new Board<TDCoordinate>(
				this.buildEmptyBoard(coordinate));

		List<Entry<TDCoordinate>> entries = gameState.getEntries();
		for (Entry<TDCoordinate> entry : entries) {
			if (entry.getComponent() != null) {
				board.putComponent(entry.getComponent(), entry.getCoordinate());
			}
		}

		TileBank tileBank = new TileBank();
		tileBank.addTiles(gameState.getTileBank());
		board.setTileBank(tileBank);
		board.setCoinBank(this.buildCoinBank());
		board.setPlayers(players);
		for (Player<TDCoordinate> player : players) {
			player.setBoard(board);
		}
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
			final GameBox gameBox = SpringContainer.INSTANCE
					.getBean(GameBox.class);
			coinMap.put(CoinType.GOLD_50,
					gameBox.takeCoins(CoinType.GOLD_50, 1));
			player.setCoins(coinMap);
			gameBox.assignRankOneCastles(player, players.size());
			gameBox.assignCastles(player, players.size());
		}

	}
}
