package org.concordia.kingdoms.adapter;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.concordia.kingdoms.Player;
import org.concordia.kingdoms.board.Score;
import org.concordia.kingdoms.board.TDCoordinate;
import org.concordia.kingdoms.domain.Castle;
import org.concordia.kingdoms.domain.Coin;
import org.concordia.kingdoms.domain.CoinType;
import org.concordia.kingdoms.domain.Color;
import org.concordia.kingdoms.domain.Tile;

import com.google.common.collect.Lists;

/**
 * This class is used to convert domain class object to Jaxb objects..
 * 
 * @author Team K
 * @since 1.1
 */
public class PlayersAdapter
		implements
		IAdapter<List<Player<TDCoordinate>>, List<org.concordia.kingdoms.jaxb.Player>> {

	public List<org.concordia.kingdoms.jaxb.Player> convertTo(
			List<Player<TDCoordinate>> players) {
		List<org.concordia.kingdoms.jaxb.Player> jaxbPlayers = Lists
				.newArrayList();
		for (final Player<TDCoordinate> player : players) {
			String name = player.getName();
			List<Score> scores = player.getScores();
			Tile startTile = player.getStartingTile();
			Map<CoinType, List<Coin>> coins = player.getCoins();
			Map<Integer, List<Castle>> castles = player.getCastles();
			Color color = player.getChosenColor();

			org.concordia.kingdoms.jaxb.Player jaxbPlayer = new org.concordia.kingdoms.jaxb.Player();

			jaxbPlayer.setName(name);
			jaxbPlayer.setStartingTile(AdapterUtil.newJaxbTile(startTile));
			Iterator<Integer> castleItr = castles.keySet().iterator();
			List<org.concordia.kingdoms.jaxb.Castle> jaxbCastles = Lists
					.newArrayList();
			while (castleItr.hasNext()) {
				Integer rank = castleItr.next();
				List<Castle> kastles = castles.get(rank);
				jaxbCastles.addAll(AdapterUtil.newJaxbCastles(kastles));
			}

			jaxbPlayer.setScores(AdapterUtil.newJaxbScores(scores));
			jaxbPlayer.setChosenColor(org.concordia.kingdoms.jaxb.Color
					.valueOf(color.name()));
			jaxbPlayer.setCastles(jaxbCastles);
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
			List<Score> scores = AdapterUtil.newScores(jaxbPlayer.getScores());

			org.concordia.kingdoms.jaxb.Tile startTile = jaxbPlayer
					.getStartingTile();
			List<org.concordia.kingdoms.jaxb.Castle> jaxbCastles = jaxbPlayer
					.getCastles();
			Player<TDCoordinate> player = Player.newPlayer(name,
					Color.valueOf(jaxbColor.name()));
			for (Score score : scores) {
				player.addNewScore(score);
			}

			player.setStartingTile(AdapterUtil.newTile(startTile));

			Map<Integer, List<Castle>> castleMap = AdapterUtil
					.reoslveCastles(AdapterUtil.newCastles(jaxbCastles));
			Iterator<Integer> itr = castleMap.keySet().iterator();

			while (itr.hasNext()) {
				Integer rank = itr.next();
				player.addCastle(rank, castleMap.get(rank));
			}

			players.add(player);
		}
		return players;
	}
}
