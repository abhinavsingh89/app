package org.concordia.kingdoms.persistence;

import java.io.File;

import org.concordia.kingdoms.jaxb.GameState;
/**
 * It is an interface for Jaxb
 * @author Team K
 * @since 1.1
 */
public interface PerstistenceManager {

	void save(GameState gameState) throws PersistenceException;

	GameState load(File file) throws PersistenceException;
}
