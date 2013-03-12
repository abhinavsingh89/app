package org.concordia.kingdoms.board;

/**
 * Game board defining current entries into the game, tile bank, coin bank
 * players, etc. This class is basically keeping a track of board state. 
 * @author Team K
 * @since 1.0
 *
 */

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.concordia.kingdoms.CoinBank;
import org.concordia.kingdoms.Player;
import org.concordia.kingdoms.TileBank;
import org.concordia.kingdoms.domain.Component;
import org.concordia.kingdoms.exceptions.GameRuleException;

import com.google.common.collect.Lists;

public class Board<T extends ICoordinate> {

	private IMatrix<T> matrix;

	private TileBank tileBank;

	private CoinBank coinBank;

	private List<Player<T>> players;

	public static final int MAX_ROWS = 5;

	public static final int MAX_COLUMNS = 6;

	private int componentsOnBoard;

	/**
	 * 
	 * @param entries
	 *            - It is a 2-D array
	 */
	public Board(final IMatrix<T> matrix) {
		this.matrix = matrix;
		this.tileBank = null;
		this.coinBank = null;
		this.players = Lists.newArrayList();
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
	public void putComponent(Component component, T coordinate)
			throws GameRuleException {
		this.matrix.putComponent(component, coordinate);
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
	public boolean isValidPosition(T coordinate) {
		return this.matrix.isValidPosition(coordinate);
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
	public boolean isEntryEmpty(T coordinate) {
		return this.matrix.isEmpty(coordinate);
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
	 * @return unmodifiable players list
	 */
	public List<Player<T>> getPlayers() {
		return Collections.unmodifiableList(this.players);
	}

	/**
	 * method used to set the players
	 * 
	 * @param players
	 */
	public void setPlayers(final List<Player<T>> players) {
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

	public Iterator<Entry<T>> getEntries() {
		return this.matrix.getEntries();
	}

	public int getComponentsOnBoard() {
		return this.componentsOnBoard;
	}
}
