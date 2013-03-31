package org.concordia.kingdoms.board;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.concordia.kingdoms.board.score.DragonDecorator;
import org.concordia.kingdoms.board.score.DragonGoldMineDecorator;
import org.concordia.kingdoms.board.score.GoldMineDecorator;
import org.concordia.kingdoms.board.score.SimpleTileDecorator;
import org.concordia.kingdoms.domain.Castle;
import org.concordia.kingdoms.domain.Color;
import org.concordia.kingdoms.domain.Component;
import org.concordia.kingdoms.domain.TileType;
import org.concordia.kingdoms.exceptions.GameRuleException;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * This class is used for two dimensional matrix.
 * 
 * @author Team K
 * @since 1.1
 */
public class TDMatrix implements IMatrix<TDCoordinate> {

	private List<List<Entry<TDCoordinate>>> entries;

	private int MAX_ROWS;

	private int MAX_COLUMNS;

	private int componentsOnBoard;

	private List<List<Integer>> rowsScores;

	private List<List<Integer>> columnsScores;

	private TileFinder tileFinder;

	private IDecorator dragonDecorator;

	private IDecorator goldmineDecorator;

	private IDecorator simpleTileDecorator;

	private IDecorator dragonGoldMineDecorator;

	private List<TDCoordinate> availableCoordinates;

	public TDMatrix(int rows, int columns) {
		this.MAX_ROWS = rows;
		this.MAX_COLUMNS = columns;
		this.componentsOnBoard = 0;
		Set<TileType> tileTypes = Sets.newHashSet();
		tileTypes.add(TileType.MOUNTAIN);
		tileTypes.add(TileType.DRAGON);
		tileTypes.add(TileType.GOLDMINE);
		tileTypes.add(TileType.WIZARD);
		this.tileFinder = new TileFinder(tileTypes);
		this.dragonDecorator = new DragonDecorator(null);
		this.goldmineDecorator = new GoldMineDecorator(null);
		this.simpleTileDecorator = new SimpleTileDecorator(null);
		this.dragonGoldMineDecorator = new DragonGoldMineDecorator();
		this.rowsScores = Lists.newArrayList();
		this.columnsScores = Lists.newArrayList();
		this.initEntries();
		this.initAvailableCoordinates();
	}

	/**
	 * initialize every entry with a null component and a coordinate in 2D
	 * matrix
	 */

	private void initEntries() {
		this.entries = Lists.newArrayList();
		for (int i = 0; i < MAX_ROWS; i++) {
			final List<Entry<TDCoordinate>> columns = Lists.newArrayList();
			for (int j = 0; j < MAX_COLUMNS; j++) {
				final Entry<TDCoordinate> newEntry = new Entry<TDCoordinate>(
						TDCoordinate.newInstance(i, j), null);
				columns.add(newEntry);
			}
			this.entries.add(columns);
		}
	}

	/**
	 * put the component on the board, for given coordinate; before the
	 * component is placed on the board, checks to see if the coordinate is
	 * invalid position, and a space is available.
	 */
	public void putComponent(Component component, TDCoordinate coordinate)
			throws GameRuleException {

		final int row = coordinate.getRow();

		final int column = coordinate.getColumn();

		if (!isValidPosition(coordinate)) {
			throw new GameRuleException("Invalid positon(" + row + "," + column
					+ ")");
		}
		if (!isEmpty(coordinate)) {
			throw new GameRuleException("No Space available");
		}
		this.entries.get(row).get(column).setComponent(component);
		this.tileFinder.rememberCoordinate(component, coordinate);
		this.componentsOnBoard++;
		this.availableCoordinates.remove(coordinate);
	}

	/**
	 * checks if the given coordinate is within mXn matrix coordinates
	 */
	public boolean isValidPosition(final TDCoordinate coordinate) {

		final int row = coordinate.getRow();

		final int column = coordinate.getColumn();

		final boolean isValidCoordinate = row >= 0 && row < MAX_ROWS
				&& column >= 0 && column < MAX_COLUMNS;
		boolean isValid = false;
		if (isValidCoordinate) {
			// check if any space is available
			isValid = this.entries.get(row).get(column).isEmpty();
		}
		return isValid;
	}

	/**
	 * method used to check if there is any empty space on the board
	 * 
	 * @return true/false
	 */
	public boolean isEmpty() {
		return this.componentsOnBoard < MAX_ROWS * MAX_COLUMNS;
	}

	/**
	 * check if the entry is empty or not for the given coordinate
	 */
	public boolean isEmpty(final TDCoordinate coordinate) {

		final int row = coordinate.getRow();

		final int column = coordinate.getColumn();

		return this.entries.get(row).get(column).isEmpty();
	}

