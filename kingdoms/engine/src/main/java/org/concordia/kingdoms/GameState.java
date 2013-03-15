package org.concordia.kingdoms;

import java.util.List;

import org.concordia.kingdoms.board.Entry;
import org.concordia.kingdoms.board.ICoordinate;
import org.concordia.kingdoms.domain.Tile;
/**
 * @author Team K
 * @since 1.1
 */
public class GameState<T extends ICoordinate> {

	private int MAX_ROWS;

	private int MAX_COLUMNS;

	private int componentsOnBoard;

	private List<Entry<T>> entries;

	private List<Tile> tileBank;

	private List<Player<T>> players;

	public GameState() {
	}

	public int getMAX_ROWS() {
		return MAX_ROWS;
	}

	public void setMAX_ROWS(int mAX_ROWS) {
		MAX_ROWS = mAX_ROWS;
	}

	public int getMAX_COLUMNS() {
		return MAX_COLUMNS;
	}

	public void setMAX_COLUMNS(int mAX_COLUMNS) {
		MAX_COLUMNS = mAX_COLUMNS;
	}

	public int getComponentsOnBoard() {
		return componentsOnBoard;
	}

	public void setComponentsOnBoard(int componentsOnBoard) {
		this.componentsOnBoard = componentsOnBoard;
	}

	public List<Entry<T>> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry<T>> entries) {
		this.entries = entries;
	}

	public List<Tile> getTileBank() {
		return tileBank;
	}

	public void setTileBank(List<Tile> tileBank) {
		this.tileBank = tileBank;
	}

	public List<Player<T>> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player<T>> players) {
		this.players = players;
	}
}
