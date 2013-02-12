package org.concordia.kingdoms.board;

public class Entry {

	private Component component;

	private Entry(Component component) {
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
	public String toString() {
		if (this.component == null) {
			return "X        ";
		}
		return this.component.getName() + ":" + this.component.getValue();
	}

	public static Entry newEntry(final Component component) {
		return new Entry(component);
	}

	public static Entry newEntry() {
		return new Entry(null);
	}
}
