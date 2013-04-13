package org.concordia.kingdoms.board.score;

import java.io.BufferedReader;
import java.io.FileReader;

import org.concordia.kingdoms.board.IDecorator;
import org.concordia.kingdoms.domain.Component;
import org.concordia.kingdoms.domain.Tile;
import org.concordia.kingdoms.domain.TileType;
/**
 * 
 * @author Team K
 * @since 1.1
 */
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
			} else if (tile.getType().equals(TileType.HAZARD)) {
				return this.component.getValue();
			}
		}
		return 0;
	}

	public IDecorator setComponent(Component component) {
		this.component = component;
		return this;
	}

}
