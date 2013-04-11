package org.concordia.kingdoms.strategies;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.concordia.kingdoms.Player;
import org.concordia.kingdoms.board.Board;
import org.concordia.kingdoms.board.Entry;
import org.concordia.kingdoms.board.IEntriesAware;
import org.concordia.kingdoms.board.TDCoordinate;
import org.concordia.kingdoms.domain.Castle;
import org.concordia.kingdoms.domain.Tile;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * This class is used to implement Maximize strategy
 * 
 * @author Team K
 * @since 1.1
 */
public class MaximizeStrategy implements IStrategy<TDCoordinate>,
		IEntriesAware<TDCoordinate> {

	private VirtualTDMatrix tdMatrix;

	public Entry<TDCoordinate> getEntry(Player<TDCoordinate> player,
			List<Tile> tiles, List<Castle> castles,
			List<TDCoordinate> emptyCoordinates) {

		// when board is empty place a castle somewhere on the board
		if (emptyCoordinates.size() == 30) {
			Castle castle = castles.get(0);
			int randomIdx = new Random().nextInt(emptyCoordinates.size());
			// arbitrary position
			TDCoordinate coordinate = emptyCoordinates.get(randomIdx);
			return new Entry<TDCoordinate>(coordinate, castle);
		}

		List<List<Integer>> rowsScores = Lists.newArrayList();

		for (int i = 0; i < Board.MAX_ROWS; i++) {
			List<Integer> rowScores = Lists.newArrayList();
			tdMatrix.getTilesScore(0, Board.MAX_COLUMNS, i, true, rowScores);
			rowsScores.add(rowScores);
		}

		List<List<Integer>> columnsScores = Lists.newArrayList();

		for (int i = 0; i < Board.MAX_COLUMNS; i++) {
			List<Integer> columnScores = Lists.newArrayList();
			tdMatrix.getTilesScore(0, Board.MAX_ROWS, i, false, columnScores);
			columnsScores.add(columnScores);
		}

		Map<TDCoordinate, Integer> coordinateScores = Maps.newHashMap();

		for (TDCoordinate coordinate : emptyCoordinates) {

			int row = coordinate.getRow();
			int column = coordinate.getColumn();

			int rowScore = 0;
			int columnScore = 0;

			List<Integer> rowScores = rowsScores.get(row);

			int rowMountains = rowScores.size() - 1;

			if (rowMountains == 0) {
				// there is no mountain in that row
				rowScore = rowScores.get(0);
			} else {
				int firstMountain = tdMatrix.getTileFinder().mountainAt(0,
						Board.MAX_COLUMNS, row, true);
				if (rowMountains == 1) {

					if (column < firstMountain) {
						rowScore = rowScores.get(0);
					} else {
						rowScore = rowScores.get(1);
					}
				} else {
					int secondMountain = tdMatrix.getTileFinder().mountainAt(
							firstMountain + 1, Board.MAX_COLUMNS, row, true);
					if (column < secondMountain) {
						rowScore = rowScores.get(1);
					} else {
						rowScore = rowScores.get(2);
					}
				}
			}

			// ////////////////////////////////////////////
			List<Integer> columnScores = columnsScores.get(column);

			int columnMountains = columnScores.size() - 1;

			if (columnMountains == 0) {
				// there is no mountain in that column
				columnScore = columnScores.get(0);
			} else {
				int firstMountain = tdMatrix.getTileFinder().mountainAt(0,
						Board.MAX_ROWS, column, false);
				if (columnMountains == 1) {

					if (row < firstMountain) {
						columnScore = columnScores.get(0);
					} else {
						columnScore = columnScores.get(1);
					}
				} else {
					int secondMountain = tdMatrix.getTileFinder().mountainAt(
							firstMountain + 1, Board.MAX_ROWS, column, false);
					if (row < secondMountain) {
						columnScore = columnScores.get(1);
					} else {
						columnScore = columnScores.get(2);
					}
				}
			}

			coordinateScores.put(coordinate, rowScore + columnScore);
		}

		TDCoordinate maxCoordinate = getMaxCoordinate(coordinateScores);

		if (castles.size() != 0) {
			Castle castle = castles.remove(castles.size() - 1);
			Entry<TDCoordinate> entry = new Entry<TDCoordinate>(maxCoordinate,
					castle);
			return entry;
		}

		Tile startTile = player.getStartingTile();

		if (tiles.size() != 0 && startTile.getValue() != null
				&& startTile.getValue() > 0) {
			Entry<TDCoordinate> entry = new Entry<TDCoordinate>(maxCoordinate,
					player.getStartingTile());
			return entry;
		} else {
			List<Tile> possessedTiles = player.getPossessedTiles();
			Tile retTile = null;
			TDCoordinate coordinate = null;

			if (!player.isStartingTileUsed()) {
				possessedTiles.add(player.getStartingTile());
			}

			int max = 0;

			for (Tile possessedTile : possessedTiles) {
				if (possessedTile.getValue() > max) {
					retTile = possessedTile;
					max = possessedTile.getValue();
				}
			}
			if (retTile != null) {
				coordinate = getMaxCoordinate(coordinateScores);
			} else {
				int min = 0;
				for (Tile possessedTile : possessedTiles) {
					if (possessedTile.getValue() <= min) {
						retTile = possessedTile;
						min = possessedTile.getValue();
					}
				}
				coordinate = getMinCoordinate(coordinateScores);
			}
			Entry<TDCoordinate> entry = new Entry<TDCoordinate>(coordinate,
					retTile);
			return entry;
		}

	}

	private TDCoordinate getMaxCoordinate(
			Map<TDCoordinate, Integer> coordinateScores) {

		int min = Collections.min(coordinateScores.values());
		int max = Collections.max(coordinateScores.values());

		Iterator<TDCoordinate> itr = coordinateScores.keySet().iterator();
		TDCoordinate maxCoordinate = null;
		int maxValue = min;
		while (itr.hasNext()) {
			TDCoordinate tdCoordinate = itr.next();
			int value = coordinateScores.get(tdCoordinate);

			if (maxValue <= value) {
				maxValue = value;
				maxCoordinate = tdCoordinate;
			}
		}
		return maxCoordinate;
	}

	private TDCoordinate getMinCoordinate(
			Map<TDCoordinate, Integer> coordinateScores) {
		int max = Collections.max(coordinateScores.values());
		Iterator<TDCoordinate> itr = coordinateScores.keySet().iterator();
		TDCoordinate minCoordinate = null;
		int minValue = max;
		while (itr.hasNext()) {
			TDCoordinate tdCoordinate = itr.next();
			int value = coordinateScores.get(tdCoordinate);
			if (minValue >= value) {
				minValue = value;
				minCoordinate = tdCoordinate;
			}
		}
		return minCoordinate;
	}

	private void hasDragon(TDCoordinate coordinate) {
		// find if there is a dragon if no mountain in between
		// avoid this coordinate
		// incase ran out of all possible coordinates and left with this place,
		// go ahead and place here
	}

	public void setEntries(Iterator<Entry<TDCoordinate>> entries) {
		this.tdMatrix = new VirtualTDMatrix(entries, 5, 6);
	}

}
