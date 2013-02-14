package org.concordia.kingdoms.tokens;

import java.io.Serializable;

import org.concordia.kingdoms.board.Component;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

/**
 * 
 * @author PVRA
 * @version 1.0-SNAPSHOT
 *
 */
@AutoProperty
public class Castle implements Component, Serializable {

	private static final long serialVersionUID = 1L;

	private int rank;

	private Color color;
	/**
	 * Constructor for a castle
	 * @param rank -  chosen rank of the castle.
	 * @param color - chosen color of the castle
	 * @return castle
	 */
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
		return "CASTLE";
	}

	@Override
	public boolean equals(Object o) {
		return Pojomatic.equals(this, o);
	}

	@Override
	public int hashCode() {
		return Pojomatic.hashCode(this);
	}

	@Override
	public String toString() {
		return "C:" + this.getName().substring(0, 3) + ":" + this.getRank()
				+ ":" + this.getColor().getCode();
	}

	public static Castle newCastle(final int rank, Color color) {
		return new Castle(rank, color);
	}

}
