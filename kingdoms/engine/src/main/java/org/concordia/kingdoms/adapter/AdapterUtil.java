package org.concordia.kingdoms.adapter;

import java.util.List;
import java.util.Map;

import org.concordia.kingdoms.board.EpochCounter;
import org.concordia.kingdoms.board.ICoordinate;
import org.concordia.kingdoms.board.Score;
import org.concordia.kingdoms.board.TDCoordinate;
import org.concordia.kingdoms.board.score.ScoreCard;
import org.concordia.kingdoms.domain.Castle;
import org.concordia.kingdoms.domain.Color;
import org.concordia.kingdoms.domain.Tile;
import org.concordia.kingdoms.domain.TileType;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Simple utility to convert the basic token objects to and from Jaxb to domain
 * objects
 * 
 * @author Team K
 * 
 */
public class AdapterUtil {

	/**
	 * builds a new Tile object from Jaxb Tile object
	 * 
	 * @param - jaxbTile Tile object using by Jaxb
	 * @return A new Tile object
	 */

	public static Tile newTile(org.concordia.kingdoms.jaxb.Tile jaxbTile) {
		if (jaxbTile == null) {
			return null;
		}
		String tileName = jaxbTile.getName();
		TileType tileType = TileType.valueOf(jaxbTile.getType().name());
		Integer value = jaxbTile.getValue();
		final Tile tile = Tile.newTile(tileType, tileName, value);
		return tile;
	}

	/**
	 * builds a new Castle object from Jaxb Castle object
	 * 
	 * @param - jaxb Castle object using by Jaxb
	 * @return A new Castle object
	 */

	public static Castle newCastle(org.concordia.kingdoms.jaxb.Castle jaxbCastle) {
		if (jaxbCastle == null) {
			return null;
		}
		Integer rank = jaxbCastle.getRank();
		Color color = Color.valueOf(jaxbCastle.getColor().name());
		final Castle castle = Castle.newCastle(rank, color);
		return castle;
	}

	/**
	 * takes the row , column and a component to build the Jaxb Entry object
	 * 
	 * @param row
	 *            - row position in the matrix
	 * @param column
	 *            - column position in the matrix
	 * @param tile
	 *            - Entry holding tile
	 * @param castle
	 *            - Entry holding castle
	 * @return A new Jaxb Entry object
	 */
	public static org.concordia.kingdoms.jaxb.Entry newJaxbEntry(
			ICoordinate coordinate, org.concordia.kingdoms.jaxb.Tile tile,
			org.concordia.kingdoms.jaxb.Castle castle) {
		org.concordia.kingdoms.jaxb.Entry jaxbEntry = new org.concordia.kingdoms.jaxb.Entry();
		if (coordinate instanceof TDCoordinate) {
			TDCoordinate tdCoordinate = ((TDCoordinate) coordinate);
			jaxbEntry.setRow(tdCoordinate.getRow());
			jaxbEntry.setColumn(tdCoordinate.getColumn());
		}
		jaxbEntry.setTile(tile);
		jaxbEntry.setCastle(castle);
		return jaxbEntry;
	}

	/**
	 * builds list of new Tile objects from Jaxb Tile list
	 * 
	 * @param - list of jaxb Tile object
	 * @return A new List of new Tile objects
	 */

	public static List<Tile> newTiles(
			List<org.concordia.kingdoms.jaxb.Tile> jaxbTiles) {
		if (jaxbTiles == null) {
			return null;
		}
		List<Tile> tiles = Lists.newArrayList();
		for (org.concordia.kingdoms.jaxb.Tile jaxbTile : jaxbTiles) {
			tiles.add(newTile(jaxbTile));
		}
		return tiles;
	}

	/**
	 * builds list of new Tile objects from Jaxb Tile list
	 * 
	 * @param - list of jaxb Tile object
	 * @return A new List of new Tile objects
	 */

