package org.concordia.kingdoms.board.ui;

import org.concordia.kingdoms.board.Board;
import org.concordia.kingdoms.board.Entry;
/**
 * It displays the output on the console  
 * @author Team K
 * @since 1.0
 * 
 */
public class Console implements Presentable {

	private Entry[][] entries;
	/**
	 * Constructor for console
	 * 
	 * @param entries
	 *            - 2-D array
	 */
	public Console(final Entry[][] entries) {
		this.entries = entries;
	}
	// It displays output on console
	public void present() {
		for (int i = 0; i < Board.MAX_ROWS; i++) {
			System.out
					.println("------------------------------------------------------------");
			for (int j = 0; j < Board.MAX_COLUMNS; j++) {

				System.out.print(this.entries[i][j] + " ");
			}
			System.out.println();
		}
	}
}
