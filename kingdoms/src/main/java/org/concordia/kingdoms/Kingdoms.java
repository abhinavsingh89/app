package org.concordia.kingdoms;

import java.io.IOException;
import java.util.List;

import org.concordia.kingdoms.board.Board;
import org.concordia.kingdoms.board.EpochCounter;
import org.concordia.kingdoms.board.factory.BoardBuilder;
import org.concordia.kingdoms.board.factory.KingdomBoardBuilder;
import org.concordia.kingdoms.board.factory.TileBank;
import org.concordia.kingdoms.tokens.Color;

import com.google.common.collect.Lists;

public class Kingdoms implements Game {

	private Board board;

	private EpochCounter epochCounter;

	private BoardBuilder builder;

	private boolean isGameInProgress = false;

	private int totalLevels;

	public Kingdoms() throws IOException {
		this(KingdomBoardBuilder.newKingdomBoardBuilder(), 3);
	}

	public Kingdoms(final BoardBuilder builder, int totalLevels)
			throws IOException {
		this.builder = builder;
		this.totalLevels = totalLevels;
	}

	public void start(final List<Player> players) {
		// if the game is not in progress then initialize everything
		if (!isGameInProgress) {
			// Game expects atleast 2 and a maximum of 4 players only
			if (players.size() < 2 || players.size() > 4) {
				throw new RuntimeException(
						"Supports mimimum of 2 and maximum of 4 Players.");
			} else {
				// initialize the board with empty entries
				this.initBoard(players);
			}
		} else {
			// when game is already in progress, resume the game but not start
			throw new RuntimeException("Game is Already in Progress");
		}
	}

	private void initBoard(List<Player> players) {
		// build a mXn board for game entries
		this.board = builder.buildBoard(Board.MAX_ROWS, Board.MAX_COLUMNS,
				players);
		// get new epoch counter
		this.epochCounter = EpochCounter.getEpochCounter(this.totalLevels);
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

	public void present() {
		this.board.display();
	}

}
