package org.concordia.kingdoms.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Lists;

/**
 * 
 * @author Team K
 * @since 1.0
 * 
 */
@XmlRootElement(name = "player")
@XmlAccessorType(XmlAccessType.FIELD)
public class Player {

	@XmlElement
	private String name;

	@XmlElement
	private Color chosenColor;

	@XmlElementWrapper(name = "scores")
	@XmlElement(name = "score")
	private List<Score> scores;

	@XmlElement
	private Tile startingTile;

	@XmlElement
	private boolean isStartingTileUsed;

	@XmlElement
	private String strategyName;

	@XmlElementWrapper(name = "castles")
	@XmlElement(name = "castle")
	private List<Castle> castles;

	@XmlElementWrapper(name = "possessedTiles")
	@XmlElement(name = "possessedTiles")
	private List<Tile> possessedTiles;

	public Player() {
		this.scores = Lists.newArrayList();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getChosenColor() {
		return chosenColor;
	}

	public void setChosenColor(Color chosenColor) {
		this.chosenColor = chosenColor;
	}

	public List<Score> getScores() {
		return scores;
	}

	public void setScores(List<Score> scores) {
		this.scores = scores;
	}

	public Tile getStartingTile() {
		return startingTile;
	}

	public void setStartingTile(Tile startingTile) {
		this.startingTile = startingTile;
	}

	public List<Castle> getCastles() {
		return castles;
	}

	public void setCastles(List<Castle> castles) {
		this.castles = castles;
	}

	public boolean isStartingTileUsed() {
		return isStartingTileUsed;
	}

	public void setStartingTileUsed(boolean isStartingTileUsed) {
		this.isStartingTileUsed = isStartingTileUsed;
	}

	public String getStrategyName() {
		return strategyName;
	}

	public void setStrategyName(String strategyName) {
		this.strategyName = strategyName;
	}

	public List<Tile> getPossessedTiles() {
		return possessedTiles;
	}

	public void setPossessedTiles(List<Tile> possessedTiles) {
		this.possessedTiles = possessedTiles;
	}

}
