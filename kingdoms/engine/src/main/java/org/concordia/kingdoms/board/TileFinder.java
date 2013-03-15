package org.concordia.kingdoms.board;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.concordia.kingdoms.domain.Component;
import org.concordia.kingdoms.domain.Tile;
import org.concordia.kingdoms.domain.TileType;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
/**
 * This class is used to convert domain class object to Jaxb objects..
 * @author Team K
 * @since 1.1
 */
public class TileFinder {

	private Set<TileType> tileTypes;

	private Map<TileType, List<TDCoordinate>> tileAt;

	public TileFinder(final Set<TileType> tileTypes) {
		this.tileTypes = tileTypes;
		this.tileAt = Maps.newHashMap();
	}

	public void rememberCoordinate(Component component, TDCoordinate coordinate) {
		if (component instanceof Tile) {
			final TileType tileType = ((Tile) component).getType();
			// check to see if the tile is what we are looking for, if yes add
			// an entry
			if (this.tileTypes.contains(tileType)) {
				if (this.tileAt.get(tileType) != null) {
					this.tileAt.get(tileType).add(coordinate);
				} else {
					final List<TDCoordinate> coordinates = Lists.newArrayList();
					coordinates.add(coordinate);
					this.tileAt.put(tileType, coordinates);
				}
			}
		}
	}

	/**
	 * return the column index if the tile to be found is for a row , returns
	 * the row index if the tile to be found is for a column. -1 if the tile is
	 * not found
	 * 
	 * @param pos
	 *            position can be either a row number or column number where
	 *            tile tile need to be searched
	 * @param isRow
	 *            - if the tile to be searched is for a row or column
	 * @return - index where the tile is available in that particular row or
	 *         column. -1 in case not found
	 */
	public int tileAt(int pos, boolean isRow, int start, int end,
			TileType tileType) {

		final List<TDCoordinate> coordinates = this.tileAt.get(tileType);

		if (coordinates == null || coordinates.isEmpty()) {
			return -1;
		}
		if (isRow) {
			for (final TDCoordinate coordinate : coordinates) {
				if (coordinate != null) {
					if (pos == coordinate.getRow()) {
						int column = coordinate.getColumn();
						if (column >= start && column < end) {
							return column;
						}
					}
				}
			}
		} else {
			for (final TDCoordinate coordinate : coordinates) {
				if (coordinate != null) {
					if (pos == coordinate.getColumn()) {
						int row = coordinate.getRow();
						if (row >= start && row < end) {
							return row;
						}
					}
				}
			}
		}
		return -1;
	}

	public boolean hasWizardOrthogonal(TDCoordinate coordinate) {
		final List<TDCoordinate> coordinates = tileAt.get(TileType.WIZARD);
		if (coordinates == null || coordinates.isEmpty()) {
			return false;
		}
		for (TDCoordinate koordinate : coordinates) {

			int kRow = koordinate.getRow();
			int kColumn = koordinate.getColumn();

			int row = coordinate.getRow();
			int column = coordinate.getColumn();

			if (row == kRow && column == kColumn + 1) {
				return true;
			}
			if (row == kRow && column - 1 == kColumn) {
				return true;
			}
			if (row + 1 == kRow && column == kColumn) {
				return true;
			}
			if (row - 1 == kRow && column == kColumn) {
				return true;
			}
		}
		return false;
	}
}
