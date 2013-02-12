package org.concordia.kingdoms.board;

import java.util.Map;

import org.concordia.kingdoms.Player;

public class EpochCounter {

	private int currentLevel;

	private int totalLevels;

	private Map<Integer, Player> winners;

	private static EpochCounter INSTANCE;

	private EpochCounter(int totalLevels) {
		this.totalLevels = totalLevels;
		this.currentLevel = 1;
	}

	public Player winner(int level) {
		return this.winners.get(level);
	}

	public void goNextLevel() {
		if (!isNextAvailable()) {
			throw new RuntimeException("Reached Last Level");
		}
		this.currentLevel++;
	}

	public boolean isNextAvailable() {
		return this.currentLevel + 1 <= this.totalLevels;
	}

	public static EpochCounter getEpochCounter(int totalLevels) {
		if (INSTANCE != null) {
			return new EpochCounter(totalLevels);
		}
		return INSTANCE;

	}
}
