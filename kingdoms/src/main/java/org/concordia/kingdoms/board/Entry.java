package org.concordia.kingdoms.board;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@AutoProperty
public class Entry {

	private int row;

	private int column;

	private Component component;

	private Entry(int row, int column, Component component) {
		this.row = row;
		this.column = column;
		this.component = component;
	}

	public Component getComponent() {
		return this.component;
	}

	public void setComponent(Component component) {
		if (!isEmpty()) {
			throw new RuntimeException("Entry is already filled");
		}
		this.component = component;
	}

	public boolean isEmpty() {
		return this.component == null;
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
		if (this.component == null) {
			return "X        ";
		}
		return this.component.toString();
	}

	public static Entry newEntry(int row, int column) {
		return newEntry(row, column, null);
	}

	public static Entry newEntry(int row, int column, final Component component) {
		return new Entry(row, column, component);
	}

}
