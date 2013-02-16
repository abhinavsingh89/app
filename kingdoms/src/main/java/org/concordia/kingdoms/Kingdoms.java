package org.concordia.kingdoms;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.concordia.kingdoms.adapter.EntriesAdapter;
import org.concordia.kingdoms.adapter.IAdapter;
import org.concordia.kingdoms.adapter.PlayersAdapter;
import org.concordia.kingdoms.board.Board;
import org.concordia.kingdoms.board.Entry;
import org.concordia.kingdoms.board.EpochCounter;
import org.concordia.kingdoms.board.factory.BoardBuilder;
import org.concordia.kingdoms.board.factory.KingdomBoardBuilder;
import org.concordia.kingdoms.board.ui.Console;
import org.concordia.kingdoms.board.ui.Presentable;
import org.concordia.kingdoms.exceptions.GameException;
import org.concordia.kingdoms.jaxb.GameState;
import org.concordia.kingdoms.jaxb.JaxbUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Kingdoms is a facade. It maintains game level, start the game.
 * 
 * @author Team K
 * @since 1.0
 * 
 */
public class Kingdoms extends AbstractGame {

	private static final Logger log = LoggerFactory.getLogger(Kingdoms.class);

	private Board board;

	private EpochCounter epochCounter;

	private BoardBuilder builder;

	private boolean isGameInProgress = false;

	public Kingdoms() throws IOException {
		this(KingdomBoardBuilder.newKingdomBoardBuilder(), 3);
	}

	/**
	 * Constructor for a kingdom
	 * 
	 * @param builder
	 * @param totalLevels
	 * 
	 */
	public Kingdoms(final BoardBuilder builder, int totalLevels)
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
	public void start(final List<Player> players) throws GameException {
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

	private void initBoard(List<Player> players) throws GameException {
		// build a mXn board for game entries

		this.board = this.builder.buildBoard(Board.MAX_ROWS, Board.MAX_COLUMNS,
				players);
	}

	public void pause() {
		//
	}

	public void save() throws GameException {
		GameState gameState = new GameState();
		IAdapter<Entry[][], List<org.concordia.kingdoms.jaxb.Entry>> entriesAdapter = new EntriesAdapter();
		IAdapter<List<Player>, List<org.concordia.kingdoms.jaxb.Player>> playersAdapter = new PlayersAdapter();
		gameState.setComponentsOnBoard(this.board.getComponentsOnBoard());
		gameState.setEntries(entriesAdapter.convertTo(this.board.getEntries()));
		gameState.setPlayers(playersAdapter.convertTo(this.board.getPlayers()));
		try {
			JaxbUtil.INSTANCE.save(gameState);
		} catch (JAXBException ex) {
			log.error(ex.getMessage());
			ex.printStackTrace();
			throw new GameException(ex.getMessage());
		}
	}

	public void resume(File file) throws GameException {
		try {
			final GameState gameState = JaxbUtil.INSTANCE.load(file);
			IAdapter<Entry[][], List<org.concordia.kingdoms.jaxb.Entry>> entriesAdapter = new EntriesAdapter();
			final Entry[][] entries = entriesAdapter.convertFrom(gameState
					.getEntries());
			Presentable presentable = new Console(entries);
			presentable.present();
		} catch (JAXBException e) {
			log.error(e.getMessage());
			throw new GameException(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new GameException(e.getMessage());
		}
	}

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

	public void present() {
		this.board.display();
	}

	@Override
	public boolean isLevelCompleted() {
		return this.board.hasAnyEmptySpace();
	}

	@Override
	public boolean isValidPosition(int row, int column) {
		return this.board.isValidPosition(row, column);
	}

}
