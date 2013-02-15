package org.concordia.kingdoms;

public abstract class AbstractGame implements Game {

	public abstract boolean isLevelCompleted();

	public boolean isValidPosition(int row, int column) {
		return true;
	}

}
