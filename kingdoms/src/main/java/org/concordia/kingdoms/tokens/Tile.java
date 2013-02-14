package org.concordia.kingdoms.tokens;

import java.io.Serializable;

import org.concordia.kingdoms.board.Component;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

/**
 * This class represents different tiles used in the game
 * 
 * @author PVRA
 * @version 1.0-SNAPSHOT
 * 
 */
@AutoProperty
public class Tile implements Component, Serializable {

	private static final long serialVersionUID = 1L;

	private TileType type;

	private String name;

	private Integer value;

	/**
	 * constructor for Tile
	 * 
	 * @param type
	 *            - It represents type of tile
	 * @param name
	 *            - It represents name of tile
	 * @param value
	 *            - It represents value of tile
	 */
	private Tile(TileType type, String name, Integer value) {
		this.type = type;
		this.name = name;
		this.value = value;
	}

	public Integer getValue() {
		return this.value;
	}

	public String getName() {
		return this.name;
	}

	public TileType getType() {
		return this.type;
	}

	@Override
	public boolean equals(Object o) {
		return Pojomatic.equals(this, o);
	}

	@Override
	public int hashCode() {
		return Pojomatic.hashCode(this);
	}

	public String showTile() {
		return Pojomatic.toString(this);
	}

	@Override
	public String toString() {
		return "T:" + this.getName().substring(0, 3) + ":" + this.getValue()
				+ ":" + this.getType().toString().charAt(0);
	}

	public static Tile newTile(TileType type, String name, Integer value) {
		return new Tile(type, name, value);
	}

}
