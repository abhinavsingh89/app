package org.concordia.kingdoms.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.concordia.kingdoms.Kingdoms;
import org.concordia.kingdoms.Player;
import org.concordia.kingdoms.board.IEntriesAware;
import org.concordia.kingdoms.board.Score;
import org.concordia.kingdoms.board.TDCoordinate;
import org.concordia.kingdoms.board.factory.TDBoardBuilder;
import org.concordia.kingdoms.domain.Color;
import org.concordia.kingdoms.exceptions.GameException;
import org.concordia.kingdoms.strategies.IStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

/**
 * Game Client
 * 
 * @author Team K
 * @since 1.0
 * 
 */
public class KingdomsTest {

	private static final Logger log = LoggerFactory
			.getLogger(KingdomsTest.class);

	private Kingdoms<TDCoordinate> kingdoms;

	public void start() throws IOException, GameException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {

		// 2 dimensional kingdoms game with 3 levels
		kingdoms = new TDKingdoms(new TDBoardBuilder(), 6);
		// players
		List<Player<TDCoordinate>> players = Lists.newArrayList();

		System.out.println("1.Resume the saved game - Press r");
		System.out.println("2.New Game - Press  any key");

		// console reader
		final BufferedReader br = new BufferedReader(new InputStreamReader(
				System.in));

		// user input
		final String reply = Console.readString(br, "");
		// if resume
		if ("r".equals(reply.toLowerCase().trim())) {
			System.out
					.println("Give Absolute path to the file you saved the xml");
			String filePath = br.readLine();
			kingdoms.resume(new File(filePath));
			players = kingdoms.getPlayers();
			assignStrategies(players, br);
		}
		// new game
		else {
			players = initPlayers(br);
			kingdoms.start(players);
			assignStrategies(players, br);
			for (Player<?> player : players) {
				if (!player.isStartingTileUsed()) {
					player.setStartingTile(kingdoms.drawTile());
				}
			}
		}

		Presentable presentable = new Console<TDCoordinate>(
				kingdoms.getEntries());

		presentable.present();

		System.out.println("");
		System.out.println("");

		while (kingdoms.isNextAvailable()) {

			log.info("Level: " + kingdoms.getEpochCounter().getCurrentLevel());

			while (!kingdoms.isLevelCompleted()) {

				for (final Player<TDCoordinate> player : players) {

					// break out of this loop; when the level finished, before
					// all the players got a chance in clock wise
					if (kingdoms.isLevelCompleted()) {
						break;
					}

					log.info("Save Game - Press s");

					final String str = br.readLine();
					if ("s".equals(str.toLowerCase().trim())) {
						saveMyGame(br);
					}
					System.out.println(player.getName() + ">");
					player.takeTurn();
					presentable.present();
					// calculate and show score after each turn
					Map<Color, Score> scoreMap = kingdoms.score();
					printFinalScore(Lists.newArrayList(player), scoreMap);

					System.out.println("");
					System.out
							.println("________________________________________________________________________________");
					System.out.println("");

				}
			}
			log.info(kingdoms.getEpochCounter().getCurrentLevel()
					+ " Level Completed!!");

			// calculate the score
			Map<Color, Score> scoreMap = kingdoms.score();
			// add the final level score to the epoch
			kingdoms.getEpochCounter().addNewScore(scoreMap);
			printFinalScore(players, scoreMap);

			Collections.sort(players, Player.PlayerComparator.INSTANCE);

			if (kingdoms.getEpochCounter().isNextAvailable()) {
				kingdoms.moveToNextLevel();
				presentable = new Console<TDCoordinate>(kingdoms.getEntries());
				presentable.present();
				System.out.println("");
				System.out.println("");
			}
		}
		System.out.println("----GAME FINISHED----");
	}

