package org.concordia.kingdoms.board;

import java.util.Comparator;

import org.concordia.kingdoms.domain.Color;
/**
 * This class is used to calculate the total score by adding rowScore and columnScore.
 * @author Team K
 * @since 1.1
 */
public class Score {

	private int rowScore;

	private int columnScore;

	private Color color;
	/**
	 * Constructor for a score
	 * 
	 * @param color
	 *            - calculating score according to color
	 */
	public Score(Color color) {
		this.color = color;
	}

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

	public int getRowScore() {
		return rowScore;
	}

	public int getColumnScore() {
		return columnScore;
	}

	public Color getColor() {
		return color;
	}

	public static class ScoreComparator implements Comparator<Score> {

		public static final ScoreComparator INSTANCE = new ScoreComparator();

		public int compare(Score o1, Score o2) {
			return o1.score() - o2.score();
		}

	}
}