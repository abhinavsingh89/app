package org.concordia.kingdoms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import org.concordia.kingdoms.board.factory.TileBank;
import org.concordia.kingdoms.tokens.Color;

import com.google.common.collect.Lists;

public class KingdomsTest {

	public static void main(String[] args) throws IOException {
		final Game kingdoms = new Kingdoms();
		final List<Player> players = Lists.newArrayList();
		String input = "";
		final BufferedReader br = new BufferedReader(new InputStreamReader(
				System.in));
		initializePlayers(br, players);
		kingdoms.start(players);
		kingdoms.present();
		while (!"exit".equals(input)) {
			for (final Player player : players) {
				System.out.print(player.getName() + ">");
				System.out
						.println("Press 1 to pick a Tile any other number for Castle");
				int tileOrCastle = Integer.parseInt(br.readLine());
				if (tileOrCastle == 1) {
					int row = getRow(br);
					int column = getColumn(br);
					player.putTile(TileBank.getTileBank().getTile(), row,
							column);
				} else {
					System.out.println("Enter Castle color");
					Color color = stringToColor(br.readLine());
					System.out.println("Enter Castle Rank");
					int rank = Integer.parseInt(br.readLine());
					int row = getRow(br);
					int column = getColumn(br);
					player.putCastle(player.getCastle(rank, color), row, column);
				}
				kingdoms.present();
			}
		}

		kingdoms.start(players);
		kingdoms.present();
	}

	private static int getColumn(final BufferedReader br) throws IOException {
		System.out.println("Enter an empty space column ");
		int column = Integer.parseInt(br.readLine());
		return column;
	}

	private static int getRow(final BufferedReader br) throws IOException {
		System.out.println("Enter an empty space row ");
		int row = Integer.parseInt(br.readLine());
		return row;
	}

	private static void initializePlayers(BufferedReader br,
			List<Player> players) throws NumberFormatException, IOException {
		System.out.println("Enter number of Players");
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
			String chosenColor = br.readLine();
			Color playerColor = stringToColor(chosenColor);
			players.add(Player.newPlayer(name, new Color[] { playerColor }));
		}
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
