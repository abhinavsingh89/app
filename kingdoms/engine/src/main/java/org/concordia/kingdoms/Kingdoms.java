package org.concordia.kingdoms;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.concordia.kingdoms.board.Board;
import org.concordia.kingdoms.board.Entry;
import org.concordia.kingdoms.board.EpochCounter;
import org.concordia.kingdoms.board.ICoordinate;
import org.concordia.kingdoms.board.Score;
import org.concordia.kingdoms.board.factory.BoardBuilder;
import org.concordia.kingdoms.domain.Color;
import org.concordia.kingdoms.domain.Tile;
import org.concordia.kingdoms.domain.TileType;
import org.concordia.kingdoms.exceptions.GameException;
import org.concordia.kingdoms.exceptions.GameRuleException;
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

	private static final int THRESHOLD_SCORE = 500;

	// default name used in case no filename mentioned
	private String fileName = "kingdoms-jaxb.xml";

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
	 */
	public void start(final List<Player<T>> players) throws GameException {
		// if the game is not in progress then initialize everything
		log.debug("About to start a new Game");
		if (!this.isGameInProgress) {
			// Game expects atleast 2 and a maximum of 4 players only
			if (players.size() < 2 || players.size() > 4) {
				throw new GameException(
						"Supports mimimum of 2 and maximum of 4 Players.");
			} else {
				// initialize the board with empty entries
				this.initBoard(players);
				log.debug("Board iniitalized Successfully");
				this.putSpecialTilesOnBoard();
			}
		} else {
			// when game is already in progress, resume the game but not start
			throw new GameException("Game Already in Progress");
		}
	}

	private void putSpecialTilesOnBoard() throws GameRuleException {
		List<T> coordinates = this.board.getAvailableCoordinates();
		/*
		 * At the start of each epoch: The Wizard, Dragon and Gold Mine tiles
		 * will all be placed on the board in randomly-selected locations
		 */
		Random random = new Random();
		// first random integer
		int r1 = random.nextInt(coordinates.size());
		// random coordinate
		T c1 = coordinates.get(r1);
		this.board.putTileOnBoard(c1, TileType.WIZARD);
		// second random integer
		int r2 = random.nextInt(coordinates.size());
		T c2 = coordinates.get(r2);
		this.board.putTileOnBoard(c2, TileType.DRAGON);
		// third random integer
		int r3 = random.nextInt(coordinates.size());
		T c3 = coordinates.get(r3);
		this.board.putTileOnBoard(c3, TileType.GOLDMINE);
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

	/**
	 * save the game state
	 */
	public void save() throws GameException {

		org.concordia.kingdoms.GameState<T> gameState = new org.concordia.kingdoms.GameState<T>();
		// its own filename
		gameState.setFileName(fileName);
		// save's ICoordinate impl
		saveCoordinate(gameState);
		// total number of components on the board
		gameState.setComponentsOnBoard(this.board.getComponentsOnBoard());
		// actual entries on the board
		List<Entry<T>> entries = Lists.newArrayList(this.board.getEntries());
		// save the entries
		gameState.setEntries(entries);
		// players who are part of this game
		gameState.setPlayers(this.board.getPlayers());
		// tile bank
		gameState.setTileBank(board.getTileBank().getTiles());
		// running epoch for this game
		gameState.setEpochCounter(epochCounter);
		this.save(gameState);
	}

	/**
	 * re-build the game from the file
	 */
	public void resume(File file) throws GameException {
		GameState<T> gameState = loadGameState(file);
		// if the game is not in progress then initialize everything
		if (!this.isGameInProgress) {
			// Game expects atleast 2 and a maximum of 4 players only
			List<Player<T>> players = gameState.getPlayers();

			if (players.size() < 2 || players.size() > 4) {
				throw new GameException(
						"Supports mimimum of 2 and maximum of 4 Players.");
			} else {
				// initialize the board with empty entries
				this.initBoard(gameState);
				this.epochCounter = gameState.getEpochCounter();
			}
		} else {
			// when game is already in progress, resume the game but not start
			throw new GameException("Game is Already in Progress");
		}

	}

	/**
	 * builds new board from the given gameState
	 * 
	 * @param gameState
	 * @see {@link GameState}
	 * @throws GameException
	 */
	private void initBoard(GameState<T> gameState) throws GameException {
		// build a mXn board for game entries
		this.board = this.builder.buildBoard(newCoordinate(), gameState);
	}

	/**
	 * subclass knows how to parse file, fill and return GameState
	 * 
	 * @param file
	 * @return
	 * @throws GameException
	 */
	protected abstract org.concordia.kingdoms.GameState<T> loadGameState(
			File file) throws GameException;

	/**
	 * Game Name
	 */
	public String getName() {
		return "Kingdoms";
	}

	/**
	 * Game description
	 */
	public String getDescription() {
		return "Kingdoms descritpion";
	}

	/**
	 * @return @see EpochCounter
	 */
	public EpochCounter getEpochCounter() {
		return this.epochCounter;
	}

	/**
	 * returns true if we reached the level end
	 */

	@Override
	public boolean isLevelCompleted() {
		return hasReachedThresholdScore() || this.board.isFull();
	}

	private boolean hasReachedThresholdScore() {
		List<Player<T>> players = getPlayers();
		for (Player<T> player : players) {
			int totalScore = player.getTotalScore();
			if (totalScore >= THRESHOLD_SCORE) {
				return true;
			}
		}
		return false;
	}

	/**
	 * use this method before placing a component on the board to determine if
	 * it is a valid position or not
	 * 
	 * @param coordinate
	 * @return
	 */
	public boolean isValidPosition(T coordinate) {
		return this.board.isValidPosition(coordinate);
	}

	/**
	 * actual entries on the board
	 * 
	 * @return
	 */
	public Iterator<Entry<T>> getEntries() {
		return this.board.getEntries();
	}

	/**
	 * score as of now on the board, and recalculates the score for each player
	 * 
	 * @return
	 */
	public Map<Color, Score> score() {
		Map<Color, Score> scoreMap = this.board.score();
		assignScore(this.getPlayers(), scoreMap);
		return scoreMap;
	}

	/**
	 * 
	 * @param players
	 * @param scoreMap
	 */
	private void assignScore(final List<Player<T>> players,
			Map<Color, Score> scoreMap) {
		for (Player<T> player : players) {
			if (player.getScores().size() < this.getEpochCounter()
					.getCurrentLevel()) {
				Color chosenColor = player.getChosenColor();
				Score newScore = new Score(chosenColor);
				player.addNewScore(newScore);
			}

			List<Score> scores = player.getScores();
			Score playerScore = scores.get(this.getEpochCounter()
					.getCurrentLevel() - 1);
			Score currentScore = scoreMap.get(player.getChosenColor());
			if (currentScore != null) {
				playerScore.setRowScore(currentScore.getRowScore());
				playerScore.setColumnScore(currentScore.getColumnScore());
			}
		}
	}

	/**
	 * prepares the board to move to next level, this includes updating the
	 * epoch, returning tiles,castles etc.
	 * 
	 * @throws GameRuleException
	 */
	public void moveToNextLevel() throws GameRuleException {
		this.epochCounter.goNextLevel();
		this.board.levelChange(newCoordinate(), builder);
		putSpecialTilesOnBoard();
	}

	/**
	 * {@link Player}(s) in the Game
	 * 
	 * @return
	 */
	public List<Player<T>> getPlayers() {
		return this.board.getPlayers();
	}

	/**
	 * returns a tile from the tile bank, null if no tiles available
	 * 
	 * @return
	 */
	public Tile drawTile() {
		return this.board.drawTile();
	}

	/**
	 * a name assigned to the game state file
	 * 
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		if (!"".equals(fileName)) {
			this.fileName = fileName;
		}
	}

	/**
	 * returns true if the tile bank ran out of tiles
	 * 
	 * @return
	 */
	public boolean isTileBankEmpty() {
		return this.board.isTileBankEmpty();
	}

	/**
	 * method used for checking if the next level is available.
	 * 
	 * @return true/false
	 */
	public boolean isNextAvailable() {

		if (hasReachedThresholdScore()) {
			return false;
		}
		return getEpochCounter().isNextAvailable();
	}

	/**
	 * {@link ICoordinate}
	 * 
	 * @return
	 */
	protected abstract T newCoordinate();

	protected abstract void saveCoordinate(GameState<T> gameState);

	protected void save(org.concordia.kingdoms.GameState<T> gameState)
			throws GameException {
		// NOTHING TO DO ; SUBCLASS WILL TAKE CARE OF HOW TO save
	}

	public void exit() {
		// NOTHING TO DO ; SUBCLASS WILL TAKE CARE OF HOW TO exit
	}

}
