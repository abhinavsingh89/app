package org.concordia.kingdoms;
/**
 * 
 * @author Team K
 * @since 1.0
 *
 */
public abstract class AbstractGame implements Game {
	
	/**
	 * 
	 * @return boolean - it return the boolean value true if level is completed otherwise false
	 */
	public abstract boolean isLevelCompleted();
	/**
	 * 
	 * @return boolean - it return the boolean value true if position is valid otherwise false
	 */
	public boolean isValidPosition(int row, int column) {
		return true;
	}

}
