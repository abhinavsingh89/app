package org.concordia.kingdoms.tokens;

import java.io.Serializable;

import org.concordia.kingdoms.board.Component;

public class Castle implements Component, Serializable {

	private static final long serialVersionUID = 1L;

	private int rank;

	private Color color;

	private Castle(final int rank, Color color) {
		this.rank = rank;
		this.color = color;
	}

	public int getRank() {
		return this.rank;
	}

	public Color getColor() {
		return this.color;
	}

	public Integer getValue() {
		return this.rank;
	}

	public String getName() {
		return "C(" + color.getCode() + ")";
	}

	public static Castle newCastle(final int rank, Color color) {
		return new Castle(rank, color);
	}

}
