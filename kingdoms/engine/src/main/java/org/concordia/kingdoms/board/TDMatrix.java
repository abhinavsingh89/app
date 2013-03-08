package org.concordia.kingdoms.board;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.concordia.kingdoms.domain.Component;
import org.concordia.kingdoms.exceptions.GameRuleException;

import com.google.common.collect.Lists;

public class TDMatrix implements IMatrix<TDCoordinate> {

	private List<List<Entry<TDCoordinate>>> entries;

	private int MAX_ROWS;

	private int MAX_COLUMNS;

	private int componentsOnBoard;

	public TDMatrix(int rows, int columns) {
		this.MAX_ROWS = rows;
		this.MAX_COLUMNS = columns;
		this.componentsOnBoard = 0;
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

	public boolean isEmpty(final TDCoordinate coordinate) {

		final int row = coordinate.getRow();

		final int column = coordinate.getColumn();

		return this.entries.get(row).get(column).isEmpty();
	}

	public Iterator<Entry<TDCoordinate>> getEntries() {

		final List<Entry<TDCoordinate>> entriesList = Lists.newArrayList();
		for (final List<Entry<TDCoordinate>> row : entries) {
			for (final Entry<TDCoordinate> column : row) {
				entriesList.add(column);
			}
		}
		return Collections.unmodifiableList(entriesList).iterator();
	}

}
