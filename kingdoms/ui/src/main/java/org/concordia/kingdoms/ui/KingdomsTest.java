package org.concordia.kingdoms.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.concordia.kingdoms.Kingdoms;
import org.concordia.kingdoms.Player;
import org.concordia.kingdoms.TileBank;
import org.concordia.kingdoms.board.Score;
import org.concordia.kingdoms.board.TDCoordinate;
import org.concordia.kingdoms.board.factory.TDBoardBuilder;
import org.concordia.kingdoms.domain.Color;
import org.concordia.kingdoms.domain.Tile;
import org.concordia.kingdoms.exceptions.GameException;
import org.concordia.kingdoms.exceptions.GameRuleException;
import org.concordia.kingdoms.spring.SpringContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

/**
 * Initializer class for the game.
 * 
 * @author Team K
 * @since 1.0
 * 
 */
public class KingdomsTest {

	private static final Logger log = LoggerFactory
			.getLogger(KingdomsTest.class);

	/**
	 * Entry point function
	 */
	public static void main(String[] args) throws IOException, GameException {
		final Kingdoms<TDCoordinate> kingdoms = new TDKingdoms(
				new TDBoardBuilder(), 3);
		final List<Player<TDCoordinate>> players = Lists.newArrayList();
		String input = "";
		System.out.println("1.Resume the saved game - Press 1");
		System.out.println("2.New Game - Press any key to continue");
		System.out.println("3.Exit- Press 3");
		final BufferedReader br = new BufferedReader(new InputStreamReader(
				System.in));
		String reply = br.readLine();
		if ("3".equals(reply)) {
			System.exit(0);
		}
		if ("1".equals(reply)) {
			System.out
					.println("Give Absolute path to the file you saved the xml");
			String filePath = br.readLine();
			kingdoms.resume(new File(filePath));
		}

		initializePlayers(br, players);
		kingdoms.start(players);
		Presentable presentable = new Console<TDCoordinate>(
				kingdoms.getEntries());
		System.out.println("Current Level: "
				+ kingdoms.getEpochCounter().getCurrentLevel());
		presentable.present();

		while (kingdoms.getEpochCounter().isNextAvailable()) {
			while (!kingdoms.isLevelCompleted()) {
				for (final Player<TDCoordinate> player : players) {

					if (player.getStartingTile() == null) {
						System.out.println(player.getName() + ">"
								+ "Press any key to pick a Starting Tile");
						br.readLine();

						Tile startingTile = SpringContainer.INSTANCE.getBean(
								"tileBank", TileBank.class).pickTile();
						player.setStartingTile(startingTile);
						System.out.println(startingTile.show());
					}

					boolean isValidInput = false;
					while (!isValidInput) {
						try {
							System.out
									.println(player.getName()
											+ ">"
											+ "Press 1 to pick a Tile any other number for Castle");
							String data = br.readLine();
							if ("save".equals(data)) {
								saveMyGame(kingdoms);
								continue;
							}
							int tileOrCastle = Integer.parseInt(data);
							if (tileOrCastle == 1) {
								placeTile(kingdoms, br, player);
							} else {
								placeCastle(kingdoms, br, player);
							}// else ending
							isValidInput = true;
							presentable.present();
						} catch (NumberFormatException ex) {
							log.error("Invalid input:" + ex.getMessage());
						}
					}
				}
			}
			System.out.println("Level Completed!!");
			Map<Color, Score> scoreCard = kingdoms.score();
			assignScore(players, scoreCard);
			Collections.sort(players, Player.PlayerComparator.INSTANCE);
		}

		// TODO:rearrange players according to winners
		kingdoms.getEpochCounter().goNextLevel();
		if ("exit".equals(input)) {
			System.out.println("--Game Exit--");
		}
	}

	private static void assignScore(final List<Player<TDCoordinate>> players,
			Map<Color, Score> scoreCard) {
		for (Player<?> player : players) {
			player.addNewScore(scoreCard.get(player.getChosenColor()));
		}
	}

	private static void placeCastle(final Kingdoms<TDCoordinate> kingdoms,
			final BufferedReader br, final Player<TDCoordinate> player)
			throws IOException {
		boolean isValidCastle = false;
		while (!isValidCastle) {
			try {
				System.out.println("Enter Castle Rank");
				int rank = Integer.parseInt(br.readLine());
				int row = getRow(br);
				int column = getColumn(br);

				// player must chose valid position to
				// place the tile
				while (!kingdoms.isValidPosition(TDCoordinate.newInstance(row,
						column))) {
					System.out.println("Not a Valid Position");
					row = getRow(br);
					column = getColumn(br);
				}
				// get a ranked castle and put it on
				// board
				player.putCastle(player.getCastle(rank),
						TDCoordinate.newInstance(row, column));
				isValidCastle = true;
			} catch (GameRuleException ex) {
				log.error(ex.getMessage());
			}
		}// castle while ending
	}

	private static void placeTile(final Kingdoms<TDCoordinate> kingdoms,
			final BufferedReader br, final Player<TDCoordinate> player)
			throws IOException, GameRuleException {
		// show the random tile picked up from
		// the tile bank
		final Tile tile = SpringContainer.INSTANCE.getBean("tileBank",
				TileBank.class).pickTile();
		System.out.println(tile.show());
		int row = getRow(br);
		int column = getColumn(br);

		// player must chose valid position to
		// place the tile
		while (!kingdoms.isValidPosition(TDCoordinate.newInstance(row, column))) {
			System.out.println("Not a Valid Position");
			row = getRow(br);
			column = getColumn(br);
		}
		player.putTile(tile, TDCoordinate.newInstance(row, column));
	}

	private static void saveMyGame(final Kingdoms kingdoms)
			throws GameException {
		log.debug("Game Saved successfully");
		kingdoms.save();
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
			List<Player<TDCoordinate>> players) throws IOException {
		log.debug("Enter number of Players");
		boolean isValidInput = false;
		while (!isValidInput) {
			try {
				int playersCount = Integer.parseInt(br.readLine());
				for (int i = 0; i < playersCount; i++) {
					log.debug("Enter Player " + (i + 1) + " Name:");
					String name = br.readLine();
					if ("".equals(name)) {
						name = "Player" + i;
						log.debug("Assigned default name " + name);
					}
					final Color[] colors = Color.values();
					boolean isValidColor = false;
					while (!isValidColor) {
						try {
							log.debug("Choose one color: "
									+ Arrays.toString(colors));
							final String chosenColor = br.readLine();
							final Color playerColor = stringToColor(chosenColor);
							players.add(Player.newPlayer(name, playerColor));
							isValidColor = true;
						} catch (GameException ex) {
							log.error(ex.getMessage());
						}
					}
				}
				isValidInput = true;
			} catch (NumberFormatException ex) {
				log.error(ex.getMessage());
			}
		}

		Collections.shuffle(players);
	}

	private static Color stringToColor(String chosenColor) throws GameException {
		for (Color color : Color.values()) {
			if (color.toString().equalsIgnoreCase(chosenColor)) {
				return color;
			}
		}
		throw new GameException("Choose valid color");
	}
}
