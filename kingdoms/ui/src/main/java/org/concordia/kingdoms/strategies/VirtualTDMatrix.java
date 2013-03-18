package org.concordia.kingdoms.strategies;

import java.util.Iterator;
import java.util.List;

import org.concordia.kingdoms.board.Board;
import org.concordia.kingdoms.board.Entry;
import org.concordia.kingdoms.board.TDCoordinate;
import org.concordia.kingdoms.board.TDMatrix;

import com.google.common.collect.Lists;

public class VirtualTDMatrix extends TDMatrix {

	public VirtualTDMatrix(Iterator<Entry<TDCoordinate>> entries, int rows,
			int columns) {
		super(5, 6);
		setEntries(resolveEntries(entries));
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

}
