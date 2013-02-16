package org.concordia.kingdoms.board;

/**
 * Game board defining current entries into the game, tile bank, coin bank
 * players, etc. This class is basically keeping a track of board state. 
 * @author Team K
 * @since 1.0
 *
 */

import java.util.List;

import org.concordia.kingdoms.GameBox;
import org.concordia.kingdoms.Player;
import org.concordia.kingdoms.board.factory.CoinBank;
import org.concordia.kingdoms.board.factory.TileBank;
import org.concordia.kingdoms.board.ui.Console;
import org.concordia.kingdoms.board.ui.Presentable;
import org.concordia.kingdoms.exceptions.GameRuleException;

import com.google.common.collect.Lists;

public class Board {

	private Entry[][] entries;

	private TileBank tileBank;

	private CoinBank coinBank;

	private List<Player> players;

	private Presentable presentable;

	public static final int MAX_ROWS = 5;

	public static final int MAX_COLUMNS = 6;

	private int componentsOnBoard;

	/**
	 * 
	 * @param entries
	 *            - It is a 2-D array
	 */
	public Board(final Entry[][] entries) {
		this.entries = entries;
		this.tileBank = null;
		this.coinBank = null;
		this.players = Lists.newArrayList();
		this.presentable = new Console(entries);
		this.componentsOnBoard = 0;
	}

	/**
	 * method used to put component
	 * 
	 * @param component
	 * 
	 * @param row
	 *            - row number
	 * @param column
	 *            - row column
	 */
	public void putComponent(Component component, int row, int column)
			throws GameRuleException {
		if (!isValidCoordinate(row, column)) {
			throw new GameRuleException("Invalid positon(" + row + "," + column
					+ ")");
		}
		if (!isEntryEmpty(row, column)) {
			throw new GameRuleException("No Space available");
		}
		this.getEntries()[row][column].setComponent(component);
		this.componentsOnBoard++;
	}

	/**
	 * method used to test the given coordinade are valid or not It returns
	 * boolean value
	 * 
	 * @param row
	 *            - row number
	 * @param column
	 *            - row column
	 */
	public boolean isValidCoordinate(int row, int column) {
		return row >= 0 && row < MAX_ROWS && column >= 0
				&& column < MAX_COLUMNS;
	}

	/**
	 * method used to test the given position is valid or not It returns boolean
	 * value
	 * 
	 * @param row
	 *            - row number
	 * @param column
	 *            - row column
	 */
	public boolean isValidPosition(int row, int column) {
		final boolean isValidCoordinate = row >= 0 && row < MAX_ROWS
				&& column >= 0 && column < MAX_COLUMNS;
		if (isValidCoordinate) {
			// check if any space is available
			final boolean isEmpty = this.getEntries()[row][column].isEmpty();
			if (isEmpty) {
				return true;
			}

		}
		return false;
	}

	/**
	 * method used to test the values in 2-D array entries. It returns boolean
	 * value
	 * 
	 * @param row
	 *            - row number
	 * @param column
	 *            - row column
	 */
	public boolean isEntryEmpty(int row, int column) {
		return this.getEntries()[row][column].isEmpty();
	}

	/**
	 * method used for display
	 */
	public void display() {
		this.presentable.present();
	}

	/**
	 * method used for returning board entries
	 * 
	 * @return entries
	 */
	public Entry[][] getEntries() {
		return this.entries;
	}

	/**
	 * method used for returning tile bank
	 * 
	 * @return tileBank
	 */
	public TileBank getTileBank() {
		return this.tileBank;
	}

	/**
	 * method to set the tilebank
	 * 
	 * @param tileBank
	 */
	public void setTileBank(TileBank tileBank) {
		this.tileBank = tileBank;
	}

	/**
	 * method for returning coin bank
	 * 
	 * @return coinBank
	 */
	public CoinBank getCoinBank() {
		return this.coinBank;
	}

	/**
	 * sets the coin bank
	 * 
	 * @param coinBank
	 */
	public void setCoinBank(CoinBank coinBank) {
		this.coinBank = coinBank;
	}

	/**
	 * method used for returning the players list
	 * 
	 * @return players
	 */
	public List<Player> getPlayers() {
		return this.players;
	}

	/**
	 * method used to set the players
	 * 
	 * @param players
	 */
	public void setPlayers(final List<Player> players) {
		this.players = players;
	}

	/**
	 * method used to check if there is any empty space on the board
	 * 
	 * @return true/false
	 */
	public boolean hasAnyEmptySpace() {
		return this.componentsOnBoard == MAX_ROWS * MAX_COLUMNS;
	}

	/**
	 * method used to reset the board to initial stage
	 */
	public void resetBoard() {
		this.entries = new Entry[Board.MAX_ROWS][MAX_COLUMNS];
		this.tileBank = null;
		this.coinBank = null;
		this.players = Lists.newArrayList();
		this.presentable = new Console(this.entries);
		this.componentsOnBoard = 0;
		GameBox.reset();
	}

	public int getComponentsOnBoard() {
		return componentsOnBoard;
	}
}