	public static List<Castle> newCastles(
			List<org.concordia.kingdoms.jaxb.Castle> jaxbCastles) {
		if (jaxbCastles == null) {
			return Lists.newArrayList();
		}
		List<Castle> castles = Lists.newArrayList();
		for (org.concordia.kingdoms.jaxb.Castle jaxbCastle : jaxbCastles) {
			castles.add(newCastle(jaxbCastle));
		}
		return castles;
	}

	/**
	 * builds list of new Jaxb Tile objects from Tile list
	 * 
	 * @param - list of Tile object
	 * @return A new List of new Jaxb Tile objects
	 */

	public static List<org.concordia.kingdoms.jaxb.Tile> newJaxbTiles(
			List<Tile> tiles) {
		if (tiles == null) {
			return Lists.newArrayList();
		}
		List<org.concordia.kingdoms.jaxb.Tile> jaxbTiles = Lists.newArrayList();

		for (Tile tile : tiles) {
			jaxbTiles.add(newJaxbTile(tile));
		}

		return jaxbTiles;
	}

	public static org.concordia.kingdoms.jaxb.Castle newJaxbCastle(
			final Castle castle) {
		final String castleName = castle.getName();
		final Color color = castle.getColor();
		final Integer castleRank = castle.getValue();
		// prepare a new Jaxb Tile
		org.concordia.kingdoms.jaxb.Castle jaxbCastle = new org.concordia.kingdoms.jaxb.Castle();
		jaxbCastle.setName(castleName);
		jaxbCastle.setRank(castleRank);
		jaxbCastle.setColor(org.concordia.kingdoms.jaxb.Color.valueOf(color
				.toString()));
		return jaxbCastle;
	}

	public static List<org.concordia.kingdoms.jaxb.Castle> newJaxbCastles(
			List<Castle> castles) {
		if (castles == null) {
			return Lists.newArrayList();
		}
		List<org.concordia.kingdoms.jaxb.Castle> jaxbCastles = Lists
				.newArrayList();

		for (Castle castle : castles) {
			jaxbCastles.add(newJaxbCastle(castle));
		}

		return jaxbCastles;
	}

	public static Map<Integer, List<Castle>> reoslveCastles(List<Castle> castles) {
		Map<Integer, List<Castle>> retCastles = Maps.newHashMap();
		for (Castle castle : castles) {
			Integer rank = castle.getValue();
			if (retCastles.get(rank) != null) {
				retCastles.get(rank).add(castle);
			} else {
				retCastles.put(rank, Lists.newArrayList(castle));
			}
		}
		return retCastles;
	}

	public static org.concordia.kingdoms.jaxb.Tile newJaxbTile(final Tile tile) {
		String tileName = tile.getName();
		TileType tileType = tile.getType();
		Integer value = tile.getValue();
		// prepare a new Jaxb Tile
		org.concordia.kingdoms.jaxb.Tile jaxbTile = new org.concordia.kingdoms.jaxb.Tile();
		jaxbTile.setName(tileName);
		jaxbTile.setValue(value);
		jaxbTile.setType(org.concordia.kingdoms.jaxb.TileType.valueOf(tileType
				.name()));
		return jaxbTile;
	}

	public static org.concordia.kingdoms.jaxb.Score newJaxbScore(
			final Score score) {
		Color color = score.getColor();
		int columnScore = score.getColumnScore();
		int rowScore = score.getRowScore();

		// prepare a new Jaxb Score
		org.concordia.kingdoms.jaxb.Score jaxbScore = new org.concordia.kingdoms.jaxb.Score();
		jaxbScore.setColor(org.concordia.kingdoms.jaxb.Color.valueOf(color
				.name()));
		jaxbScore.setColumnScore(columnScore);
		jaxbScore.setRowScore(rowScore);
		return jaxbScore;
	}

	/**
	 * builds list of new Jaxb Score objects from Score list
	 * 
	 * @param - list of Score object
	 * @return A new List of new Jaxb Score objects
	 */

