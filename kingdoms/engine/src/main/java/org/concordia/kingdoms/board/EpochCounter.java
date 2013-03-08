package org.concordia.kingdoms.board;

/**
 * maintains the game status related to level
 * @author Team K
 * @since 1.0
 *
 */
import java.util.Map;

import org.concordia.kingdoms.Player;
import org.concordia.kingdoms.exceptions.GameRuleException;

public class EpochCounter {

	private int currentLevel;

	private int totalLevels;

	private Map<Integer, Player> winners;

	/**
	 * initialization for number of levels i.e. setting totalLevels
	 * 
	 * @param totalLevels
	 */
	public EpochCounter(int totalLevels) {
		this.totalLevels = totalLevels;
		this.currentLevel = 1;
	}

	/**
	 * method for setting the current level and total levels
	 * 
	 * @param currentLevel
	 * @param totalLevels
	 */
	public EpochCounter(int currentLevel, int totalLevels) {
		this.currentLevel = currentLevel;
		this.totalLevels = totalLevels;
	}

	/**
	 * method used for returning the winner of the level
	 * 
	 * @param level
	 * @return winner
	 */
	public Player winner(int level) {
		return this.winners.get(level);
	}

	/**
	 * method used for moving the epoch to next level.
	 * 
	 * @throws GameRuleException
	 */
	public void goNextLevel() throws GameRuleException {
		if (!isNextAvailable()) {
			throw new GameRuleException("Reached Last Level");
		}
		this.currentLevel++;
	}

	/**
	 * method used for checking if the next level is available.
	 * 
	 * @return true/false
	 */
	public boolean isNextAvailable() {
		return this.currentLevel + 1 <= this.totalLevels;
	}

	/**
	 * method used for fetching current level.
	 * 
	 * @return current level;
	 */
	public int getCurrentLevel() {
		return currentLevel;
	}
}