	/**
	 * entries returned as an Iterator where first n entries represent a row for
	 * m times, giving mXn board
	 */

	public Iterator<Entry<TDCoordinate>> getEntries() {

		final List<Entry<TDCoordinate>> entriesList = Lists.newArrayList();

		for (final List<Entry<TDCoordinate>> row : this.entries) {
			for (final Entry<TDCoordinate> column : row) {
				entriesList.add(column);
			}
		}
		return entriesList.iterator();
	}

	/**
	 * score calculated on this board
	 */
	public Map<Color, Score> score() {

		List<List<Map<Color, Integer>>> rowsRanks = Lists.newArrayList();

		rowsScores = Lists.newArrayList();

		columnsScores = Lists.newArrayList();

		for (int row = 0; row < MAX_ROWS; row++) {
			final List<Integer> scores = Lists.newArrayList();
			getTilesScore(0, MAX_COLUMNS, row, true, scores);
			this.rowsScores.add(scores);
			List<Map<Color, Integer>> rowRanks = Lists.newArrayList();
			rowsRanks.add(rowRanks);
			this.getCastleRankScore(0, MAX_COLUMNS, row, true, rowRanks);
		}

		List<List<Map<Color, Integer>>> columnsRanks = Lists.newArrayList();

		for (int column = 0; column < MAX_COLUMNS; column++) {

			final List<Integer> scores = Lists.newArrayList();
			getTilesScore(0, MAX_ROWS, column, false, scores);
			this.columnsScores.add(scores);
			List<Map<Color, Integer>> columnRanks = Lists.newArrayList();
			columnsRanks.add(columnRanks);
			this.getCastleRankScore(0, MAX_ROWS, column, false, columnRanks);

		}

		Map<Color, Score> finalScore = Maps.newHashMap();

		extractFinalScore(rowsRanks, finalScore, true, rowsScores);
		extractFinalScore(columnsRanks, finalScore, false, columnsScores);

		return finalScore;
	}

	/**
	 * print cumulative row,column and total score
	 * 
	 * @param finalScore
	 */
	public void printFinalScore(Map<Color, Score> finalScore) {
		if (finalScore == null) {
			System.out.println("No Entry Found");
			return;
		}
		Iterator<Color> itr = finalScore.keySet().iterator();
		while (itr.hasNext()) {
			Color color = itr.next();
			Score score = finalScore.get(color);
			System.out.print(color + " ");
			System.out.print(score.getRowScore() + " ");
			System.out.print(score.getColumnScore() + " ");
			System.out.println(score.score());
		}
	}

	/**
	 * calculates the cumulative score for all the rows or columns score
	 * multiplied by each castle rank
	 * 
	 * @param rowsOrColumnsRanks
	 * @param finalScore
	 * @param isRow
	 * @param rowsOrColumnsScores
	 */
	private void extractFinalScore(
			List<List<Map<Color, Integer>>> rowsOrColumnsRanks,
			Map<Color, Score> finalScore, boolean isRow,
			List<List<Integer>> rowsOrColumnsScores) {
		for (int i = 0; i < rowsOrColumnsRanks.size(); i++) {
			List<Map<Color, Integer>> rowOrColumnRanks = rowsOrColumnsRanks
					.get(i);
			for (int j = 0; j < rowOrColumnRanks.size(); j++) {
				Map<Color, Integer> colorRanks = rowOrColumnRanks.get(j);
				if (colorRanks.keySet().isEmpty()) {
					continue;
				}
				Iterator<Color> colorItr = colorRanks.keySet().iterator();
				while (colorItr.hasNext()) {
					Color color = colorItr.next();
					Integer finalRank = colorRanks.get(color);
					int finalRowScore = finalRank
							* rowsOrColumnsScores.get(i).get(j);
					if (isRow) {
						if (finalScore.get(color) != null) {
							finalScore.get(color).incrementRowScoreBy(
									finalRowScore);
						} else {
							finalScore.put(color, new Score(color)
									.incrementRowScoreBy(finalRowScore));
						}
					} else {
						if (finalScore.get(color) != null) {
							finalScore.get(color).incrementColumnScoreBy(
									finalRowScore);
						} else {
							finalScore.put(color, new Score(color)
									.incrementColumnScoreBy(finalRowScore));
						}
					}
				}
			}
		}
	}

