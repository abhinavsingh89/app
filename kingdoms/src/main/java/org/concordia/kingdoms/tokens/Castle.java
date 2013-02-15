package org.concordia.kingdoms.tokens;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.concordia.kingdoms.board.Component;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

/**
 * Each castle has a rank , indicated by the number of towers on the castle. The
 * higher a castle’s rank, the more influential it is in a kingdom.
 * 
 * @author Team K
 * @since 1.0
 */
@AutoProperty
@XmlRootElement(name = "castle")
public class Castle implements Component, Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement
	private int rank;

	@XmlAttribute
	private Color color;

	private Castle() {
		this(0, null);
	}

	/**
	 * Constructor for a castle
	 * 
	 * @param rank
	 *            - chosen rank of the castle.
	 * @param color
	 *            - chosen color of the castle
	 * @return castle
	 */
	private Castle(final int rank, Color color) {
		this.rank = rank;
		this.color = color;
	}

	/**
	 * @return color associated with this Castle
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * @return rank associated with this Castle
	 */
	public Integer getValue() {
		return this.rank;
	}

	/**
	 * default name for a castle is CASTLE
	 */
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

	/**
	 * @return A string representing Castle in a maximum of 10 string literal
	 */
	@Override
	public String toString() {
		return "C:" + this.getName().substring(0, 3) + ":" + this.getValue()
				+ ":" + this.getColor().getCode();
	}

	public static Castle newCastle(final int rank, Color color) {
		return new Castle(rank, color);
	}
}
