package org.concordia.kingdoms.persistence;

import javax.xml.bind.JAXBException;

import org.concordia.kingdoms.jaxb.GameState;
import org.concordia.kingdoms.jaxb.JaxbUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JaxbPersitenceManager implements PerstistenceManager {

	private static final Logger log = LoggerFactory
			.getLogger(JaxbPersitenceManager.class);

	public void reload() {

	}

	public void save(GameState gameState) throws PersistenceException {
		try {
			JaxbUtil.INSTANCE.save(gameState);
		} catch (JAXBException ex) {
			log.error(ex.getMessage());
			ex.printStackTrace();
			throw new PersistenceException(ex.getMessage());
		}
	}
}
