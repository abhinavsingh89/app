package org.concordia.kingdoms.board.score;

import org.concordia.kingdoms.board.IDecorator;
import org.concordia.kingdoms.domain.Component;
import org.concordia.kingdoms.domain.Tile;
import org.concordia.kingdoms.domain.TileType;
/**
 * 
 * @author Team K
 * @since 1.1
 */
public class GoldMineDecorator implements IDecorator {

	private Component component;

	public GoldMineDecorator(Component component) {
		this.component = component;
	}

	public Integer getValue() {
		if (this.component == null) {
			return null;
		}
		if (this.component instanceof Tile) {
			final Tile tile = (Tile) this.component;
			if (tile.getType().equals(TileType.RESOURCE)
					|| tile.getType().equals(TileType.HAZARD)) {
				return 2 * this.component.getValue();
			}
		}
		return 0;
	}

	public String getName() {
		return this.component.getName();
	}

	public IDecorator setComponent(Component component) {
		this.component = component;
		return this;
	}

}
