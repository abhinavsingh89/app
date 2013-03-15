package org.concordia.kingdoms.jaxb;

/**
 * maintains the game status related to level
 * @author Team K
 * @since 1.0
 *
 */
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "epoch")
@XmlAccessorType(XmlAccessType.FIELD)
public class EpochCounter {

	@XmlAttribute
	private int currentLevel;

	@XmlAttribute
	private int totalLevels;

	@XmlElementWrapper(name = "scoreCards")
	@XmlElement(name = "scoreCard")
	private List<ScoreCard> scoreCards;

	public int getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
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

}
