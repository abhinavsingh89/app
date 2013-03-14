package org.concordia.kingdoms.persistence;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.concordia.kingdoms.jaxb.GameState;
import org.concordia.kingdoms.jaxb.JaxbUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * This class is used to save and load the Game state.
 * @author Team K
 * @since 1.1
 */
public class JaxbPersitenceManager implements PerstistenceManager {

	private static final Logger log = LoggerFactory
			.getLogger(JaxbPersitenceManager.class);
	/**
	 * This method is used to save the Game state.
	 * 
	 */
	public void save(GameState gameState) throws PersistenceException {
		try {
			JaxbUtil.INSTANCE.save(gameState);
		} catch (JAXBException ex) {
			log.error(ex.getMessage());
			ex.printStackTrace();
			throw new PersistenceException(ex.getMessage());
		}
	}
	/**
	 * This method is used to load the Game state.
	 * 
	 */
	public GameState load(File file) throws PersistenceException {

		try {
			final GameState gameState = JaxbUtil.INSTANCE.load(file);
			return gameState;
		} catch (JAXBException e) {
			log.error(e.getMessage());
			throw new PersistenceException(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new PersistenceException(e.getMessage());
		}
	}
}
