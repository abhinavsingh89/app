package org.concordia.kingdoms;

import org.concordia.kingdoms.board.ICoordinate;

/**
 * Games which have a concept of level need to subclass
 * 
 * @author Team K
 * @since 1.0
 * 
 */
public abstract class AbstractGame<T extends ICoordinate> implements Game<T> {

	/**
	 * A level may be completed depending on the variants used in the game but
	 * generally a level is said to be finished when there is no other option to
	 * make another move
	 * 
	 * @return boolean - it return the boolean value true if level is completed
	 *         otherwise false
	 */
	public abstract boolean isLevelCompleted();

}