	private void assignStrategies(List<Player<TDCoordinate>> players,
			final BufferedReader br) throws IOException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		for (Player<TDCoordinate> player : players) {

			System.out.println("Choose a Strategy for player :"
					+ player.getName() + ">");
			player.setPlayStrategy(readStrategy(br));
		}
	}

	public static void printFinalScore(List<Player<TDCoordinate>> players,
			Map<Color, Score> finalScore) {
		if (finalScore == null) {
			System.out.println("No Entry Found");
			return;
		}
		System.out.println("Current Level Score:");
		Iterator<Color> itr = finalScore.keySet().iterator();
		while (itr.hasNext()) {
			Color color = itr.next();
			Score score = finalScore.get(color);
			System.out.println(color + " ");
			System.out.print(score.getRowScore() + " ");
			System.out.print(score.getColumnScore() + " ");
			System.out.println(score.score() + "");
		}
		System.out.println("Player's Score:");

		for (Player<?> player : players) {
			System.out.println("Name:" + player.getName());
			System.out.println("Color:" + player.getChosenColor());
			System.out.println("Score:" + player.getTotalScore());
		}
	}

	private void saveMyGame(BufferedReader br) throws GameException,
			IOException {
		log.debug("Give a name to file");
		String fileName = br.readLine();
		kingdoms.setFileName(fileName);
		kingdoms.save();
		log.debug("Game Saved successfully");
	}

	/**
	 * initialize players with the name and color
	 * 
	 * @param br
	 *            - {@link BufferedReader}
	 * @param players
	 * @throws IOException
	 */

	private List<Player<TDCoordinate>> initPlayers(BufferedReader br)
			throws IOException, InstantiationException, IllegalAccessException,
			ClassNotFoundException {

		final List<Player<TDCoordinate>> players = Lists.newArrayList();

		int totalPlayers = Console.readInt(br, "Enter Number of Players");

		while (totalPlayers > 0) {
			// give a name to player
			log.info("Enter Player Name:");
			String name = br.readLine();
			if ("".equals(name)) {
				name = "default" + totalPlayers;
				log.debug("Assigned default name " + name);
			}
			final Color color = choseColor(br);
			Player<TDCoordinate> player = Player.newPlayer(name, color);
			players.add(player);
			totalPlayers--;
		}
		Collections.shuffle(players);
		return players;
	}

	private IStrategy<TDCoordinate> readStrategy(BufferedReader br)
			throws IOException, InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		// all the nick names for each strategy class
		for (Strategy strategy : Strategy.values()) {
			System.out.println(strategy.name());
		}
		// pick a strategy
		String strategyClass = null;
		while (strategyClass == null) {
			String strategyType = Console.readString(br, "Choose a Strategy:");
			strategyClass = Strategy.valueOf(strategyType.toUpperCase())
					.getClassName();
		}
		// create the object of that strategy
		Object strategy = Class.forName(strategyClass).newInstance();
		// in case the strategy needs to know about the entries
		if (strategy instanceof IEntriesAware) {
			((IEntriesAware) strategy).setEntries(kingdoms.getEntries());
		}
		return (IStrategy<TDCoordinate>) strategy;
	}

	private Color choseColor(BufferedReader br) throws IOException {
		// give a color to player
		final List<Color> colors = Arrays.asList(Color.values());
		Color color = null;
		boolean invalidColor = true;
		while (invalidColor) {
			color = readColor(br, colors);
			if (color != null) {
				invalidColor = false;
			}
		}// while end
		return color;
	}

	private Color readColor(BufferedReader br, List<Color> colors)
			throws IOException {

		final String chosenColor = Console.readString(br, "Choose one color: "
				+ colors);
		Color retColor = null;
		for (Color color : colors) {
			if (chosenColor.equalsIgnoreCase(color.toString())) {
				retColor = color;
			}
		}
		if (retColor != null) {
			colors.set(colors.indexOf(retColor), null);
		}
		return retColor;
	}

	/**
	 * Entry point function
	 * 
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static void main(String[] args) throws IOException, GameException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		KingdomsTest testUI = new KingdomsTest();
		testUI.start();
	}
}
