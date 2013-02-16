package org.concordia.kingdoms.adapter;

import org.concordia.kingdoms.tokens.Castle;
import org.concordia.kingdoms.tokens.Color;
import org.concordia.kingdoms.tokens.Tile;
import org.concordia.kingdoms.tokens.TileType;

/**
 * Simple utility to convert the basic token objects to and from Jaxb to domain
 * objects
 * 
 * @author Team K
 * 
 */
public class AdapterUtil {

	/**
	 * builds a new Tile object from Jaxb Tile object
	 * 
	 * @param - jaxbTile Tile object using by Jaxb
	 * @return A new Tile object
	 */

	public static Tile newTile(org.concordia.kingdoms.jaxb.Tile jaxbTile) {
		if (jaxbTile == null) {
			return null;
		}
		String tileName = jaxbTile.getName();
		TileType tileType = TileType.valueOf(jaxbTile.getType().name());
		Integer value = jaxbTile.getValue();
		final Tile tile = Tile.newTile(tileType, tileName, value);
		return tile;
	}

	/**
	 * builds a new Castle object from Jaxb Castle object
	 * 
	 * @param - jaxb Castle object using by Jaxb
	 * @return A new Castle object
	 */

	public static Castle newCastle(org.concordia.kingdoms.jaxb.Castle jaxbCastle) {
		if (jaxbCastle == null) {
			return null;
		}
		Integer rank = jaxbCastle.getRank();
		Color color = Color.valueOf(jaxbCastle.getColor().name());
		final Castle castle = Castle.newCastle(rank, color);
		return castle;
	}

	/**
	 * takes the row , column and a component to build the Jaxb Entry object
	 * 
	 * @param row
	 *            - row position in the matrix
	 * @param column
	 *            - column position in the matrix
	 * @param tile
	 *            - Entry holding tile
	 * @param castle
	 *            - Entry holding castle
	 * @return A new Jaxb Entry object
	 */
	public static org.concordia.kingdoms.jaxb.Entry newJaxbEntry(int row,
			int column, org.concordia.kingdoms.jaxb.Tile tile,
			org.concordia.kingdoms.jaxb.Castle castle) {
		org.concordia.kingdoms.jaxb.Entry jaxbEntry = new org.concordia.kingdoms.jaxb.Entry();
		jaxbEntry.setRow(row);
		jaxbEntry.setColumn(column);
		jaxbEntry.setTile(tile);
		jaxbEntry.setCastle(castle);
		return jaxbEntry;
	}

}
