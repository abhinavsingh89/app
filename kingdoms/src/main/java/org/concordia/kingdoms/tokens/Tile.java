package org.concordia.kingdoms.tokens;

import java.io.Serializable;

import org.concordia.kingdoms.board.Component;

public class Tile implements Component, Serializable {

	private static final long serialVersionUID = 1L;

	private TileType type;

	private String name;

	private Integer value;

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
	public String toString() {
		return this.name + "(" + this.value + ")";
	}

	public static Tile newTile(TileType type, String name, Integer value) {
		return new Tile(type, name, value);
	}
}
