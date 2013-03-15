package org.concordia.kingdoms;

import java.io.File;
import java.util.List;

import org.concordia.kingdoms.board.ICoordinate;
import org.concordia.kingdoms.exceptions.GameException;

/**
 * The user of this interface has precise control over the game. The control
 * like starting the game, resuming, saving and exit the game
 * 
 * @author Team K
 * @since 1.0
 * 
 */
public interface Game<T extends ICoordinate> {

	/**
	 * starts the game
	 * 
	 * @param players
	 *            - required
	 * @throws GameException
	 */
	void start(List<Player<T>> players) throws GameException;

	/**
	 * resumes the game from the file having the game state
	 * 
	 * @param file
	 *            - required
	 * @throws GameException
	 */
	void resume(File file) throws GameException;

	/**
	 * saves the game state, so that the same can be reloaded
	 * 
	 * @throws GameException
	 */
	void save() throws GameException;

	/**
	 * gets out of the game
	 */
	void exit();

	String getName();

	String getDescription();

}
