package org.concordia.kingdoms.board;

public class Score {

	private int rowScore;

	private int columnScore;

	Score incrementRowScoreBy(int score) {
		this.rowScore += score;
		return this;
	}

	Score incrementColumnScoreBy(int score) {
		this.columnScore += score;
		return this;
	}

	public int score() {
		return rowScore + columnScore;
	}
}