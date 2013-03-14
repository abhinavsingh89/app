package org.concordia.kingdoms.board;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
