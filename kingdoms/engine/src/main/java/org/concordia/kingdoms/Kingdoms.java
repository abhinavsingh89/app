package org.concordia.kingdoms;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.concordia.kingdoms.board.Board;
import org.concordia.kingdoms.board.Entry;
import org.concordia.kingdoms.board.EpochCounter;
import org.concordia.kingdoms.board.ICoordinate;
import org.concordia.kingdoms.board.Score;
import org.concordia.kingdoms.board.factory.BoardBuilder;
import org.concordia.kingdoms.domain.Color;
import org.concordia.kingdoms.exceptions.GameException;
import org.concordia.kingdoms.spring.SpringContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

/**
 * Kingdoms is a facade. It maintains game level, start the game.
 * 
 * @author Team K
 * @since 1.0
 * 
 */
public abstract class Kingdoms<T extends ICoordinate> extends AbstractGame<T> {

	private static final Logger log = LoggerFactory.getLogger(Kingdoms.class);

	private Board<T> board;

	private EpochCounter epochCounter;

	private BoardBuilder<T> builder;

	private boolean isGameInProgress = false;

	/**
	 * Constructor for a kingdom
	 * 
	 * @param builder
	 * @param totalLevels
	 * 
	 */
	public Kingdoms(final BoardBuilder<T> builder, int totalLevels)
			throws IOException {
		this.builder = builder;
		this.epochCounter = new EpochCounter(totalLevels);
	}

	/**
	 * This method is used to start the game
	 * 
	 * @param players
	 *            - Number of players
	 * 
	 */
	public void start(List<Player<T>> players) throws GameException {
		// if the game is not in progress then initialize everything

		if (!this.isGameInProgress) {
			// Game expects atleast 2 and a maximum of 4 players only

			if (players.size() < 2 || players.size() > 4) {
				throw new GameException(
						"Supports mimimum of 2 and maximum of 4 Players.");
			} else {
				// initialize the board with empty entries
				this.initBoard(players);
			}
		} else {
			// when game is already in progress, resume the game but not start

			throw new GameException("Game is Already in Progress");
		}
	}

	/**
	 * Public method for initializing the board with players.
	 * 
	 * @param players
	 * @throws GameException
	 */
	private void initBoard(List<Player<T>> players) throws GameException {
		// build a mXn board for game entries

		this.board = this.builder.buildBoard(newCoordinate(), players);
	}

	protected abstract T newCoordinate();

	/**
	 * public method for pausing
	 */
	public void pause() {
		//
	}

	public void save() throws GameException {
		org.concordia.kingdoms.GameState<T> gameState = new org.concordia.kingdoms.GameState<T>();
		gameState.setComponentsOnBoard(this.board.getComponentsOnBoard());
		gameState.setComponentsOnBoard(this.board.getComponentsOnBoard());
		List<Entry<T>> entries = Lists.newArrayList(this.board.getEntries());
		gameState.setEntries(entries);
		gameState.setPlayers(this.board.getPlayers());
		gameState.setTileBank(SpringContainer.INSTANCE.getBean("tileBank",
				TileBank.class).getTiles());
		this.save(gameState);
	}

	protected void save(org.concordia.kingdoms.GameState<T> gameState)
			throws GameException {
		//
	}

	public void resume(File file) throws GameException {
		loadGameState(file);
	}

	protected abstract org.concordia.kingdoms.GameState<T> loadGameState(
			File file) throws GameException;

	public void exit() {
		//
	}

	public String getName() {
		/**
		 * get from properties file
		 */
		return "Kingdoms";
	}

	public String getDescription() {
		return "Kingdoms descritpion";
	}

	public EpochCounter getEpochCounter() {
		return this.epochCounter;
	}

	@Override
	public boolean isLevelCompleted() {
		return !this.board.isEmpty();
	}

	public boolean isValidPosition(T coordinate) {
		return this.board.isValidPosition(coordinate);
	}

	public Iterator<Entry<T>> getEntries() {
		return this.board.getEntries();
	}

	public Map<Color, Score> score() {
		return this.board.score();
	}

}
