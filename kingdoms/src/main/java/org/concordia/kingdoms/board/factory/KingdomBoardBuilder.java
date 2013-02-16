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
import org.concordia.kingdoms.board.Entry;
import org.concordia.kingdoms.exceptions.GameException;
import org.concordia.kingdoms.tokens.Coin;
import org.concordia.kingdoms.tokens.CoinType;
import org.concordia.kingdoms.tokens.Color;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class KingdomBoardBuilder implements BoardBuilder {

	private KingdomBoardBuilder() {
		//
	}

	/**
	 * method used for initializing the board with empty state.
	 * @return entries
	 */
	public Entry[][] buildEmptyBoard(int rows, int columns) {
		final Entry[][] entries = new Entry[rows][columns];
		this.initEntries(entries, rows, columns);
		return entries;
	}

	/**
	 * method used for initializing entries with provided result. 
	 * @param entries
	 * @param rows
	 * @param columns
	 */
	private void initEntries(final Entry[][] entries, int rows, int columns) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				entries[i][j] = Entry.newEntry(i, j);
			}
		}
	}

	/**
	 * method used returning tile bank
	 * @return tilebank
	 */
	public TileBank buildTileBank() {
		return TileBank.getTileBank();
	}

	/**
	 * method used for returning tilebank
	 * @return coinbank
	 */
	public CoinBank buildCoinBank() {
		return CoinBank.getCoinBank();
	}

	/**
	 * method used for building the board
	 * @return board
	 */
	public Board buildBoard(final int rows, final int columns,
			final List<Player> players) throws GameException {
		isPlayersColorUnique(players);
		final Board board = new Board(this.buildEmptyBoard(rows, columns));
		board.setTileBank(this.buildTileBank());
		board.setCoinBank(this.buildCoinBank());
		board.setPlayers(players);
		initPlayers(board, players);
		return board;
	}

	/**
	 * method used for checking if the color is not already selected.
	 * @param players
	 * @return true/false
	 * @throws GameException
	 */
	private boolean isPlayersColorUnique(final List<Player> players)
			throws GameException {
		Set<Color> colors = Sets.newHashSet();
		for (Player player : players) {

			Color color = player.getChosenColor();
			int sizeBefore = colors.size();
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
	 * @param board
	 * @param players
	 * @throws GameException
	 */
	private void initPlayers(Board board, final List<Player> players)
			throws GameException {
		// each player must have the board to put component
		for (final Player player : players) {
			player.setBoard(board);
			final Map<CoinType, List<Coin>> coinMap = Maps.newHashMap();
			coinMap.put(CoinType.GOLD_50,
					GameBox.getGameBox().takeCoins(CoinType.GOLD_50, 1));
			player.setCoins(coinMap);
			GameBox.getGameBox().assignRankOneCastles(player, players.size());
			GameBox.getGameBox().assignCastles(player, players.size());
		}

	}

	/**
	 * method used to create the player
	 * @return player
	 */
	public Player buildPlayer(final String name, final Color chosenColor) {
		return Player.newPlayer(name, chosenColor);
	}

	/**
	 * public method used for building the board.
	 * @return
	 */
	public static KingdomBoardBuilder newKingdomBoardBuilder() {
		return new KingdomBoardBuilder();
	}

}
