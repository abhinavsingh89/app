package org.concordia.kingdoms.board;

import org.concordia.kingdoms.domain.Component;
import org.concordia.kingdoms.exceptions.GameRuleException;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

/**
 * An Entry is a place holder for a Component. Each entry is recognized with its
 * coordinate and the component. It's up to the client code to maintain the
 * uniqueness of each entry.
 * 
 * @author Team K
 * @since 1.0
 * 
 */

@AutoProperty
public class Entry<T extends ICoordinate> {

	private T coordinate;

	private Component component;

	private DisasterType disasterType;

	/**
	 * constructor-every entry must have a coordinate
	 * 
	 * @param row
	 * @param column
	 * @param component
	 */
	public Entry(T coordinate, Component component) {
		this.coordinate = coordinate;
		this.component = component;
	}

	public T getCoordinate() {
		return this.coordinate;
	}

	/**
	 * method used for returning the component
	 * 
	 * @return {@link Component}
	 */
	public Component getComponent() {
		return this.component;
	}

	/**
	 * method used for setting the component, an entry can have the component
	 * placed only once
	 * 
	 * @param component
	 *            - a component to put in this entry
	 * @throws GameRuleException
	 *             - if the entry is already filled an exception will be thrown
	 */
	public void setComponent(Component component) throws GameRuleException {
		if (!isEmpty()) {
			throw new GameRuleException("Entry is already filled");
		}
		this.component = component;
	}

	Component setNull() {
		Component removedComponent = this.component;
		this.component = null;
		return removedComponent;
	}

	/**
	 * @return true if this entry holds a component otherwise false
	 */
	public boolean isEmpty() {
		return this.component == null;
	}

	/**
	 * handled by pojomatic
	 */
	@Override
	public boolean equals(Object o) {
		return Pojomatic.equals(this, o);
	}

	/**
	 * handled by pojomatic
	 */
	@Override
	public int hashCode() {
		return Pojomatic.hashCode(this);
	}

	/**
	 * representing the component as "X          " if entry {@link #isEmpty()},
	 * otherwise represented as a short cut code in 10 characters
	 */
	@Override
	public String toString() {
		if (this.isEmpty()) {
			return "X          ";
		}
		return getOutputStr(this.component.toString());
	}

	/**
	 * appends 10 spaces to the given input string
	 */
	public String getOutputStr(String input) {
		final StringBuffer sb = new StringBuffer(input);
		for (int i = input.length(); i < 11; i++) {
			sb.append(" ");
		}
		return sb.toString();
	}

	public DisasterType getDisasterType() {
		return disasterType;
	}

	public void setDisasterType(DisasterType disasterType) {
		this.disasterType = disasterType;
	}

}
