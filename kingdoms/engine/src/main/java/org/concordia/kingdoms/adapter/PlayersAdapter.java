package org.concordia.kingdoms.adapter;

import java.util.List;
import java.util.Map;

import org.concordia.kingdoms.Player;
import org.concordia.kingdoms.board.TDCoordinate;
import org.concordia.kingdoms.domain.Castle;
import org.concordia.kingdoms.domain.Coin;
import org.concordia.kingdoms.domain.CoinType;
import org.concordia.kingdoms.domain.Color;
import org.concordia.kingdoms.domain.Tile;

import com.google.common.collect.Lists;

public class PlayersAdapter
		implements
		IAdapter<List<Player<TDCoordinate>>, List<org.concordia.kingdoms.jaxb.Player>> {

	public List<org.concordia.kingdoms.jaxb.Player> convertTo(
			List<Player<TDCoordinate>> players) {
		List<org.concordia.kingdoms.jaxb.Player> jaxbPlayers = Lists
				.newArrayList();
		for (final Player<TDCoordinate> player : players) {
			String name = player.getName();
			int score = player.getScore();
			Tile startTile = player.getStartingTile();
			Map<CoinType, List<Coin>> coins = player.getCoins();
			Map<Integer, List<Castle>> castles = player.getCastles();
			Color color = player.getChosenColor();

			org.concordia.kingdoms.jaxb.Player jaxbPlayer = new org.concordia.kingdoms.jaxb.Player();
			jaxbPlayer.setName(name);
			jaxbPlayer.setScore(score);
			jaxbPlayer.setChosenColor(org.concordia.kingdoms.jaxb.Color
					.valueOf(color.name()));

			jaxbPlayers.add(jaxbPlayer);
		}
		return jaxbPlayers;
	}

	public List<Player<TDCoordinate>> convertFrom(
			List<org.concordia.kingdoms.jaxb.Player> jaxbPlayers) {

		final List<Player<TDCoordinate>> players = Lists.newArrayList();

		for (org.concordia.kingdoms.jaxb.Player jaxbPlayer : jaxbPlayers) {
			String name = jaxbPlayer.getName();
			org.concordia.kingdoms.jaxb.Color jaxbColor = jaxbPlayer
					.getChosenColor();
			int score = jaxbPlayer.getScore();
			org.concordia.kingdoms.jaxb.Tile startTile = jaxbPlayer
					.getStartingTile();
			List<org.concordia.kingdoms.jaxb.Castle> castles = jaxbPlayer
					.getCastles();
			Player<TDCoordinate> player = Player.newPlayer(name,
					Color.valueOf(jaxbColor.name()));
			player.setScore(score);

			players.add(player);
		}
		return players;
	}
}
