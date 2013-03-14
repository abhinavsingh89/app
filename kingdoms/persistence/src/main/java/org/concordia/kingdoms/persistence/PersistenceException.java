package org.concordia.kingdoms.persistence;
/**
 * This class is used to handle the exception within the module.
 * @author Team K
 * @since 1.1
 */
public class PersistenceException extends Exception {

	private static final long serialVersionUID = 1L;

	public PersistenceException(String message) {
		super(message);
	}

}
