package org.concordia.kingdoms.board;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
/**
 * This class is used to calculate the score for each level i.e. epoch
 * @author Team K
 * @since 1.1
 */
public class ScoreCard {

	private int level;

	private List<Score> scores;

	public ScoreCard(int level, List<Score> scores) {
		this.level = level;
		this.scores = Collections.unmodifiableList(scores);
	}

	public int getLevel() {
		return this.level;
	}

	public Collection<Score> getScores() {
		return this.scores;
	}
}
