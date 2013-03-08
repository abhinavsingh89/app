package org.concordia.kingdoms.adapter;

import java.util.List;

import org.concordia.kingdoms.board.ICoordinate;
import org.concordia.kingdoms.board.TDCoordinate;
import org.concordia.kingdoms.domain.Castle;
import org.concordia.kingdoms.domain.Color;
import org.concordia.kingdoms.domain.Tile;
import org.concordia.kingdoms.domain.TileType;

import com.google.common.collect.Lists;

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
	public static org.concordia.kingdoms.jaxb.Entry newJaxbEntry(
			ICoordinate coordinate, org.concordia.kingdoms.jaxb.Tile tile,
			org.concordia.kingdoms.jaxb.Castle castle) {
		org.concordia.kingdoms.jaxb.Entry jaxbEntry = new org.concordia.kingdoms.jaxb.Entry();
		if (coordinate instanceof TDCoordinate) {
			TDCoordinate tdCoordinate = ((TDCoordinate) coordinate);
			jaxbEntry.setRow(tdCoordinate.getRow());
			jaxbEntry.setColumn(tdCoordinate.getColumn());
		}
		jaxbEntry.setTile(tile);
		jaxbEntry.setCastle(castle);
		return jaxbEntry;
	}

	/**
	 * builds list of new Tile objects from Jaxb Tile list
	 * 
	 * @param - list of jaxb Tile object
	 * @return A new List of new Tile objects
	 */

	public static List<Tile> newTiles(
			List<org.concordia.kingdoms.jaxb.Tile> jaxbTiles) {
		if (jaxbTiles == null) {
			return null;
		}
		List<Tile> tiles = Lists.newArrayList();
		for (org.concordia.kingdoms.jaxb.Tile jaxbTile : jaxbTiles) {
			tiles.add(newTile(jaxbTile));
		}
		return tiles;
	}

	/**
	 * builds list of new Jaxb Tile objects from Tile list
	 * 
	 * @param - list of Tile object
	 * @return A new List of new Jaxb Tile objects
	 */

	public static List<org.concordia.kingdoms.jaxb.Tile> newJaxbTiles(
			List<Tile> tiles) {
		if (tiles == null) {
			return null;
		}
		List<org.concordia.kingdoms.jaxb.Tile> jaxbTiles = Lists.newArrayList();

		for (Tile tile : tiles) {
			jaxbTiles.add(newJaxbTile(tile));
		}

		return jaxbTiles;
	}

	public static org.concordia.kingdoms.jaxb.Castle newJaxbCastle(
			final Castle castle) {
		final String castleName = castle.getName();
		final Color color = castle.getColor();
		final Integer castleRank = castle.getValue();
		// prepare a new Jaxb Tile
		org.concordia.kingdoms.jaxb.Castle jaxbCastle = new org.concordia.kingdoms.jaxb.Castle();
		jaxbCastle.setName(castleName);
		jaxbCastle.setRank(castleRank);
		jaxbCastle.setColor(org.concordia.kingdoms.jaxb.Color.valueOf(color
				.toString()));
		return jaxbCastle;
	}

	public static org.concordia.kingdoms.jaxb.Tile newJaxbTile(final Tile tile) {
		String tileName = tile.getName();
		TileType tileType = tile.getType();
		Integer value = tile.getValue();
		// prepare a new Jaxb Tile
		org.concordia.kingdoms.jaxb.Tile jaxbTile = new org.concordia.kingdoms.jaxb.Tile();
		jaxbTile.setName(tileName);
		jaxbTile.setValue(value);
		jaxbTile.setType(org.concordia.kingdoms.jaxb.TileType.valueOf(tileType
				.name()));
		return jaxbTile;
	}

}
