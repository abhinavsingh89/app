package org.concordia.kingdoms;

/** Initializer class for the game. */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.concordia.kingdoms.board.factory.TileBank;
import org.concordia.kingdoms.exceptions.GameException;
import org.concordia.kingdoms.exceptions.GameRuleException;
import org.concordia.kingdoms.tokens.Color;
import org.concordia.kingdoms.tokens.Tile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
/**
 * 
 * @author Team K
 * @version 1.0-SNAPSHOT
 *
 */
public class KingdomsTest {

	private static final Logger log = LoggerFactory
			.getLogger(KingdomsTest.class);
	/**
	 * Entry point function
	 */
	public static void main(String[] args) throws IOException, GameException {
		final Kingdoms kingdoms = new Kingdoms();
		final List<Player> players = Lists.newArrayList();
		String input = "";
		final BufferedReader br = new BufferedReader(new InputStreamReader(
				System.in));
		initializePlayers(br, players);
		kingdoms.start(players);
		System.out.println("Current Level: "
				+ kingdoms.getEpochCounter().getCurrentLevel());
		kingdoms.present();
		while (!"exit".equals(input)
				&& kingdoms.getEpochCounter().isNextAvailable()) {
			while (!kingdoms.isLevelCompleted()) {
				for (final Player player : players) {
					boolean isValidInput = false;
					while (!isValidInput) {
						try {
							System.out
									.println(player.getName()
											+ ">"
											+ "Press 1 to pick a Tile any other number for Castle");
							int tileOrCastle = Integer.parseInt(br.readLine());
							if (tileOrCastle == 1) {
								boolean isValidPosition = false;
								while (!isValidPosition)
									try {
										final Tile tile = TileBank
												.getTileBank().getTile();
										System.out.println(tile.showTile());
										int row = getRow(br);
										int column = getColumn(br);
										player.putTile(tile, row, column);
										isValidPosition = true;
									} catch (GameRuleException ex) {
										log.debug(ex.getMessage());
									}

							} else {
								boolean isValidCastle = false;
								while (!isValidCastle) {
									try {
										System.out
												.println("Enter Castle color");
										final Color color = stringToColor(br
												.readLine());
										System.out.println("Enter Castle Rank");
										int rank = Integer.parseInt(br
												.readLine());
										int row = getRow(br);
										int column = getColumn(br);
										player.putCastle(
												player.getCastle(rank, color),
												row, column);
										isValidCastle = true;
									} catch (GameRuleException ex) {
										log.debug(ex.getMessage());
									}
								}// castle while ending

							}// else ending
							isValidInput = true;
							kingdoms.present();
						} catch (NumberFormatException ex) {
							log.debug("Invalid input:" + ex.getMessage());
						}
					}
				}
			}
			System.out.println("Level Completed!!");
			// TODO:rearrange players according to winners
			kingdoms.getEpochCounter().goNextLevel();
		}
		if ("exit".equals(input)) {
			System.out.println("--Game Over--");
		}
	}

	private static int getColumn(final BufferedReader br) throws IOException {
		log.debug("Enter an empty space column ");
		int column = Integer.parseInt(br.readLine());
		return column;
	}

	private static int getRow(final BufferedReader br) throws IOException {
		log.debug("Enter an empty space row ");
		int row = Integer.parseInt(br.readLine());
		return row;
	}

	private static void initializePlayers(BufferedReader br,
			List<Player> players) throws NumberFormatException, IOException {
		log.debug("Enter number of Players");
		final int playersCount = Integer.parseInt(br.readLine());

		for (int i = 0; i < playersCount; i++) {
			System.out.println("Enter Player " + (i + 1) + " Name:");
			String name = br.readLine();
			if ("".equals(name)) {
				name = "Player" + i;
				System.out.println("Assigned default name " + name);
			}
			final Color[] colors = Color.values();
			System.out.println("Choose one color: " + Arrays.toString(colors));
			final String chosenColor = br.readLine();
			final Color playerColor = stringToColor(chosenColor);
			players.add(Player.newPlayer(name, new Color[] { playerColor }));
		}
		Collections.shuffle(players);
	}

	private static Color stringToColor(String chosenColor) {
		for (Color color : Color.values()) {
			if (color.toString().equalsIgnoreCase(chosenColor)) {
				return color;
			}
		}
		throw new RuntimeException("Choose valid color");
	}
}
