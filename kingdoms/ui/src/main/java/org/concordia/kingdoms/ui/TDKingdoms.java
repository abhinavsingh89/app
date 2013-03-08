package org.concordia.kingdoms.ui;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.concordia.kingdoms.GameState;
import org.concordia.kingdoms.Kingdoms;
import org.concordia.kingdoms.adapter.AdapterUtil;
import org.concordia.kingdoms.adapter.EntriesAdapter;
import org.concordia.kingdoms.adapter.IAdapter;
import org.concordia.kingdoms.adapter.PlayersAdapter;
import org.concordia.kingdoms.board.Board;
import org.concordia.kingdoms.board.Entry;
import org.concordia.kingdoms.board.TDCoordinate;
import org.concordia.kingdoms.board.factory.BoardBuilder;
import org.concordia.kingdoms.board.factory.TDBoardBuilder;
import org.concordia.kingdoms.exceptions.GameException;
import org.concordia.kingdoms.jaxb.Player;
import org.concordia.kingdoms.persistence.JaxbPersitenceManager;
import org.concordia.kingdoms.persistence.PersistenceException;
import org.concordia.kingdoms.persistence.PerstistenceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TDKingdoms extends Kingdoms<TDCoordinate> {

	private static final Logger log = LoggerFactory.getLogger(TDKingdoms.class);

	private PerstistenceManager perstistenceManager;

	public TDKingdoms(BoardBuilder<TDCoordinate> builder, int totalLevels)
			throws IOException {
		super(new TDBoardBuilder(), totalLevels);
		this.perstistenceManager = new JaxbPersitenceManager();
	}

	@Override
	protected TDCoordinate newCoordinate() {
		return TDCoordinate.newInstance(Board.MAX_ROWS, Board.MAX_COLUMNS);
	}

	@Override
	protected IAdapter<Iterator<Entry<TDCoordinate>>, List<org.concordia.kingdoms.jaxb.Entry>> newEntriesAdapter() {
		return new EntriesAdapter();
	}

	@Override
	protected IAdapter<List<org.concordia.kingdoms.Player<TDCoordinate>>, List<Player>> newPlayersAdapter() {
		return new PlayersAdapter();
	}

	public void save(GameState<TDCoordinate> gameState) throws GameException {
		org.concordia.kingdoms.jaxb.GameState jaxbGameState = new org.concordia.kingdoms.jaxb.GameState();
		jaxbGameState.setComponentsOnBoard(gameState.getComponentsOnBoard());
		jaxbGameState.setEntries(newEntriesAdapter().convertTo(
				gameState.getEntries().iterator()));
		jaxbGameState.setPlayers(newPlayersAdapter().convertTo(
				gameState.getPlayers()));
		jaxbGameState.setTileBank(AdapterUtil.newJaxbTiles(gameState
				.getTileBank()));
		try {
			this.perstistenceManager.save(jaxbGameState);
		} catch (PersistenceException e) {
			throw new GameException(e.getMessage());
		}
	}
}
