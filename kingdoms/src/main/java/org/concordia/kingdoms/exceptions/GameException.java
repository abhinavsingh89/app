package org.concordia.kingdoms.exceptions;

import org.concordia.kingdoms.board.factory.CoinBank;

/**
 * It is used to handle the exceptions 
 * @author Team K
 * @since 1.0
 * 
 */
public class GameException extends Exception {

	private static final long serialVersionUID = 1L;
	/**
	 * Constructor for a GameException
	 * 
	 * @param message
	 *            - shows the error message
	 */
	public GameException(String message) {
		super(message);
	}
}
