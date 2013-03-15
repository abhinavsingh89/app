package org.concordia.kingdoms.adapter;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.concordia.kingdoms.board.Board;
import org.concordia.kingdoms.board.Entry;
import org.concordia.kingdoms.board.ICoordinate;
import org.concordia.kingdoms.board.TDCoordinate;
import org.concordia.kingdoms.domain.Castle;
import org.concordia.kingdoms.domain.Component;
import org.concordia.kingdoms.domain.Tile;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
/**
 * This class is used to convert domain class object to Jaxb objects..
 * @author Team K
 * @since 1.1
 */
public class EntriesAdapter
		implements
		IAdapter<Iterator<Entry<TDCoordinate>>, List<org.concordia.kingdoms.jaxb.Entry>> {

	public Iterator<Entry<TDCoordinate>> convertFrom(
			List<org.concordia.kingdoms.jaxb.Entry> jaxbEntries) {

		// collection whose iterator will be returned
		List<Entry<TDCoordinate>> retEntries = Lists.newArrayList();
		// arranging the entries in a map with row as key and again the value as
		// a map of column and Entry
		final Map<Integer, Map<Integer, org.concordia.kingdoms.jaxb.Entry>> rowMap = Maps
				.newHashMap();
		// over the given jaxb Entry objects
		for (org.concordia.kingdoms.jaxb.Entry jaxbEntry : jaxbEntries) {
			// when no entry is available for the row, create a new map for this
			// row entry
			if (rowMap.get(jaxbEntry.getRow()) == null) {
				final Map<Integer, org.concordia.kingdoms.jaxb.Entry> columnMap = Maps
						.newHashMap();
				// add to map with key as column and value as Entry this makes
				// an actual entry for a coordinate
				columnMap.put(jaxbEntry.getColumn(), jaxbEntry);
				// key with a row is added to the row map
				rowMap.put(jaxbEntry.getRow(), columnMap);
			} else {
				rowMap.get(jaxbEntry.getRow()).put(jaxbEntry.getColumn(),
						jaxbEntry);
			}
		}
		for (int row = 0; row < Board.MAX_ROWS; row++) {
			for (int column = 0; column < Board.MAX_COLUMNS; column++) {
				// if the row has no entries before
				if (rowMap.get(row) != null) {
					org.concordia.kingdoms.jaxb.Entry entry = rowMap.get(row)
							.get(column);
					// if the particular column has no entry
					if (entry != null) {

						org.concordia.kingdoms.jaxb.Castle jaxbCastle = entry
								.getCastle();
						org.concordia.kingdoms.jaxb.Tile jaxbTile = entry
								.getTile();
						if (jaxbCastle == null) {
							// new entry with a tile
							TDCoordinate entryCoordinate = TDCoordinate
									.newInstance(entry.getRow(),
											entry.getColumn());
							Tile entryTile = AdapterUtil.newTile(jaxbTile);
							Entry<TDCoordinate> tdEntry = new Entry<TDCoordinate>(
									entryCoordinate, (Component) entryTile);
							retEntries.add(tdEntry);
						} else {
							// new entry with a castle
							TDCoordinate entryCoordinate = TDCoordinate
									.newInstance(entry.getRow(),
											entry.getColumn());
							Castle entryCasle = AdapterUtil
									.newCastle(jaxbCastle);
							Entry<TDCoordinate> tdEntry = new Entry<TDCoordinate>(
									entryCoordinate, (Component) entryCasle);
							retEntries.add(tdEntry);
						}
					} else {
						// new entry

						retEntries.add(new Entry<TDCoordinate>(TDCoordinate
								.newInstance(row, column), null));
					}
				} else {
					// new entry
					retEntries.add(new Entry<TDCoordinate>(TDCoordinate
							.newInstance(row, column), null));
				}
			}
		}
		return retEntries.iterator();
	}

	public List<org.concordia.kingdoms.jaxb.Entry> convertTo(
			Iterator<Entry<TDCoordinate>> entries) {

		// collection to be returned
		final List<org.concordia.kingdoms.jaxb.Entry> jaxbEntries = Lists
				.newArrayList();
		// over the given entries
		while (entries.hasNext()) {
			// each entry
			final Entry<? extends ICoordinate> entry = entries.next();
			// entry holding component
			final Component component = entry.getComponent();
			// the component can be either Tile or Castle
			if (component instanceof Tile) {
				final Tile tile = (Tile) component;
				org.concordia.kingdoms.jaxb.Tile jaxbTile = AdapterUtil
						.newJaxbTile(tile);
				jaxbEntries.add(AdapterUtil.newJaxbEntry(entry.getCoordinate(),
						jaxbTile, null));
			}
			if (component instanceof Castle) {
				final Castle castle = (Castle) component;
				org.concordia.kingdoms.jaxb.Castle jaxbCastle = AdapterUtil
						.newJaxbCastle(castle);
				jaxbEntries.add(AdapterUtil.newJaxbEntry(entry.getCoordinate(),
						null, jaxbCastle));
			}
		}
		return jaxbEntries;

	}

}
