package org.concordia.kingdoms.board;

/**
 * A 2-D coordinate to identified by the row and column
 * @author Team K
 * @since 1.1
 */

public class TDCoordinate implements ICoordinate {

	private int row;

	private int column;
	/**
	 * Constructor for a TDCoordinate
	 * 
	 * @param row
	 *            - row number
	 * @param column
	 *            - column number
	 */
	private TDCoordinate(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return this.row;
	}

	public int getColumn() {
		return this.column;
	}

	public static TDCoordinate newInstance(int row, int column) {
		return new TDCoordinate(row, column);
	}

}