	/**
	 * calculates the tile's score whose value is influenced by dragon, goldmine
	 * tile. mountain tile divides the row or column into the left side and
	 * right side and a recursive call is made on both sides
	 * 
	 * @param start
	 *            - range start
	 * @param end
	 *            - range end
	 * @param rowOrColumnNumber
	 *            - row or column starting index
	 * @param isRow
	 *            - true if the score is for row, false otherwise
	 * @param scores
	 *            - score for each part, divided by mountain if any
	 */
	public void getTilesScore(int start, int end, int rowOrColumnNumber,
			boolean isRow, List<Integer> scores) {

		if (start == end) {
			return;
		}
		int mountainAt = tileFinder.mountainAt(start, end, rowOrColumnNumber,
				isRow);
		if (mountainAt == -1) {

			// when dragon is in the range
			if (tileFinder.hasDragon(start, end, rowOrColumnNumber, isRow)) {
				// when goldmine is in the range
				if (tileFinder
						.hasGoldMine(start, end, rowOrColumnNumber, isRow)) {
					// dragon nullifies the resource tile value and goldmine
					// doubles the tile value; apply both the rules using the
					// decorator
					if (isRow) {
						scores.add(tileRowScore(start, end, rowOrColumnNumber,
								dragonGoldMineDecorator));
					} else {
						scores.add(tileColumnScore(start, end,
								rowOrColumnNumber, dragonGoldMineDecorator));
					}

				} else {
					if (isRow) {
						scores.add(tileRowScore(start, end, rowOrColumnNumber,
								dragonDecorator));
					} else {
						scores.add(tileColumnScore(start, end,
								rowOrColumnNumber, dragonDecorator));
					}
				}

			} else {
				if (tileFinder
						.hasGoldMine(start, end, rowOrColumnNumber, isRow)) {
					// goldmine decorator
					if (isRow) {
						scores.add(tileRowScore(start, end, rowOrColumnNumber,
								goldmineDecorator));
					} else {
						scores.add(tileColumnScore(start, end,
								rowOrColumnNumber, goldmineDecorator));
					}

				} else {
					// simple tile decorator,which gives just the component
					// value, when no dragon or goldmine is
					// found
					if (isRow) {
						scores.add(tileRowScore(start, end, rowOrColumnNumber,
								simpleTileDecorator));
					} else {
						scores.add(tileColumnScore(start, end,
								rowOrColumnNumber, simpleTileDecorator));
					}
				}
			}
		} else {
			// left side of mountain
			getTilesScore(start, mountainAt, rowOrColumnNumber, isRow, scores);
			// right side of mountain
			getTilesScore(mountainAt + 1, end, rowOrColumnNumber, isRow, scores);
		}
	}

	/**
	 * total score for the given column within start to end range
	 * 
	 * @param start
	 *            - range starting point
	 * @param end
	 *            - range ending point
	 * @param column
	 *            - column number
	 * @param decorator
	 *            - {@link IDecorator}
	 * @return
	 */
	private int tileColumnScore(int start, int end, int column,
			IDecorator decorator) {
		int columnScore = 0;
		for (int row = start; row < end; row++) {
			columnScore += getEntryValue(row, column, decorator);
		}
		return columnScore;
	}

	/**
	 * total score for the given row within start to end range
	 * 
	 * @param start
	 *            - range starating point
	 * @param end
	 *            - range ending point
	 * @param row
	 *            - row number
	 * @param decorator
	 *            - {@link IDecorator}
	 * @return
	 */
	private int tileRowScore(int start, int end, int row, IDecorator decorator) {
		int columnScore = 0;
		for (int column = start; column < end; column++) {
			columnScore += getEntryValue(row, column, decorator);
		}
		return columnScore;
	}

	/**
	 * calculates effective component's value
	 * 
	 * @param row
	 *            - row number
	 * @param column
	 *            - column number
	 * @param decorator
	 *            - gives the final component value
	 * @return - effective entry's component value
	 */
	private int getEntryValue(int row, int column, IDecorator decorator) {
		int totalValue = 0;
		final Entry<TDCoordinate> entry = getEntry(row, column);
		final Integer value = decorator.setComponent(entry.getComponent())
				.getValue();
		if (value != null) {
			totalValue += value.intValue();
		}
		return totalValue;
	}

