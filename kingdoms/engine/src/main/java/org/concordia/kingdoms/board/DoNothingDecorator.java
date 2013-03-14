package org.concordia.kingdoms.board;

import org.concordia.kingdoms.domain.Component;

public class DoNothingDecorator implements IDecorator {

	private Component component;

	public DoNothingDecorator(Component component) {
		this.component = component;
	}

	public String getName() {
		return this.component.getName();
	}

	public Integer getValue() {
		if (this.component != null) {
			return this.component.getValue();
		}
		return null;
	}

	public IDecorator setComponent(Component component) {
		this.component = component;
		return this;
	}

}
