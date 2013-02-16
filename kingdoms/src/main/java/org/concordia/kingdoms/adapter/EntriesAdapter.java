package org.concordia.kingdoms.adapter;

import java.util.List;
import java.util.Map;

import org.concordia.kingdoms.board.Board;
import org.concordia.kingdoms.board.Component;
import org.concordia.kingdoms.board.Entry;
import org.concordia.kingdoms.tokens.Castle;
import org.concordia.kingdoms.tokens.Color;
import org.concordia.kingdoms.tokens.Tile;
import org.concordia.kingdoms.tokens.TileType;

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
		int rows = domainEntries.length;
		int columns = domainEntries[0].length;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				Entry entry = domainEntries[i][j];
				int row = entry.getRow();
				int column = entry.getColumn();
				final Component component = entry.getComponent();

				if (component instanceof Tile) {
					final Tile tile = (Tile) component;
					String tileName = tile.getName();
					TileType tileType = tile.getType();
					Integer value = tile.getValue();

					org.concordia.kingdoms.jaxb.Tile jaxbTile = new org.concordia.kingdoms.jaxb.Tile();
					jaxbTile.setName(tileName);
					jaxbTile.setValue(value);
					jaxbTile.setType(org.concordia.kingdoms.jaxb.TileType
							.valueOf(tileType.name()));
					jaxbEntries.add(newJaxbEntry(row, column, jaxbTile, null));
				}
				if (component instanceof Castle) {
					final Castle castle = (Castle) component;
					final String castleName = castle.getName();
					final Color color = castle.getColor();
					final Integer castleRank = castle.getValue();

					org.concordia.kingdoms.jaxb.Castle jaxbCastle = new org.concordia.kingdoms.jaxb.Castle();
					jaxbCastle.setName(castleName);
					jaxbCastle.setRank(castleRank);
					jaxbCastle.setColor(org.concordia.kingdoms.jaxb.Color
							.valueOf(color.toString()));
					jaxbEntries
							.add(newJaxbEntry(row, column, null, jaxbCastle));
				}
			}
		}
		return jaxbEntries;
	}

	public org.concordia.kingdoms.jaxb.Entry newJaxbEntry(int row, int column,
			org.concordia.kingdoms.jaxb.Tile tile,
			org.concordia.kingdoms.jaxb.Castle castle) {
		org.concordia.kingdoms.jaxb.Entry jaxbEntry = new org.concordia.kingdoms.jaxb.Entry();
		jaxbEntry.setRow(row);
		jaxbEntry.setColumn(column);
		jaxbEntry.setTile(tile);
		jaxbEntry.setCastle(castle);
		return jaxbEntry;
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
									newTile(jaxbTile));
						} else {
							// new entry with a castle
							entries[i][j] = Entry.newEntry(row, column,
									newCastle(jaxbCastle));
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

	private Tile newTile(org.concordia.kingdoms.jaxb.Tile jaxbTile) {
		if (jaxbTile == null) {
			return null;
		}
		String tileName = jaxbTile.getName();
		TileType tileType = TileType.valueOf(jaxbTile.getType().name());
		Integer value = jaxbTile.getValue();
		final Tile tile = Tile.newTile(tileType, tileName, value);
		return tile;
	}

	private Castle newCastle(org.concordia.kingdoms.jaxb.Castle jaxbCastle) {
		if (jaxbCastle == null) {
			return null;
		}
		Integer rank = jaxbCastle.getRank();
		Color color = Color.valueOf(jaxbCastle.getColor().name());
		final Castle castle = Castle.newCastle(rank, color);
		return castle;
	}
}
