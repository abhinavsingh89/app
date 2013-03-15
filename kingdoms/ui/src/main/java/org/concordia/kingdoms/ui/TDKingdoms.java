package org.concordia.kingdoms.ui;

import java.io.File;
import java.io.IOException;

import org.concordia.kingdoms.GameState;
import org.concordia.kingdoms.Kingdoms;
import org.concordia.kingdoms.adapter.AdapterUtil;
import org.concordia.kingdoms.adapter.EntriesAdapter;
import org.concordia.kingdoms.adapter.PlayersAdapter;
import org.concordia.kingdoms.board.Board;
import org.concordia.kingdoms.board.TDCoordinate;
import org.concordia.kingdoms.board.factory.BoardBuilder;
import org.concordia.kingdoms.board.factory.TDBoardBuilder;
import org.concordia.kingdoms.exceptions.GameException;
import org.concordia.kingdoms.persistence.JaxbPersitenceManager;
import org.concordia.kingdoms.persistence.PersistenceException;
import org.concordia.kingdoms.persistence.PerstistenceManager;
import org.concordia.kingdoms.spring.SpringContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The class is a 2D kingdoms
 * 
 * @author Team K
 * @since 1.1
 */
public class TDKingdoms extends Kingdoms<TDCoordinate> {

	private static final Logger log = LoggerFactory.getLogger(TDKingdoms.class);

	private PerstistenceManager perstistenceManager;
	/**
	 * Constructor for a TDKingdoms
	 * 
	 */
	public TDKingdoms(BoardBuilder<TDCoordinate> builder, int totalLevels)
			throws IOException {
		super(new TDBoardBuilder(), totalLevels);
		this.perstistenceManager = new JaxbPersitenceManager();
	}

	@Override
	protected TDCoordinate newCoordinate() {
		return TDCoordinate.newInstance(Board.MAX_ROWS, Board.MAX_COLUMNS);
	}

	public void save(GameState<TDCoordinate> gameState) throws GameException {
		org.concordia.kingdoms.jaxb.GameState jaxbGameState = new org.concordia.kingdoms.jaxb.GameState();
		jaxbGameState.setComponentsOnBoard(gameState.getComponentsOnBoard());
		jaxbGameState.setEntries(SpringContainer.INSTANCE.getBean(
				"entriesAdapter", EntriesAdapter.class).convertTo(
				gameState.getEntries().iterator()));
		jaxbGameState.setPlayers(SpringContainer.INSTANCE.getBean(
				"playersAdapter", PlayersAdapter.class).convertTo(
				gameState.getPlayers()));
		jaxbGameState.setTileBank(AdapterUtil.newJaxbTiles(gameState
				.getTileBank()));
		try {
			this.perstistenceManager.save(jaxbGameState);
		} catch (PersistenceException e) {
			throw new GameException(e.getMessage());
		}
	}

	@Override
	public GameState<TDCoordinate> loadGameState(File file)
			throws GameException {
		try {
			org.concordia.kingdoms.jaxb.GameState jaxbGameState = this.perstistenceManager
					.load(file);

		} catch (PersistenceException e) {
			log.error(e.getMessage());
		}
		return null;
	}
}
