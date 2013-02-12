package org.concordia.kingdoms.board.ui;

import org.concordia.kingdoms.board.Board;
import org.concordia.kingdoms.board.Entry;

public class Console implements Presentable {

	private Entry[][] entries;

	public Console(final Entry[][] entries) {
		this.entries = entries;
	}

	public void present() {
		for (int i = 0; i < Board.MAX_ROWS; i++) {
			for (int j = 0; j < Board.MAX_COLUMNS; j++) {
				System.out.print(this.entries[i][j] + " ");
			}
			System.out.println();
		}
	}
}
