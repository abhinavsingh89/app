package org.concordia.kingdoms.adapter;

import java.util.List;
import java.util.Map;

import org.concordia.kingdoms.board.Board;
import org.concordia.kingdoms.board.Component;
import org.concordia.kingdoms.board.Entry;
import org.concordia.kingdoms.tokens.Castle;
import org.concordia.kingdoms.tokens.Tile;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class EntriesAdapter implements
		IAdapter<Entry[][], List<org.concordia.kingdoms.jaxb.Entry>> {

	public List<org.concordia.kingdoms.jaxb.Entry> convertTo(
			Entry[][] domainEntries) {

		final List<org.concordia.kingdoms.jaxb.Entry> jaxbEntries = Lists
				.newArrayList();

		if (domainEntries == null) {
			return jaxbEntries;
		}
		// find the rows length
		int rows = domainEntries.length;
		// find the rows length
		int columns = domainEntries[0].length;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				Entry entry = domainEntries[i][j];
				int row = entry.getRow();
				int column = entry.getColumn();
				final Component component = entry.getComponent();
				// the component can be either Tile or Castle
				if (component instanceof Tile) {
					final Tile tile = (Tile) component;
					org.concordia.kingdoms.jaxb.Tile jaxbTile = AdapterUtil
							.newJaxbTile(tile);
					jaxbEntries.add(AdapterUtil.newJaxbEntry(row, column,
							jaxbTile, null));
				}
				if (component instanceof Castle) {
					final Castle castle = (Castle) component;
					org.concordia.kingdoms.jaxb.Castle jaxbCastle = AdapterUtil
							.newJaxbCastle(castle);
					jaxbEntries.add(AdapterUtil.newJaxbEntry(row, column, null,
							jaxbCastle));
				}
			}
		}
		return jaxbEntries;
	}

	public Entry[][] convertFrom(
			List<org.concordia.kingdoms.jaxb.Entry> jaxbEntries) {
		final Entry[][] entries = new Entry[Board.MAX_ROWS][Board.MAX_COLUMNS];
		final Map<Integer, Map<Integer, org.concordia.kingdoms.jaxb.Entry>> entriesMap = Maps
				.newHashMap();
		for (org.concordia.kingdoms.jaxb.Entry jaxbEntry : jaxbEntries) {
			// when no entry is available for the row
			if (entriesMap.get(jaxbEntry.getRow()) == null) {
				Map<Integer, org.concordia.kingdoms.jaxb.Entry> entryMap = Maps
						.newHashMap();
				entryMap.put(jaxbEntry.getColumn(), jaxbEntry);
				entriesMap.put(jaxbEntry.getRow(), entryMap);
			} else {
				Map<Integer, org.concordia.kingdoms.jaxb.Entry> entryMap = Maps
						.newHashMap();
				entriesMap.get(jaxbEntry.getRow()).put(jaxbEntry.getColumn(),
						jaxbEntry);
			}
		}
		for (int i = 0; i < Board.MAX_ROWS; i++) {
			for (int j = 0; j < Board.MAX_COLUMNS; j++) {
				// if the row has no entries before
				if (entriesMap.get(i) != null) {
					org.concordia.kingdoms.jaxb.Entry entry = entriesMap.get(i)
							.get(j);
					// if the particular column has no entry
					if (entry != null) {
						int column = entry.getColumn();
						int row = entry.getRow();

						org.concordia.kingdoms.jaxb.Castle jaxbCastle = entry
								.getCastle();
						org.concordia.kingdoms.jaxb.Tile jaxbTile = entry
								.getTile();
						if (jaxbCastle == null) {
							// new entry with a tile
							entries[i][j] = Entry.newEntry(row, column,
									AdapterUtil.newTile(jaxbTile));
						} else {
							// new entry with a castle
							entries[i][j] = Entry.newEntry(row, column,
									AdapterUtil.newCastle(jaxbCastle));
						}
					} else {
						// new entry
						entries[i][j] = Entry.newEntry(i, j, null);
					}
				} else {
					// new entry
					entries[i][j] = Entry.newEntry(i, j, null);
				}
			}
		}
		return entries;
	}
}
