package org.concordia.kingdoms.ui;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.concordia.kingdoms.GameState;
import org.concordia.kingdoms.Kingdoms;
import org.concordia.kingdoms.Player;
import org.concordia.kingdoms.adapter.AdapterUtil;
import org.concordia.kingdoms.adapter.EntriesAdapter;
import org.concordia.kingdoms.adapter.PlayersAdapter;
import org.concordia.kingdoms.board.Board;
import org.concordia.kingdoms.board.Entry;
import org.concordia.kingdoms.board.TDCoordinate;
import org.concordia.kingdoms.board.factory.BoardBuilder;
import org.concordia.kingdoms.board.factory.TDBoardBuilder;
import org.concordia.kingdoms.domain.Tile;
import org.concordia.kingdoms.exceptions.GameException;
import org.concordia.kingdoms.persistence.JaxbPersitenceManager;
import org.concordia.kingdoms.persistence.PersistenceException;
import org.concordia.kingdoms.persistence.PerstistenceManager;
import org.concordia.kingdoms.spring.SpringContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

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
		jaxbGameState.setMAX_ROWS(gameState.getMAX_ROWS());
		jaxbGameState.setMAX_COLUMNS(gameState.getMAX_COLUMNS());

		jaxbGameState.setEntries(SpringContainer.INSTANCE.getBean(
				"entriesAdapter", EntriesAdapter.class).convertTo(
				gameState.getEntries().iterator()));

		jaxbGameState.setPlayers(SpringContainer.INSTANCE.getBean(
				"playersAdapter", PlayersAdapter.class).convertTo(
				gameState.getPlayers()));

		jaxbGameState.setTileBank(AdapterUtil.newJaxbTiles(gameState
				.getTileBank()));

		jaxbGameState.setEpochCounter(AdapterUtil.newJaxbEpochCounter(gameState
				.getEpochCounter()));
		try {
			this.perstistenceManager.save(jaxbGameState);
		} catch (PersistenceException e) {
			throw new GameException(e.getMessage());
		}
	}

	@Override
	protected void saveCoordinate(GameState<TDCoordinate> gameState) {
		TDCoordinate coordinate = newCoordinate();
		gameState.setMAX_ROWS(coordinate.getRow());
		gameState.setMAX_COLUMNS(coordinate.getColumn());
	}

	@Override
	public GameState<TDCoordinate> loadGameState(File file)
			throws GameException {
		GameState<TDCoordinate> gameState = new GameState<TDCoordinate>();
		try {
			org.concordia.kingdoms.jaxb.GameState jaxbGameState = this.perstistenceManager
					.load(file);
			int componentsOnBoard = jaxbGameState.getComponentsOnBoard();

			Iterator<Entry<TDCoordinate>> entries = SpringContainer.INSTANCE
					.getBean("entriesAdapter", EntriesAdapter.class)
					.convertFrom(jaxbGameState.getEntries());

			int MAX_COLUMNS = jaxbGameState.getMAX_COLUMNS();
			int MAX_ROWS = jaxbGameState.getMAX_ROWS();

			List<Player<TDCoordinate>> players = SpringContainer.INSTANCE
					.getBean("playersAdapter", PlayersAdapter.class)
					.convertFrom(jaxbGameState.getPlayers());

			List<Tile> tiles = AdapterUtil
					.newTiles(jaxbGameState.getTileBank());

			gameState.setComponentsOnBoard(componentsOnBoard);
			gameState.setEntries(Lists.newArrayList(entries));
			gameState.setMAX_COLUMNS(MAX_COLUMNS);
			gameState.setMAX_ROWS(MAX_ROWS);
			gameState.setPlayers(players);
			gameState.setTileBank(tiles);

		} catch (PersistenceException e) {
			log.error(e.getMessage());
			throw new GameException(e.getMessage());
		}
		return gameState;
	}
}
