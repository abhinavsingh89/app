package org.concordia.kingdoms.board;

import org.concordia.kingdoms.domain.Component;
import org.concordia.kingdoms.domain.Tile;
import org.concordia.kingdoms.domain.TileType;

public class DragonGoldMineDecorator implements IDecorator {

	IDecorator dragonDecorator;

	IDecorator goldMineDecorator;

	Component component;

	public DragonGoldMineDecorator() {
		this.dragonDecorator = new DragonDecorator(null);
		this.goldMineDecorator = new GoldMineDecorator(null);
	}

	public String getName() {
		return component.getName();
	}

	public Integer getValue() {
		if (component instanceof Tile) {
			final Tile tile = (Tile) component;
			if (tile.getType().equals(TileType.RESOURCE)) {
				return 0;
			} else if (tile.getType().equals(TileType.HAZARD)) {
				return goldMineDecorator.setComponent(component).getValue();
			}
		}
		return 0;
	}

	public IDecorator setComponent(Component component) {
		this.component = component;
		return this;
	}

}
