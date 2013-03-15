package org.concordia.kingdoms.board;

import org.concordia.kingdoms.domain.Component;
import org.concordia.kingdoms.domain.Tile;
import org.concordia.kingdoms.domain.TileType;
/**
 * 
 * @author Team K
 * @since 1.1
 */
public class SimpleTileDecorator implements IDecorator {

	private Component component;
	/**
	 * Constructor for a SimpleTileDecorator
	 */
	public SimpleTileDecorator(Component component) {
		this.component = component;
	}

	public String getName() {
		return this.component.getName();
	}

	public Integer getValue() {
		if (this.component != null) {
			if (this.component instanceof Tile) {
				final Tile tile = (Tile) this.component;
				if (tile.getType().equals(TileType.RESOURCE)
						|| tile.getType().equals(TileType.HAZARD)) {
					return this.component.getValue();
				}
			}
		}
		return null;
	}

	public IDecorator setComponent(Component component) {
		this.component = component;
		return this;
	}

}
