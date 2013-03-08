package org.concordia.kingdoms.persistence;

import org.concordia.kingdoms.jaxb.GameState;

public interface PerstistenceManager {

	void save(GameState gameState) throws PersistenceException;

	void reload();
}
