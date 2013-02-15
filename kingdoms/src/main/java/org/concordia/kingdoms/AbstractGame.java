package org.concordia.kingdoms;
/**
 * 
 * @author Team K
 * @version 1.0-SNAPSHOT
 *
 */
public abstract class AbstractGame implements Game {

	public abstract boolean isLevelCompleted();

	public boolean isValidPosition(int row, int column) {
		return true;
	}

}
