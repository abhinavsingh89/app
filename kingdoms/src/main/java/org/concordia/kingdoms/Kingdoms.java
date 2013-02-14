package org.concordia.kingdoms;

import java.io.IOException;
import java.util.List;

import org.concordia.kingdoms.board.Board;
import org.concordia.kingdoms.board.EpochCounter;
import org.concordia.kingdoms.board.factory.BoardBuilder;
import org.concordia.kingdoms.board.factory.KingdomBoardBuilder;
import org.concordia.kingdoms.exceptions.GameException;

public class Kingdoms extends AbstractGame {

	private Board board;

	private EpochCounter epochCounter;

	private BoardBuilder builder;

	private boolean isGameInProgress = false;

	public Kingdoms() throws IOException {
		this(KingdomBoardBuilder.newKingdomBoardBuilder(), 3);
	}

	public Kingdoms(final BoardBuilder builder, int totalLevels)
			throws IOException {
		this.builder = builder;
		this.epochCounter = new EpochCounter(totalLevels);
	}

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

	public void save() {
		//
	}

	public void resume() {

	}

	public void exit() {
		//
	}

	public String getName() {
		// get from properties file
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

}
