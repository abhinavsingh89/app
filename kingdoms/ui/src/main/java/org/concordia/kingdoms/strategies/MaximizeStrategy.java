package org.concordia.kingdoms.strategies;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.concordia.kingdoms.Player;
import org.concordia.kingdoms.board.Board;
import org.concordia.kingdoms.board.Entry;
import org.concordia.kingdoms.board.IEntriesAware;
import org.concordia.kingdoms.board.TDCoordinate;
import org.concordia.kingdoms.domain.Castle;
import org.concordia.kingdoms.domain.Tile;
import org.concordia.kingdoms.domain.TileType;

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

		Map<Integer, List<TDCoordinate>> coordinateScores = Maps.newHashMap();

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
			int tScore = rowScore + columnScore;
			List<TDCoordinate> coords = coordinateScores.get(tScore);
			if (coords == null) {
				coords = Lists.newArrayList();
			}
			coords.add(coordinate);
			coordinateScores.put(tScore, coords);
		}

		List<TDCoordinate> goodOptions = Lists.newArrayList();
		List<TDCoordinate> badOptions = Lists.newArrayList();
		resolveMaxCoord(coordinateScores, goodOptions, badOptions);
		TDCoordinate goodOption = null;
		TDCoordinate badOption = null;
		if (goodOptions.isEmpty()) {
			if (badOptions.isEmpty()) {
				// no way
			} else {
				badOption = badOptions.get(0);
			}
		} else {
			goodOption = goodOptions.get(0);
		}

		TDCoordinate coordinate = null;

		if (goodOption == null) {
			if (badOption == null) {
				// no possible move
			} else {
				coordinate = badOptions.get(0);
			}
		} else {
			coordinate = goodOptions.get(0);
		}

		if (!castles.isEmpty()) {
			Castle castle = castles.remove(castles.size() - 1);
			Entry<TDCoordinate> entry = new Entry<TDCoordinate>(coordinate,
					castle);
			return entry;
		}

		List<Tile> possessedTiles = player.getPossessedTiles();
		Tile retTile = null;

		if (!player.isStartingTileUsed()) {
			possessedTiles.add(player.getStartingTile());
		}

		retTile = getMaxTile(possessedTiles);

		if (retTile != null) {
			if (goodOption == null) {
				if (badOption == null) {
					// no possible move
				} else {
					coordinate = badOptions.get(0);
				}
			} else {
				coordinate = goodOptions.get(0);
			}
		}

		Entry<TDCoordinate> entry = new Entry<TDCoordinate>(coordinate, retTile);
		return entry;
	}

	private Tile getMinTile(List<Tile> possessedTiles) {
		int min = 6;
		Tile retTile = null;
		for (Tile possessedTile : possessedTiles) {
			if (possessedTile.getValue() == null) {
				retTile = possessedTile;
				continue;
			}
			if (possessedTile.getValue() <= min) {
				min = possessedTile.getValue();
			}
		}
		return retTile;
	}

	private Tile getMaxTile(List<Tile> possessedTiles) {
		int max = 0;
		Tile retTile = null;
		for (Tile possessedTile : possessedTiles) {
			if (possessedTile.getValue() == null
					&& possessedTile.getType() != TileType.DRAGON) {
				retTile = possessedTile;
				continue;
			}
			if (possessedTile.getValue() > max) {
				retTile = possessedTile;
				max = possessedTile.getValue();
			}
		}
		return retTile;
	}

	// private TDCoordinate getMaxCoordinate(
	// Map<TDCoordinate, Integer> coordinateScores) {
	//
	// int min = Collections.min(coordinateScores.values());
	// int max = Collections.max(coordinateScores.values());
	//
	// Iterator<TDCoordinate> itr = coordinateScores.keySet().iterator();
	// TDCoordinate maxCoordinate = null;
	// int maxValue = min;
	// while (itr.hasNext()) {
	// TDCoordinate tdCoordinate = itr.next();
	// int value = coordinateScores.get(tdCoordinate);
	//
	// if (maxValue <= value) {
	// maxValue = value;
	// maxCoordinate = tdCoordinate;
	// }
	// }
	// return maxCoordinate;
	// }

	// private TDCoordinate getMinCoordinate(
	// Map<TDCoordinate, Integer> coordinateScores) {
	// int max = Collections.max(coordinateScores.values());
	// Iterator<TDCoordinate> itr = coordinateScores.keySet().iterator();
	// TDCoordinate minCoordinate = null;
	// int minValue = max;
	// while (itr.hasNext()) {
	// TDCoordinate tdCoordinate = itr.next();
	// int value = coordinateScores.get(tdCoordinate);
	// if (minValue >= value) {
	// minValue = value;
	// minCoordinate = tdCoordinate;
	// }
	// }
	// return minCoordinate;
	// }

	private void resolveMaxCoord(
			Map<Integer, List<TDCoordinate>> coordinateScores,
			List<TDCoordinate> goodOptions, List<TDCoordinate> badOptions) {
		Iterator<Integer> keys = coordinateScores.keySet().iterator();
		List<Integer> maxValues = Lists.newArrayList();
		while (keys.hasNext()) {
			maxValues.add(keys.next());
		}
		Collections.sort(maxValues);
		for (int i = maxValues.size(); i > 0 && goodOptions.isEmpty(); i--) {

			Integer max = maxValues.get(i - 1);
			List<TDCoordinate> coords = coordinateScores.get(max);
			// find if there is any dragon
			for (int j = 0; j < coords.size() && goodOptions.isEmpty(); j++) {
				TDCoordinate coord = coords.get(j);
				int[] mountainsRowPos = getMountainIndexInARow(coord);
				int firstMountainInRow = mountainsRowPos[0];
				int secondMountainInRow = mountainsRowPos[1];

				if (firstMountainInRow != -1) {
					// there is atleast one mountain in its row
					if (firstMountainInRow > coord.getColumn()) {
						// and this mountain is on the right side
						boolean dragonInRow = tdMatrix.getTileFinder()
								.hasDragon(0, firstMountainInRow,
										coord.getRow(), true);
						if (!dragonInRow) {
							// no dragon on its way till the mountain
							getMountainIndexInAColumn(coord);
							int[] mountainsColumnPos = getMountainIndexInAColumn(coord);
							int firstMountainInColumn = mountainsColumnPos[0];
							int secondMountainInColumn = mountainsColumnPos[1];

							if (firstMountainInColumn == -1) {
								// no mountain in this full column
								// check for dragon in this full column
								boolean dragonInColumn = tdMatrix
										.getTileFinder().hasDragon(0,
												Board.MAX_ROWS,
												coord.getColumn(), false);
								if (!dragonInColumn) {
									// and found no dragon
									goodOptions.add(coord);
								} else {
									// not a good option,
									badOptions.add(coord);
								}
							} else {
								checkMountainInColumn(goodOptions, badOptions,
										coord, firstMountainInColumn,
										secondMountainInColumn);
							}// else
						} else {
							// dragon in in its way
							// bad option
							badOptions.add(coord);
						}
					} else {
						// mountain is on the left side
						// check for second mountain in its row
						if (secondMountainInRow > coord.getColumn()) {
							boolean dragon = tdMatrix.getTileFinder()
									.hasDragon(firstMountainInRow,
											secondMountainInRow,
											coord.getRow(), true);
							if (!dragon) {
								// check for mountains in column
								int[] mountainsColumnPos = getMountainIndexInAColumn(coord);
								checkMountainInColumn(goodOptions, badOptions,
										coord, mountainsColumnPos[0],
										mountainsColumnPos[1]);
							} else {
								// if a dragon is between the mountains
								// bad option
								badOptions.add(coord);
							}// else
						} else {
							// if second mountain is also to the left
							boolean dragon = tdMatrix.getTileFinder()
									.hasDragon(secondMountainInRow,
											Board.MAX_COLUMNS, coord.getRow(),
											true);
							if (!dragon) {
								// check for mountains in column
								int[] mountainsColumnPos = getMountainIndexInAColumn(coord);
								checkMountainInColumn(goodOptions, badOptions,
										coord, mountainsColumnPos[0],
										mountainsColumnPos[1]);
							} else {
								// bad option
								badOptions.add(coord);
							}// else
						}// else
					}// else
				} else {
					boolean dragon = tdMatrix.getTileFinder().hasDragon(0,
							Board.MAX_COLUMNS, coord.getRow(), true);
					if (!dragon) {
						int[] mountainsColumnPos = getMountainIndexInAColumn(coord);

						checkMountainInColumn(goodOptions, badOptions, coord,
								mountainsColumnPos[0], mountainsColumnPos[1]);
					} else {
						// bad option
						badOptions.add(coord);
					}// else
				}// else
			}// for
		}
	}

	private void checkMountainInColumn(List<TDCoordinate> goodOptions,
			List<TDCoordinate> badOptions, TDCoordinate coord,
			int firstMountainInColumn, int secondMountainInColumn) {
		// there is atleast one mountain in column
		if (firstMountainInColumn > coord.getRow()) {
			// mountain is on to the bottom
			// check for dragon in this part of column till
			// first mountain presence
			boolean dragonInColumn = tdMatrix.getTileFinder().hasDragon(0,
					firstMountainInColumn, coord.getColumn(), false);
			if (!dragonInColumn) {
				// good option
				goodOptions.add(coord);
			} else {
				badOptions.add(coord);
			}
		} else {
			// mountain is to the left
			if (secondMountainInColumn == -1) {
				// no second mountain
				// check for dragon in this part of till the
				// end
				boolean dragonInColumn = tdMatrix.getTileFinder().hasDragon(
						firstMountainInColumn, Board.MAX_ROWS,
						coord.getColumn(), false);
				if (!dragonInColumn) {
					// good option
					goodOptions.add(coord);
				} else {
					// bad option
					badOptions.add(coord);
				}
			} else {
				// if there is a second mountain in this
				// column
				if (secondMountainInColumn > coord.getRow()) {
					// second mountain is to the bottom
					boolean dragonInColumn = tdMatrix.getTileFinder()
							.hasDragon(firstMountainInColumn,
									secondMountainInColumn, coord.getColumn(),
									false);
					if (!dragonInColumn) {
						// good option
						goodOptions.add(coord);
					} else {
						// bad option
						badOptions.add(coord);
					}
				} else {
					// second mountain is to the top
					boolean dragonInColumn = tdMatrix.getTileFinder()
							.hasDragon(secondMountainInColumn, Board.MAX_ROWS,
									coord.getColumn(), false);
					if (!dragonInColumn) {
						// good option
						goodOptions.add(coord);
					} else {
						// bad option
						badOptions.add(coord);
					}
				}// else
			}// else
		}// else
	}

	private int[] getMountainIndexInARow(TDCoordinate coord) {
		int firstMountainInRow = tdMatrix.getTileFinder().mountainAt(0,
				Board.MAX_COLUMNS, coord.getRow(), true);
		int secondMountainInRow = -1;
		if (firstMountainInRow != -1) {
			secondMountainInRow = tdMatrix.getTileFinder().mountainAt(
					firstMountainInRow + 1, Board.MAX_COLUMNS, coord.getRow(),
					true);
		}
		return new int[] { firstMountainInRow, secondMountainInRow };
	}

	private int[] getMountainIndexInAColumn(TDCoordinate coord) {
		int firstMountainInColumn = tdMatrix.getTileFinder().mountainAt(0,
				Board.MAX_ROWS, coord.getColumn(), false);
		int secondMountainInColumn = -1;
		if (firstMountainInColumn != -1) {
			secondMountainInColumn = tdMatrix.getTileFinder().mountainAt(
					firstMountainInColumn + 1, Board.MAX_ROWS,
					coord.getColumn(), false);
		}
		return new int[] { firstMountainInColumn, secondMountainInColumn };
	}

	public void setEntries(Iterator<Entry<TDCoordinate>> entries) {
		this.tdMatrix = new VirtualTDMatrix(entries, Board.MAX_ROWS,
				Board.MAX_COLUMNS);
	}

}