	public static List<org.concordia.kingdoms.jaxb.Score> newJaxbScores(
			List<Score> scores) {
		if (scores == null) {
			return Lists.newArrayList();
		}
		List<org.concordia.kingdoms.jaxb.Score> jaxbScores = Lists
				.newArrayList();

		for (Score score : scores) {
			jaxbScores.add(newJaxbScore(score));
		}

		return jaxbScores;
	}

	/**
	 * builds list of new Score objects from Jaxb Score list
	 * 
	 * @param - list of jaxb Score object
	 * @return A new List of new Score objects
	 */

	public static List<Score> newScores(
			List<org.concordia.kingdoms.jaxb.Score> jaxbScores) {
		if (jaxbScores == null) {
			return Lists.newArrayList();
		}
		List<Score> scores = Lists.newArrayList();
		for (org.concordia.kingdoms.jaxb.Score jaxbScore : jaxbScores) {
			scores.add(newScore(jaxbScore));
		}
		return scores;
	}

	/**
	 * builds a new Score object from Jaxb Score object
	 * 
	 * @param - jaxbScore Score object using by Jaxb
	 * @return A new Score object
	 */

	public static Score newScore(org.concordia.kingdoms.jaxb.Score jaxbScore) {
		if (jaxbScore == null) {
			return null;
		}
		org.concordia.kingdoms.jaxb.Color color = jaxbScore.getColor();
		int columnScore = jaxbScore.getColumnScore();
		int rowScore = jaxbScore.getRowScore();

		Score score = new Score(Color.valueOf(color.name()));
		score.incrementColumnScoreBy(columnScore);
		score.incrementRowScoreBy(rowScore);

		return score;
	}

	public static EpochCounter newEpochCounter(
			org.concordia.kingdoms.jaxb.EpochCounter jaxbEpochCounter) {

		List<org.concordia.kingdoms.jaxb.ScoreCard> jaxbScoreCards = jaxbEpochCounter
				.getScoreCards();

		List<ScoreCard> scoreCards = Lists.newArrayList();

		for (org.concordia.kingdoms.jaxb.ScoreCard jaxbScoreCard : jaxbScoreCards) {
			List<org.concordia.kingdoms.jaxb.Score> scores = jaxbScoreCard
					.getScores();
			ScoreCard scoreCard = new ScoreCard(jaxbScoreCard.getLevel(),
					newScores(scores));
			scoreCards.add(scoreCard);
		}

		EpochCounter epochCounter = new EpochCounter(
				jaxbEpochCounter.getCurrentLevel(),
				jaxbEpochCounter.getTotalLevels());
		epochCounter.setScoreCards(scoreCards);

		return epochCounter;
	}

	public static org.concordia.kingdoms.jaxb.EpochCounter newJaxbEpochCounter(
			EpochCounter epochCounter) {

		int currentLevel = epochCounter.getCurrentLevel();
		int totalLevels = epochCounter.getTotalLevels();
		List<ScoreCard> scoreCards = epochCounter.getScoreCards();
		List<org.concordia.kingdoms.jaxb.ScoreCard> jaxbScoreCards = Lists
				.newArrayList();
		for (ScoreCard scoreCard : scoreCards) {
			int level = scoreCard.getLevel();
			List<Score> scores = scoreCard.getScores();
			List<org.concordia.kingdoms.jaxb.Score> jaxbScores = newJaxbScores(scores);
			org.concordia.kingdoms.jaxb.ScoreCard jaxbScoreCard = new org.concordia.kingdoms.jaxb.ScoreCard();
			jaxbScoreCard.setLevel(level);
			jaxbScoreCard.setScores(jaxbScores);
			jaxbScoreCards.add(jaxbScoreCard);
		}
		org.concordia.kingdoms.jaxb.EpochCounter jaxbEpochCounter = new org.concordia.kingdoms.jaxb.EpochCounter();
		jaxbEpochCounter.setCurrentLevel(currentLevel);
		jaxbEpochCounter.setTotalLevels(totalLevels);
		jaxbEpochCounter.setScoreCards(jaxbScoreCards);
		return jaxbEpochCounter;
	}
}
