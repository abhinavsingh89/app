package org.concordia.kingdoms.board;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.concordia.kingdoms.Player;
import org.concordia.kingdoms.domain.Castle;
import org.concordia.kingdoms.domain.Color;
import org.concordia.kingdoms.domain.Component;
import org.concordia.kingdoms.domain.TileType;
import org.concordia.kingdoms.exceptions.GameRuleException;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class TDMatrix implements IMatrix<TDCoordinate>,
		ScoreService<TDCoordinate> {

	private List<List<Entry<TDCoordinate>>> entries;

	private int MAX_ROWS;

	private int MAX_COLUMNS;

	private int componentsOnBoard;

	private List<List<Integer>> rowScores;

	private List<List<Integer>> columnScores;

	private Map<Integer, Map<Color, Score>> scores;

	private TileFinder tileFinder;

	private IDecorator dragonDecorator;

	private IDecorator goldmineDecorator;

	private IDecorator doNothingDecorator;

	private IDecorator dragonGoldMineDecorator;

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
		this.doNothingDecorator = new SimpleTileDecorator(null);
		this.dragonGoldMineDecorator = new DragonGoldMineDecorator();
		this.rowScores = Lists.newArrayList();
		this.columnScores = Lists.newArrayList();
		this.initEntries();
	}

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
	}

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
		return this.componentsOnBoard == MAX_ROWS * MAX_COLUMNS;
	}

	public boolean isEmpty(final TDCoordinate coordinate) {

		final int row = coordinate.getRow();

		final int column = coordinate.getColumn();

		return this.entries.get(row).get(column).isEmpty();
	}

	public Iterator<Entry<TDCoordinate>> getEntries() {

		final List<Entry<TDCoordinate>> entriesList = Lists.newArrayList();

		for (final List<Entry<TDCoordinate>> row : this.entries) {
			for (final Entry<TDCoordinate> column : row) {
				entriesList.add(column);
			}
		}
		return Collections.unmodifiableList(entriesList).iterator();
	}

	public int score() {

		List<Map<Color, Integer>> rowRanks = Lists.newArrayList();

		for (int row = 0; row < MAX_ROWS; row++) {
			final List<Integer> scores = Lists.newArrayList();
			getScore(0, MAX_COLUMNS, row, true, scores);
			this.rowScores.add(scores);
			this.calculateScore(0, MAX_COLUMNS, row, true, rowRanks);
		}

		List<Map<Color, Integer>> columnRanks = Lists.newArrayList();

		for (int column = 0; column < MAX_COLUMNS; column++) {
			final List<Integer> scores = Lists.newArrayList();
			getScore(0, MAX_ROWS, column, false, scores);
			calculateScore(0, MAX_ROWS, column, false, columnRanks);
			this.columnScores.add(scores);
		}
		System.out.println("---------------------------------------------");
		for (List<Integer> scores : this.rowScores) {
			System.out.println(scores);
		}
		System.out.println("---------------------------------------------");
		for (List<Integer> scores : columnScores) {
			System.out.println(scores);
		}

		System.out.println("---------------------------------------------");
		int i = 0;
		for (Map<Color, Integer> colorMap : rowRanks) {
			System.out.print(i++ + ")");
			Iterator<Color> itr = colorMap.keySet().iterator();
			while (itr.hasNext()) {
				Color color = itr.next();
				System.out.println(color + " " + colorMap.get(color));
			}
		}

		System.out.println("---------------------------------------------");
		i = 0;
		for (Map<Color, Integer> colorMap : columnRanks) {
			System.out.print(i++ + ")");
			Iterator<Color> itr = colorMap.keySet().iterator();
			while (itr.hasNext()) {
				Color color = itr.next();
				System.out.println(color + " " + colorMap.get(color));
			}
		}

		return 0;
	}

	public void getScore(int start, int end, int rowOrColumnNumber,
			boolean isRow, List<Integer> scores) {
		if (start == end) {
			return;
		}
		int mountainAt = mountainAt(start, end, rowOrColumnNumber, isRow);
		if (mountainAt == -1) {

			if (hasDragon(start, end, rowOrColumnNumber, isRow)) {
				// dragon decorator
				if (hasGoldMine(start, end, rowOrColumnNumber, isRow)) {
					// goldmine decorator
					if (isRow) {
						scores.add(rowScore(start, end, rowOrColumnNumber,
								dragonGoldMineDecorator));
					} else {
						scores.add(columnScore(start, end, rowOrColumnNumber,
								dragonGoldMineDecorator));
					}

				} else {
					if (isRow) {
						scores.add(rowScore(start, end, rowOrColumnNumber,
								dragonDecorator));
					} else {
						scores.add(columnScore(start, end, rowOrColumnNumber,
								dragonDecorator));
					}
				}

			} else {
				if (hasGoldMine(start, end, rowOrColumnNumber, isRow)) {
					// goldmine decorator
					if (isRow) {
						scores.add(rowScore(start, end, rowOrColumnNumber,
								goldmineDecorator));
					} else {
						scores.add(columnScore(start, end, rowOrColumnNumber,
								goldmineDecorator));
					}

				} else {
					// do nothing decorator
					if (isRow) {
						scores.add(rowScore(start, end, rowOrColumnNumber,
								doNothingDecorator));
					} else {
						scores.add(columnScore(start, end, rowOrColumnNumber,
								doNothingDecorator));
					}
				}
			}
		} else {
			getScore(start, mountainAt, rowOrColumnNumber, isRow, scores);
			getScore(mountainAt + 1, end, rowOrColumnNumber, isRow, scores);
		}

	}

	private int mountainAt(int start, int end, int rowOrColumnNumber,
			boolean isRow) {
		int idx = tileFinder.tileAt(rowOrColumnNumber, isRow, start, end,
				TileType.MOUNTAIN);
		return idx;
	}

	private boolean hasDragon(int start, int end, int rowOrColumnNumber,
			boolean isRow) {
		int idx = tileFinder.tileAt(rowOrColumnNumber, isRow, start, end,
				TileType.DRAGON);
		return idx != -1;
	}

	private boolean hasGoldMine(int start, int end, int rowOrColumnNumber,
			boolean isRow) {
		int idx = tileFinder.tileAt(rowOrColumnNumber, isRow, start, end,
				TileType.GOLDMINE);
		return idx != -1;
	}

	private int columnScore(int start, int end, int column, IDecorator decorator) {
		int columnScore = 0;
		for (int row = start; row < end; row++) {
			columnScore += getEntryValue(row, column, decorator);
		}
		return columnScore;
	}

	private int rowScore(int start, int end, int row, IDecorator decorator) {
		int columnScore = 0;
		for (int column = start; column < end; column++) {
			columnScore += getEntryValue(row, column, decorator);
		}
		return columnScore;
	}

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

	private Entry<TDCoordinate> getEntry(int row, int column) {
		return this.entries.get(row).get(column);
	}

	private void calculateScore(int start, int end, int rowOrColumnNumber,
			boolean isRow, List<Map<Color, Integer>> scores) {
		if (start == end) {
			return;
		}
		int mountainAt = mountainAt(start, end, rowOrColumnNumber, isRow);

		if (mountainAt == -1) {
			Map<Color, Integer> rankMap = Maps.newHashMap();
			scores.add(rankMap);
			if (isRow) {
				calcRowScore(start, end, rowOrColumnNumber, rankMap);
			} else {
				calcColumnScore(start, end, rowOrColumnNumber, rankMap);
			}
		} else {
			calculateScore(start, mountainAt, rowOrColumnNumber, isRow, scores);
			calculateScore(mountainAt + 1, end, rowOrColumnNumber, isRow,
					scores);
		}
	}

	private void calcRowScore(int start, int end, int row,
			Map<Color, Integer> rankMap) {
		for (int column = start; column < end; column++) {
			final Entry<TDCoordinate> entry = getEntry(row, column);
			final Component component = entry.getComponent();
			if (component instanceof Castle) {
				final Castle castle = (Castle) component;
				final Color color = castle.getColor();
				final boolean hasWizardOrthogonal = this.tileFinder
						.hasWizardOrthogonal(entry.getCoordinate());
				int rank = castle.getValue();
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
	}

	private void calcColumnScore(int start, int end, int column,
			Map<Color, Integer> rankMap) {
		for (int row = start; row < end; row++) {
			final Entry<TDCoordinate> entry = getEntry(row, column);
			final Component component = entry.getComponent();
			if (component instanceof Castle) {
				final Castle castle = (Castle) component;
				final Color color = castle.getColor();
				final boolean hasWizardOrthogonal = this.tileFinder
						.hasWizardOrthogonal(entry.getCoordinate());
				int rank = castle.getValue();
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
	}

	public int getTotalComponents() {
		return this.componentsOnBoard;
	}
}
