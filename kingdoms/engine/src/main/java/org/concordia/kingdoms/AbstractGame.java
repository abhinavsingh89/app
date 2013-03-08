package org.concordia.kingdoms;

import org.concordia.kingdoms.board.ICoordinate;

/**
 * 
 * @author Team K
 * @since 1.0
 * 
 */
public abstract class AbstractGame<T extends ICoordinate> implements Game<T> {

	/**
	 * 
	 * @return boolean - it return the boolean value true if level is completed
	 *         otherwise false
	 */
	public abstract boolean isLevelCompleted();

}
