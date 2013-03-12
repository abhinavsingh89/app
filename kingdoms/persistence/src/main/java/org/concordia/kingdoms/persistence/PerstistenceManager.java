package org.concordia.kingdoms.persistence;

import java.io.File;

import org.concordia.kingdoms.jaxb.GameState;

public interface PerstistenceManager {

	void save(GameState gameState) throws PersistenceException;

	GameState load(File file) throws PersistenceException;
}
