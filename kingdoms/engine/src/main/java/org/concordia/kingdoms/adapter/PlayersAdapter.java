package org.concordia.kingdoms.adapter;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.concordia.kingdoms.Player;
import org.concordia.kingdoms.board.Score;
import org.concordia.kingdoms.board.TDCoordinate;
import org.concordia.kingdoms.domain.Castle;
import org.concordia.kingdoms.domain.Color;
import org.concordia.kingdoms.domain.Tile;
import org.concordia.kingdoms.exceptions.GameRuleException;
import org.concordia.kingdoms.strategies.IStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private static final Logger log = LoggerFactory
			.getLogger(PlayersAdapter.class);

	public List<org.concordia.kingdoms.jaxb.Player> convertTo(
			List<Player<TDCoordinate>> players) {
		List<org.concordia.kingdoms.jaxb.Player> jaxbPlayers = Lists
				.newArrayList();
		for (final Player<TDCoordinate> player : players) {
			// player's name
			String name = player.getName();
			// player's scores
			List<Score> scores = player.getScores();
			// player's starting tile, null if already used
			Tile startTile = player.getStartingTile();
			boolean isStartingTileUsed = player.isStartingTileUsed();
			Map<Integer, List<Castle>> castles = player.getCastles();
			// player's chosen color
			Color color = player.getChosenColor();

			org.concordia.kingdoms.jaxb.Player jaxbPlayer = new org.concordia.kingdoms.jaxb.Player();

			jaxbPlayer.setName(name);
			if (startTile != null) {
				jaxbPlayer.setStartingTile(AdapterUtil.newJaxbTile(startTile));
			}
			// starting tile used or not
			jaxbPlayer.setStartingTileUsed(isStartingTileUsed);

			// possessed tiles
			jaxbPlayer.setPossessedTiles(AdapterUtil.newJaxbTiles(player
					.getPossessedTiles()));
			// player's IStrategy
			jaxbPlayer.setStrategyName(player.getPlayStrategy().getClass()
					.getName());
			// player's holding castle(s)
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
			if (!jaxbPlayer.isStartingTileUsed()) {
				try {
					player.setStartingTile(AdapterUtil.newTile(startTile));
				} catch (GameRuleException e) {
					e.printStackTrace();
				}
			}
			IStrategy<TDCoordinate> strategy = null;
			try {
				strategy = (IStrategy<TDCoordinate>) Class.forName(
						jaxbPlayer.getStrategyName()).newInstance();
			} catch (InstantiationException e) {
				log.error(e.getMessage());
			} catch (IllegalAccessException e) {
				log.error(e.getMessage());
			} catch (ClassNotFoundException e) {
				log.error(e.getMessage());
			}
			player.setPlayStrategy(strategy);

			player.setStartingTileUsed(jaxbPlayer.isStartingTileUsed());

			player.setPossessedTiles(AdapterUtil.newTiles(jaxbPlayer
					.getPossessedTiles()));

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
