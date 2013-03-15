package org.concordia.kingdoms.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class is used to calculate the score for each level i.e. epoch
 * 
 * @author Team K
 * @since 1.1
 */
@XmlRootElement(name = "scoreCard")
@XmlAccessorType(XmlAccessType.FIELD)
public class ScoreCard {

	@XmlElement
	private int level;

	@XmlElementWrapper(name = "scores")
	@XmlElement(name = "score")
	private List<Score> scores;

	public int getLevel() {
		return this.level;
	}

	public List<Score> getScores() {
		return this.scores;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setScores(List<Score> scores) {
		this.scores = scores;
	}
}
