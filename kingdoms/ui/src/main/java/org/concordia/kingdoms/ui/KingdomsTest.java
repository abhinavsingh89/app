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
import org.concordia.kingdoms.strategies.MaximizeStrategy;
import org.concordia.kingdoms.strategies.MinimizeStrategy;
import org.concordia.kingdoms.strategies.UserInputStrategy;
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

	Kingdoms<TDCoordinate> kingdoms;

	public void start() throws IOException, GameException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {

		// 2 dimensional kingdoms game with 3 levels
		kingdoms = new TDKingdoms(new TDBoardBuilder(), 3);
		// players
		List<Player<TDCoordinate>> players = Lists.newArrayList();

		Console.print("1.Resume the saved game - Press r");
		Console.print("2.New Game - Press  any key");

		final BufferedReader br = new BufferedReader(new InputStreamReader(
				System.in));

		final String reply = Console.readString(br, "");

		if ("r".equals(reply.toLowerCase().trim())) {
			Console.print("Give Absolute path to the file you saved the xml");
			String filePath = br.readLine();
			kingdoms.resume(new File(filePath));
			players = kingdoms.getPlayers();

			for (Player player : players) {
				if (player.getPlayStrategy().getClass().getName()
						.equals(MaximizeStrategy.class.getName())) {
					MaximizeStrategy maximizeStrategy = (MaximizeStrategy) player
							.getPlayStrategy();
					maximizeStrategy.setEntries(kingdoms.getEntries());
				} else {
					if (player.getPlayStrategy().getClass().getName()
							.equals(MinimizeStrategy.class.getName())) {
						MinimizeStrategy minimizeStrategy = (MinimizeStrategy) player
								.getPlayStrategy();
						minimizeStrategy.setEntries(kingdoms.getEntries());
					}
				}
			}

		}

		else {
			players = initPlayers(br);

			// Player<TDCoordinate> randomStrategyPlayer = Player.newPlayer(
			// "random_player", Color.BLUE);
			//
			// randomStrategyPlayer.setPlayStrategy(new RandomStrategy());
			// players.add(randomStrategyPlayer);
			//
			// Player<TDCoordinate> maximizeStrategyPlayer = Player.newPlayer(
			// "maximize_player", Color.RED);
			//
			// players.add(maximizeStrategyPlayer);
			//
			// Player<TDCoordinate> minimizeStrategyPlayer = Player.newPlayer(
			// "minimize_player", Color.GREEN);
			//
			// players.add(minimizeStrategyPlayer);
			//
			// Player<TDCoordinate> dumbStrategyPlayer = Player.newPlayer(
			// "dumb_player", Color.YELLOW);
			// players.add(dumbStrategyPlayer);
			//
			// log.debug("Random Strategy Player is in the Game");
			// log.debug("Maximize Strategy Player is in the Game");
			// log.debug("Minimize Strategy Player is in the Game");

			kingdoms.start(players);
			for (Player<?> player : players) {
				if (!player.isStartingTileUsed()) {
					player.setStartingTile(kingdoms.drawTile());
				}
			}

		}

		Presentable presentable = new Console<TDCoordinate>(
				kingdoms.getEntries());

		log.info("Level: " + kingdoms.getEpochCounter().getCurrentLevel());

		presentable.present();

		Console.print("");
		Console.print("");

		while (kingdoms.getEpochCounter().isNextAvailable()) {
			log.info("Level: " + kingdoms.getEpochCounter().getCurrentLevel());
			while (!kingdoms.isLevelCompleted()) {
				for (final Player<TDCoordinate> player : players) {
					if (kingdoms.isLevelCompleted()) {
						break;
					}
					log.info("Save Game - Press s");
					final String str = br.readLine();
					if ("s".equals(str.toLowerCase().trim())) {
						saveMyGame(br);
					}
					log.info(player.getName() + ">");
					player.myTurn();
					presentable.present();
					Console.print("");
					Console.print("________________________________________________________________________________");
					Console.print("");
				}
			}
			log.info(kingdoms.getEpochCounter().getCurrentLevel()
					+ " Level Completed!!");
			Map<Color, Score> scoreCard = kingdoms.score();
			assignScore(players, scoreCard);

			for (Player player : players) {
				kingdoms.getEpochCounter().addNewScore(player, scoreCard);
			}

			printFinalScore(players, scoreCard);
			Collections.sort(players, Player.PlayerComparator.INSTANCE);
			if (kingdoms.getEpochCounter().isNextAvailable()) {
				kingdoms.moveToNextLevel();
				presentable = new Console<TDCoordinate>(kingdoms.getEntries());
				presentable.present();
				Console.print("");
				Console.print("");
			}
		}
		Console.print("----GAME FINISHED----");

	}

	public static void printFinalScore(List<Player<TDCoordinate>> players,
			Map<Color, Score> finalScore) {
		if (finalScore == null) {
			Console.print("No Entry Found");
			return;
		}
		Console.print("============================================================================");
		Console.print("Current Level Score:");
		Iterator<Color> itr = finalScore.keySet().iterator();
		while (itr.hasNext()) {
			Color color = itr.next();
			Score score = finalScore.get(color);
			Console.print(color + " ");
			Console.print(score.getRowScore() + " ");
			Console.print(score.getColumnScore() + " ");
			Console.print(score.score() + "");
		}
		System.out
				.print("--------------------------------------------------------------------------");
		Console.print("Player's Score:");

		for (Player<?> player : players) {
			Console.print("Name:" + player.getName());
			Console.print("Color:" + player.getChosenColor());
			Console.print("Score:" + player.getTotalScore());
		}
		System.out
				.print("============================================================================");
	}

	private void assignScore(final List<Player<TDCoordinate>> players,
			Map<Color, Score> scoreCard) {
		for (Player<?> player : players) {
			player.addNewScore(scoreCard.get(player.getChosenColor()));
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
	 * initialize players
	 * 
	 * @param br
	 *            - {@link BufferedReader}
	 * @param players
	 * @throws IOException
	 */
	private static void initializePlayers(BufferedReader br,
			List<Player<TDCoordinate>> players) throws IOException {

		log.info("You want to play: press y");

		boolean humanPlayer = "y".equals(br.readLine().toLowerCase().trim());

		if (humanPlayer) {
			log.debug("Enter your Name:");
			String name = br.readLine();
			if ("".equals(name.trim())) {
				name = "default";
				log.debug("Assigned default name " + name);
			}

			final Color[] colors = Color.values();
			boolean isValidColor = false;

			while (!isValidColor) {
				try {
					log.debug("Choose one color: " + Arrays.toString(colors));
					final String chosenColor = br.readLine();
					final Color playerColor = stringToColor(chosenColor);
					Player<TDCoordinate> player = Player.newPlayer(name,
							playerColor);
					player.setPlayStrategy(new UserInputStrategy());
					players.add(player);
					isValidColor = true;
				} catch (GameException ex) {
					log.error(ex.getMessage());
				}
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
			player.setPlayStrategy(choseStrategy(br));
			players.add(player);
			totalPlayers--;
		}
		return players;
	}

	private IStrategy<TDCoordinate> choseStrategy(BufferedReader br)
			throws IOException, InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		return readStrategy(br);
	}

	private IStrategy<TDCoordinate> readStrategy(BufferedReader br)
			throws IOException, InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		// all the nick names for each strategy class
		for (Strategy strategy : Strategy.values()) {
			Console.print(strategy.name());
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