	/**
	 * calculate each castel's rank for a given row or column. A row or column
	 * may be divided into two or more, if the mountain tile is in the range.
	 * This is a recursive call if the mountain is in between, and divided into
	 * the left side of the mountain and to the right side of the mountain.
	 * 
	 * @param start
	 *            - range starting index
	 * @param end
	 *            - range ending index
	 * @param rowOrColumnNumber
	 *            - row or column starting index
	 * @param isRow
	 *            - true if the method is being called for calculating row,
	 *            false for column
	 * @param rowRanks
	 *            - resolved castle rank map
	 */
	public void getCastleRankScore(int start, int end, int rowOrColumnNumber,
			boolean isRow, List<Map<Color, Integer>> rowRanks) {

		// when mountain is to the extreme left, extreme right, or side by side
		// mountains
		if (start == end) {
			return;
		}
		// check mountain index
		int mountainAt = tileFinder.mountainAt(start, end, rowOrColumnNumber,
				isRow);

		// when no mountain is available , calculate the score for the elements
		// within this range
		if (mountainAt == -1) {

			Map<Color, Integer> map = Maps.newHashMap();
			rowRanks.add(map);
			if (isRow) {
				getCastleRowRankScore(start, end, rowOrColumnNumber, map);
			} else {
				getCastleColumnRankScore(start, end, rowOrColumnNumber, map);
			}
		} else {
			// to the left side of the mountain
			getCastleRankScore(start, mountainAt, rowOrColumnNumber, isRow,
					rowRanks);
			// to the right side of the mountain
			getCastleRankScore(mountainAt + 1, end, rowOrColumnNumber, isRow,
					rowRanks);
		}
	}

	/**
	 * iterates over all the castles within the given row range from start to
	 * end. calculate each castle rank , double its rank if any wizard
	 * orthogonal to this castle.
	 * 
	 * @param start
	 *            - range starting
	 * @param end
	 *            - range ending
	 * @param column
	 *            - castles lying in this row
	 * @param rankMap
	 *            - resolved map, castles color and calculated rank
	 */
	private void getCastleRowRankScore(int start, int end, int row,
			Map<Color, Integer> rankMap) {
		for (int column = start; column < end; column++) {
			final Entry<TDCoordinate> entry = getEntry(row, column);
			resolveCastleRanks(rankMap, entry);
		}
	}

	/**
	 * iterates over all the castles within the given column range from start to
	 * end. calculate each castle rank , double its rank if any wizard
	 * orthogonal to this castle.
	 * 
	 * @param start
	 *            - range starting
	 * @param end
	 *            - range ending
	 * @param column
	 *            - castles lying in this column
	 * @param rankMap
	 *            - resolved map, castles color and calculated rank
	 */
	private void getCastleColumnRankScore(int start, int end, int column,
			Map<Color, Integer> rankMap) {
		// runs through the elements within the range
		for (int row = start; row < end; row++) {
			// column entry
			final Entry<TDCoordinate> entry = getEntry(row, column);
			resolveCastleRanks(rankMap, entry);
		}
	}

	/**
	 * resolves the castle's rank with respect to the wizard tile position
	 * 
	 * @param rankMap
	 *            - resolved map, castles color and calculated rank
	 * @param entry
	 *            - @see {@link Entry}
	 */
	private void resolveCastleRanks(Map<Color, Integer> rankMap,
			final Entry<TDCoordinate> entry) {
		final Component component = entry.getComponent();
		// check for castle
		if (component instanceof Castle) {
			final Castle castle = (Castle) component;
			final Color color = castle.getColor();
			// check if any wizard is orthogonal
			final boolean hasWizardOrthogonal = this.tileFinder
					.hasWizardOrthogonal(entry.getCoordinate());
			int rank = castle.getValue();
			// effective castle rank=double the castle rank
			if (hasWizardOrthogonal) {
				rank = 2 * rank;
			}
			if (rankMap.get(color) != null) {
				rank = rank + rankMap.get(color);
				rankMap.put(color, rank);
			} else {
				rankMap.put(color, rank);
			}
		}
	}

	/**
	 * @param row
	 *            - row number
	 * @param column
	 *            - column number
	 * @return Entry
	 */
	private Entry<TDCoordinate> getEntry(int row, int column) {
		return this.entries.get(row).get(column);
	}

	/**
	 * gives the total number of components already on the board.
	 */
	public int getTotalComponents() {
		return this.componentsOnBoard;
	}

	public List<TDCoordinate> getAvailableCoordinates() {
		return this.availableCoordinates;
	}

	private void initAvailableCoordinates() {
		this.availableCoordinates = Lists.newArrayList();
		for (int i = 0; i < MAX_ROWS; i++) {
			for (int j = 0; j < MAX_COLUMNS; j++) {
				this.availableCoordinates.add(TDCoordinate.newInstance(i, j));
			}
		}
	}

	public List<Component> clearAllEntries() {
		List<Component> components = Lists.newArrayList();
		Iterator<Entry<TDCoordinate>> entries = getEntries();
		while (entries.hasNext()) {
			Entry<TDCoordinate> entry = entries.next();
			components.add(entry.setNull());
		}
		initAvailableCoordinates();
		return components;
	}

	protected void setEntries(List<List<Entry<TDCoordinate>>> entries) {
		this.entries = entries;
	}

	public TileFinder getTileFinder() {
		return tileFinder;
	}

}
