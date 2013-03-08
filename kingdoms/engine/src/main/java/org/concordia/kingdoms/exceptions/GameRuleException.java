package org.concordia.kingdoms.exceptions;
/**
 * It is used to handle the exceptions 
 * @author Team K
 * @since 1.0
 * 
 */
public class GameRuleException extends GameException {

	private static final long serialVersionUID = 1L;
	/**
	 * Constructor for a GameRuleException
	 * 
	 * @param message
	 *            - shows the error message
	 */
	public GameRuleException(String message) {
		super(message);
	}
}
