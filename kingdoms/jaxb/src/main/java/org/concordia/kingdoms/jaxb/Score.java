package org.concordia.kingdoms.jaxb;

import java.util.Comparator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class is used to calculate the total score by adding rowScore and
 * columnScore.
 * 
 * @author Team K
 * @since 1.1
 */
@XmlRootElement(name = "score")
@XmlAccessorType(XmlAccessType.FIELD)
public class Score {

	@XmlElement
	private int rowScore;

	@XmlElement
	private int columnScore;

	@XmlElement
	private Color color;

	public Score() {
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

	public void setColor(Color color) {
		this.color = color;
	}

	public void setColumnScore(int columnScore) {
		this.columnScore = columnScore;
	}

	public void setRowScore(int rowScore) {
		this.rowScore = rowScore;
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