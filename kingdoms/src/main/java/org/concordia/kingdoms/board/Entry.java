package org.concordia.kingdoms.board;

import org.concordia.kingdoms.exceptions.GameRuleException;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

/**
 * Keeping the track of every board entry.
 * 
 * @author Team K
 * @since 1.0
 * 
 */

@AutoProperty
public class Entry {

	private int row;

	private int column;

	private Component component;

	/**
	 * Constructor for entry
	 * 
	 * @param row
	 *            - row number
	 * @param column
	 *            - row column
	 * @param component
	 * 
	 */
	private Entry(int row, int column, Component component) {
		this.row = row;
		this.column = column;
		this.component = component;
	}

	/**
	 * method used for returning the component
	 * @return component
	 */
	public Component getComponent() {
		return this.component;
	}

	/**
	 * method used for setting the component
	 * @param component
	 * @throws GameRuleException
	 */
	public void setComponent(Component component) throws GameRuleException {
		if (!isEmpty()) {
			throw new GameRuleException("Entry is already filled");
		}
		this.component = component;
	}

	/**
	 * method used to check if the function is empty or not. 
	 * @return true/false
	 */
	public boolean isEmpty() {
		return this.component == null;
	}

	/**
	 * method used to compare objects
	 * @return true/false
	 */
	@Override
	public boolean equals(Object o) {
		return Pojomatic.equals(this, o);
	}

	/**
	 * method used to compare hashcode
	 * @return true/false
	 */
	@Override
	public int hashCode() {
		return Pojomatic.hashCode(this);
	}

	/**
	 * method used to create the component if it is null and return it as string
	 * @return component.toString
	 */
	@Override
	public String toString() {
		if (this.component == null) {
			return "X         ";
		}
		return getOutputStr(this.component.toString());
	}

	/**
	 * method used return input buffers
	 * @param input
	 * @return stringBuffer (sb)
	 */
	public String getOutputStr(String input) {
		StringBuffer sb = new StringBuffer(input);
		for (int i = input.length(); i < 10; i++) {
			sb.append(" ");
		}
		return sb.toString();
	}

	/**
	 * public method used for making a new entry
	 * @param row
	 * @param column
	 * @return entry
	 */
	public static Entry newEntry(int row, int column) {
		return newEntry(row, column, null);
	}

	/**
	 * public method used for making a new entry 
	 * @param row
	 * @param column
	 * @param component
	 * @return entry
	 */
	public static Entry newEntry(int row, int column, final Component component) {
		return new Entry(row, column, component);
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

}
