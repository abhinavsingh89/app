package org.concordia.kingdoms.board;

import org.concordia.kingdoms.domain.Component;
import org.concordia.kingdoms.domain.Tile;
import org.concordia.kingdoms.domain.TileType;

public class DragonDecorator implements IDecorator {

	private Component component;

	public DragonDecorator(Component component) {
		this.component = component;
	}

	public String getName() {
		return this.component.getName();
	}

	public Integer getValue() {
		if (this.component == null) {
			return null;
		}
		if (this.component instanceof Tile) {
			final Tile tile = (Tile) this.component;
			if (tile.getType().equals(TileType.RESOURCE)) {
				return 0;
			}
		}
		return this.component.getValue();
	}

	public IDecorator setComponent(Component component) {
		this.component = component;
		return this;
	}

}
