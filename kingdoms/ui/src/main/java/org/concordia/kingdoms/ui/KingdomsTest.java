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
import org.concordia.kingdoms.board.Score;
import org.concordia.kingdoms.board.TDCoordinate;
import org.concordia.kingdoms.board.factory.TDBoardBuilder;
import org.concordia.kingdoms.domain.Color;
import org.concordia.kingdoms.exceptions.GameException;
import org.concordia.kingdoms.strategies.RandomStrategy;
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

	/**
	 * Entry point function
	 */
	public static void main(String[] args) throws IOException, GameException {
		// 2 dimensional kingdoms game with 3 levels
		final Kingdoms<TDCoordinate> kingdoms = new TDKingdoms(
				new TDBoardBuilder(), 3);
		// players
		List<Player<TDCoordinate>> players = Lists.newArrayList();

		System.out.println("1.Resume the saved game - Press r");
		System.out.println("2.New Game - Press  n ");

		final BufferedReader br = new BufferedReader(new InputStreamReader(
				System.in));

		final String reply = br.readLine();

		if ("r".equals(reply.toLowerCase().trim())) {
			System.out
					.println("Give Absolute path to the file you saved the xml");
			String filePath = br.readLine();
			kingdoms.resume(new File(filePath));
			players = kingdoms.getPlayers();
		}

		if ("n".equals(reply.toLowerCase().trim())) {
			initializePlayers(br, players);
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

		System.out.println();
		System.out.println();

		while (kingdoms.getEpochCounter().isNextAvailable()) {
			while (!kingdoms.isLevelCompleted()) {
				for (final Player<TDCoordinate> player : players) {
					log.info("Save Game - Press s");
					final String str = br.readLine();
					if ("s".equals(str.toLowerCase().trim())) {
						saveMyGame(br, kingdoms);
					}
					log.info(player.getName() + ">");
					player.myTurn();
					presentable.present();
					System.out.println();
					System.out
							.println("________________________________________________________________________________");
					System.out.println();
				}
			}
			log.info(kingdoms.getEpochCounter().getCurrentLevel()
					+ " Level Completed!!");
			Map<Color, Score> scoreCard = kingdoms.score();
			assignScore(players, scoreCard);
			printFinalScore(players, scoreCard);
			Collections.sort(players, Player.PlayerComparator.INSTANCE);
			if (kingdoms.getEpochCounter().isNextAvailable()) {
				kingdoms.moveToNextLevel();
				presentable = new Console<TDCoordinate>(kingdoms.getEntries());
				presentable.present();
				System.out.println();
				System.out.println();
			}
		}
		System.out.println("----GAME FINISHED----");
	}

	public static void printFinalScore(List<Player<TDCoordinate>> players,
			Map<Color, Score> finalScore) {
		if (finalScore == null) {
			System.out.println("No Entry Found");
			return;
		}
		System.out
				.println("============================================================================");
		System.out.println("Current Level Score:");
		Iterator<Color> itr = finalScore.keySet().iterator();
		while (itr.hasNext()) {
			Color color = itr.next();
			Score score = finalScore.get(color);
			System.out.print(color + " ");
			System.out.print(score.getRowScore() + " ");
			System.out.print(score.getColumnScore() + " ");
			System.out.println(score.score());
		}
		System.out
				.println("--------------------------------------------------------------------------");
		System.out.println("Player's Score:");

		for (Player<?> player : players) {
			System.out.println("Name:" + player.getName());
			System.out.println("Color:" + player.getChosenColor());
			System.out.println("Score:" + player.getTotalScore());
		}
		System.out
				.println("============================================================================");
	}

	private static void assignScore(final List<Player<TDCoordinate>> players,
			Map<Color, Score> scoreCard) {
		for (Player<?> player : players) {
			player.addNewScore(scoreCard.get(player.getChosenColor()));
		}
	}

	private static void saveMyGame(BufferedReader br, final Kingdoms kingdoms)
			throws GameException, IOException {
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

		Player<TDCoordinate> randomStrategyPlayer = Player.newPlayer(null,
				Color.BLUE);

		randomStrategyPlayer.setName("random_player");
		randomStrategyPlayer.setPlayStrategy(new RandomStrategy());
		players.add(randomStrategyPlayer);

		log.debug("Random Strategy Player is in the Game");

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
