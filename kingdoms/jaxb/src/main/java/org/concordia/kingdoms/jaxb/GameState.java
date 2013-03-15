package org.concordia.kingdoms.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Team K
 * @since 1.1
 */
@XmlRootElement(name = "kingdoms", namespace = "org.concordia.kingdoms")
@XmlAccessorType(XmlAccessType.FIELD)
public class GameState {

	@XmlElement(name = "rows")
	int MAX_ROWS;

	@XmlElement(name = "columns")
	int MAX_COLUMNS;

	@XmlElement(name = "tokensCount")
	int componentsOnBoard;

	@XmlElementWrapper(name = "entries")
	@XmlElement(name = "entry")
	List<Entry> entries;

	@XmlElementWrapper(name = "tilesBank")
	@XmlElement(name = "tile")
	List<Tile> tileBank;

	@XmlElementWrapper(name = "players")
	@XmlElement(name = "player")
	List<Player> players;

	@XmlElement(name = "epoch")
	EpochCounter epochCounter;

	String fileName;

	public GameState() {
	}

	public int getMAX_ROWS() {
		return MAX_ROWS;
	}

	public void setMAX_ROWS(int mAX_ROWS) {
		MAX_ROWS = mAX_ROWS;
	}

	public int getMAX_COLUMNS() {
		return MAX_COLUMNS;
	}

	public void setMAX_COLUMNS(int mAX_COLUMNS) {
		MAX_COLUMNS = mAX_COLUMNS;
	}

	public int getComponentsOnBoard() {
		return componentsOnBoard;
	}

	public void setComponentsOnBoard(int componentsOnBoard) {
		this.componentsOnBoard = componentsOnBoard;
	}

	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

	public List<Tile> getTileBank() {
		return tileBank;
	}

	public void setTileBank(List<Tile> tileBank) {
		this.tileBank = tileBank;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public EpochCounter getEpochCounter() {
		return epochCounter;
	}

	public void setEpochCounter(EpochCounter epochCounter) {
		this.epochCounter = epochCounter;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}
}
