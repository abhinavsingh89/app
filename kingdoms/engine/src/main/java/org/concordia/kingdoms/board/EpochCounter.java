package org.concordia.kingdoms.board;

/**
 * maintains the game status related to level
 * @author Team K
 * @since 1.0
 *
 */
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.concordia.kingdoms.Player;
import org.concordia.kingdoms.board.score.ScoreCard;
import org.concordia.kingdoms.domain.Color;
import org.concordia.kingdoms.exceptions.GameRuleException;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class EpochCounter {

	private int currentLevel;

	private int totalLevels;

	private List<ScoreCard> scoreCards;

	/**
	 * initialization for number of levels i.e. setting totalLevels
	 * 
	 * @param totalLevels
	 */
	public EpochCounter(int totalLevels) {
		this(1, totalLevels);
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
		this.scoreCards = Lists.newArrayList();
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

	public void addNewScore(Player<?> players, Map<Color, Score> newScore) {
		Iterator<Color> itr = newScore.keySet().iterator();
		final List<Score> scores = Lists.newArrayList();
		while (itr.hasNext()) {
			Color color = itr.next();
			Score score = newScore.get(color);
			scores.add(score);
		}
		Collections.sort(scores, Score.ScoreComparator.INSTANCE);
		Map<Color, Score> sortedScores = Maps.newHashMap();

		for (Score score : scores) {
			sortedScores.put(score.getColor(), score);
		}
		ScoreCard newScoreCard = new ScoreCard(this.scoreCards.size() + 1,
				scores);
		this.scoreCards.add(newScoreCard);
	}

	public int getTotalLevels() {
		return totalLevels;
	}

	public void setTotalLevels(int totalLevels) {
		this.totalLevels = totalLevels;
	}

	public List<ScoreCard> getScoreCards() {
		return scoreCards;
	}

	public void setScoreCards(List<ScoreCard> scoreCards) {
		this.scoreCards = scoreCards;
	}

	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}

}
