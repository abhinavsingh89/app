package org.concordia.kingdoms.strategies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.concordia.kingdoms.Player;
import org.concordia.kingdoms.board.Entry;
import org.concordia.kingdoms.board.TDCoordinate;
import org.concordia.kingdoms.domain.Castle;
import org.concordia.kingdoms.domain.Tile;
import org.concordia.kingdoms.exceptions.GameRuleException;
import org.concordia.kingdoms.ui.Console;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used to implement strategy of human player
 * 
 * @author Team K
 * @since 1.1
 */
public class UserInputStrategy implements IStrategy<TDCoordinate> {

	private static final Logger log = LoggerFactory
			.getLogger(UserInputStrategy.class);

	public Entry<TDCoordinate> getEntry(Player<TDCoordinate> player,
			List<Tile> tiles, List<Castle> castles,
			List<TDCoordinate> emptyCoordinates) throws GameRuleException {

		Entry<TDCoordinate> entry = null;

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			boolean invalidInput = true;
			while (invalidInput) {

				if (!player.isStartingTileUsed()) {
					System.out.print("Press 1 for Starting Tile");
					System.out.println("- " + player.getStartingTile().show());
				}
				System.out.println("Press 2 for Castle");
				System.out.println("Press 3 to draw facedown Tile");

				String input = br.readLine();
				// choose starting tile
				if ("1".equals(input.trim())) {
					if (player.isStartingTileUsed()) {
						System.out.println("Starting Tile already used");
						continue;
					} else {
						final TDCoordinate coordinate = getCoordinate(
								emptyCoordinates, br);
						final Tile startingTile = player.getStartingTile();
						entry = new Entry<TDCoordinate>(coordinate,
								startingTile);
						return entry;
					}
				}
				// choose castle
				if ("2".equals(input.trim())) {

					final TDCoordinate coordinate = getCoordinate(
							emptyCoordinates, br);

					boolean invalidCastle = true;

					while (invalidCastle) {
						int rank = getNumber(br, "Castle Rank");
						Castle castle = Castle.newCastle(rank,
								player.getChosenColor());
						if (!castles.contains(castle)) {
							log.error("Castle not Available with the rank "
									+ rank);
							continue;
						}
						entry = new Entry<TDCoordinate>(coordinate, castle);
						return entry;
					}

				}
				// draw a tile
				if ("3".equals(input.trim())) {
					final List<Tile> possessedTiles = player
							.getPossessedTiles();

					int i = 0;
					for (Tile tile : possessedTiles) {
						log.info(i++ +" "+ tile.show());
					}
					
					int in = Console.readInt(br,
							"Choose any one Possesed tiles:");

					final Tile tile = player.drawTile(in);
					log.info(tile.show());

					final TDCoordinate coordinate = getCoordinate(
							emptyCoordinates, br);

					entry = new Entry<TDCoordinate>(coordinate, tile);
					return entry;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}

	private TDCoordinate getCoordinate(List<TDCoordinate> emptyCoordinates,
			BufferedReader br) throws IOException {
		boolean invalidCoordinate = true;
		TDCoordinate coordinate = null;
		while (invalidCoordinate) {
			int row = getNumber(br, "row");
			int column = getNumber(br, "column");
			coordinate = TDCoordinate.newInstance(row, column);
			if (!emptyCoordinates.contains(coordinate)) {
				log.error("invalid row/column number");
				continue;
			}
			invalidCoordinate = false;
		}
		return coordinate;
	}

	private static int getNumber(final BufferedReader br, String inputStr)
			throws IOException {
		System.out.println("Enter " + inputStr);
		int index = 0;
		boolean flag = true;
		while (flag) {
			try {
				index = Integer.parseInt(br.readLine());
				flag = false;
			} catch (NumberFormatException e) {
				System.out.println("Invalid " + inputStr);
			}
		}
		return index;
	}

}
