package org.concordia.kingdoms.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

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

	@XmlElement
	private int score;

	@XmlElement
	private Tile startingTile;

	@XmlElementWrapper(name = "castles")
	@XmlElement(name = "castle")
	private List<Castle> castles;

	public Player() {

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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
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
}
