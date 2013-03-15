package org.concordia.kingdoms.board.score;

import java.util.Collections;
import java.util.List;

import org.concordia.kingdoms.board.Score;

/**
 * This class is used to calculate the score for each level i.e. epoch
 * @author Team K
 * @since 1.1
 */
public class ScoreCard {

	private int level;

	private List<Score> scores;
	/**
	 * Constructor for a Score card
	 */
	public ScoreCard(int level, List<Score> scores) {
		this.level = level;
		this.scores = Collections.unmodifiableList(scores);
	}

	public int getLevel() {
		return this.level;
	}

	public List<Score> getScores() {
		return this.scores;
	}
}
