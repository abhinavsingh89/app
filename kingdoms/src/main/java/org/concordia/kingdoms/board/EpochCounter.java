package org.concordia.kingdoms.board;
/**
 * 
 * @author Team K
 * @since 1.0
 *
 */
import java.util.Map;

import org.concordia.kingdoms.Player;
import org.concordia.kingdoms.exceptions.GameRuleException;
/**
 * 
 * Constructor for EpochCounter
 */
public class EpochCounter {

	private int currentLevel;

	private int totalLevels;

	private Map<Integer, Player> winners;

	public EpochCounter(int totalLevels) {
		this.totalLevels = totalLevels;
		this.currentLevel = 1;
	}

	public EpochCounter(int currentLevel, int totalLevels) {
		this.currentLevel = currentLevel;
		this.totalLevels = totalLevels;
	}

	public Player winner(int level) {
		return this.winners.get(level);
	}

	public void goNextLevel() throws GameRuleException {
		if (!isNextAvailable()) {
			throw new GameRuleException("Reached Last Level");
		}
		this.currentLevel++;
	}

	public boolean isNextAvailable() {
		return this.currentLevel + 1 <= this.totalLevels;
	}

	public int getCurrentLevel() {
		return currentLevel;
	}
}
