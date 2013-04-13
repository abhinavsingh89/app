package org.concordia.kingdoms.strategies;

import java.util.Iterator;
import java.util.List;

import org.concordia.kingdoms.board.Board;
import org.concordia.kingdoms.board.Entry;
import org.concordia.kingdoms.board.TDCoordinate;
import org.concordia.kingdoms.board.TDMatrix;
import org.concordia.kingdoms.board.TileFinder;
import org.concordia.kingdoms.domain.Component;
import org.concordia.kingdoms.domain.Tile;
import org.concordia.kingdoms.domain.TileType;

import com.google.common.collect.Lists;

public class VirtualTDMatrix extends TDMatrix {

	private List<List<Entry<TDCoordinate>>> entriesList;

	public VirtualTDMatrix(Iterator<Entry<TDCoordinate>> entries, int rows,
			int columns) {
		super(5, 6);
		this.entriesList = resolveEntries(entries);
		setEntries(entriesList);
	}

	private List<List<Entry<TDCoordinate>>> resolveEntries(
			final Iterator<Entry<TDCoordinate>> itr) {
		List<List<Entry<TDCoordinate>>> entries = Lists.newArrayList();
		while (itr.hasNext()) {
			List<Entry<TDCoordinate>> columns = Lists.newArrayList();
			int i = 0;
			while (i < Board.MAX_COLUMNS) {
				columns.add(itr.next());
				i++;
			}
			entries.add(columns);
		}
		return entries;
	}

	@Override
	public TileFinder getTileFinder() {
		return new TileFinder(null) {
			@Override
			public boolean hasDragon(int start, int end, int rowOrColumnNumber,
					boolean isRow) {
				boolean dragon = false;
				if (isRow) {
					List<Entry<TDCoordinate>> columns = entriesList
							.get(rowOrColumnNumber);
					for (int i = start; i < end; i++) {
						Component comp = columns.get(i).getComponent();
						dragon = isDragonTile(comp);
						if (dragon) {
							break;
						}
					}
				} else {
					if (start < 0) {
						start = 0;
					}
					if (end < 0) {
						end = Board.MAX_ROWS;
					}
					for (int i = start; i < end; i++) {

						Component comp = entriesList.get(start)
								.get(rowOrColumnNumber).getComponent();
						dragon = isDragonTile(comp);
					}
				}
				return dragon;
			}

			private boolean isDragonTile(Component comp) {
				boolean dragon = false;
				if (comp != null) {
					if (comp instanceof Tile) {
						Tile tile = (Tile) comp;
						if (tile.getType().equals(TileType.DRAGON)) {
							dragon = true;
						}
					}
				}
				return dragon;
			}
		};
	}
}
