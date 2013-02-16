package org.concordia.kingdoms.adapter;

import java.util.List;
import java.util.Map;

import org.concordia.kingdoms.Player;
import org.concordia.kingdoms.tokens.Castle;
import org.concordia.kingdoms.tokens.Coin;
import org.concordia.kingdoms.tokens.CoinType;
import org.concordia.kingdoms.tokens.Color;
import org.concordia.kingdoms.tokens.Tile;

import com.google.common.collect.Lists;

public class PlayersAdapter implements
		IAdapter<List<Player>, List<org.concordia.kingdoms.jaxb.Player>> {

	public List<org.concordia.kingdoms.jaxb.Player> convertTo(
			List<Player> players) {
		List<org.concordia.kingdoms.jaxb.Player> jaxbPlayers = Lists
				.newArrayList();
		for (final Player player : players) {
			String name = player.getName();
			int score = player.getScore();
			Tile startTile = player.getStartingTile();
			Map<CoinType, List<Coin>> coins = player.getCoins();
			Map<Integer, List<Castle>> castles = player.getCastles();

			org.concordia.kingdoms.jaxb.Player jaxbPlayer = new org.concordia.kingdoms.jaxb.Player();
			jaxbPlayer.setName(name);
			jaxbPlayer.setScore(score);

			jaxbPlayers.add(jaxbPlayer);
		}
		return jaxbPlayers;
	}

	public List<Player> convertFrom(
			List<org.concordia.kingdoms.jaxb.Player> jaxbPlayers) {

		final List<Player> players = Lists.newArrayList();

		for (org.concordia.kingdoms.jaxb.Player jaxbPlayer : jaxbPlayers) {
			String name = jaxbPlayer.getName();
			org.concordia.kingdoms.jaxb.Color jaxbColor = jaxbPlayer
					.getChosenColor();
			int score = jaxbPlayer.getScore();
			org.concordia.kingdoms.jaxb.Tile startTile = jaxbPlayer
					.getStartingTile();
			List<org.concordia.kingdoms.jaxb.Castle> castles = jaxbPlayer
					.getCastles();
			Player player = Player.newPlayer(name,
					Color.valueOf(jaxbColor.name()));
			player.setScore(score);

			players.add(player);
		}
		return players;
	}
}
