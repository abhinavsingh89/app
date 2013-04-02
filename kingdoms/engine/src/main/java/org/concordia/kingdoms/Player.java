package org.concordia.kingdoms;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.concordia.kingdoms.board.Board;
import org.concordia.kingdoms.board.Entry;
import org.concordia.kingdoms.board.IBoardAware;
import org.concordia.kingdoms.board.ICoordinate;
import org.concordia.kingdoms.board.Score;
import org.concordia.kingdoms.domain.Castle;
import org.concordia.kingdoms.domain.Coin;
import org.concordia.kingdoms.domain.CoinType;
import org.concordia.kingdoms.domain.Color;
import org.concordia.kingdoms.domain.Component;
import org.concordia.kingdoms.domain.Tile;
import org.concordia.kingdoms.exceptions.GameRuleException;
import org.concordia.kingdoms.strategies.IStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * @author Team K
 * @since 1.0
 * 
 */
public class Player<T extends ICoordinate> implements IBoardAware<T> {

	private static final Logger log = LoggerFactory.getLogger(Player.class);

	private String name;

	private Color chosenColor;

	private List<Score> scores;

	private Tile startingTile;

	private boolean isStartingTileUsed;

	private Map<CoinType, List<Coin>> coins;

	private Map<Integer, List<Castle>> castles;

	private Board<T> board;

	private IStrategy<T> playStrategy;

	/**
	 * Constructor for player
	 * 
	 * @param name
	 *            - Name of the player
	 * @param chosenColors
	 *            - Different colors chosen by player
	 * 
	 */
	private Player(String name, final Color chosenColor) {
		this.name = name;
		this.chosenColor = chosenColor;
		this.scores = Lists.newArrayList();
		this.startingTile = null;
		this.coins = Maps.newHashMap();
		this.castles = Maps.newHashMap();
		this.isStartingTileUsed = false;
	}

	public void takeTurn() {

		// tile available with this player to chose
		final List<Tile> tilesToChose = Lists.newArrayList();
		// if starting tile is already used then it is not a candidate key to
		// chose again
		if (!isStartingTileUsed) {
			tilesToChose.add(startingTile);
		}
		// all the castles associated with this castles
		final List<Castle> castlesList = getCastlesAsList();
		Entry<T> entry = null;
		try {
			// strategy determined entry
			entry = playStrategy.getEntry(this, tilesToChose, castlesList,
					board.getAvailableCoordinates());
			// when strategy couldn't resolve the component or the coordinate
			// skipping the player's turn, this turn is consumed as a no action
			if (entry == null || entry.getComponent() == null
					|| entry.getCoordinate() == null) {
				log.info("Player " + this.getName()
						+ " ran out of possible moves");
				return;
			}
		} catch (GameRuleException e1) {
			log.error(e1.getMessage());
		}
		try {
			// if the component determined by strategy is a castle
			if (entry.getComponent() instanceof Castle) {
				putCastle((Castle) entry.getComponent(), entry.getCoordinate());
			} else {
				// if the component determined by strategy is a tile
				final Tile tile = (Tile) entry.getComponent();
				putTile(tile, entry.getCoordinate());
				if (tile.equals(startingTile)) {
					startingTile = null;
					isStartingTileUsed = true;
				}
			}
		} catch (GameRuleException e) {
			log.error(e.getMessage());
		}
	}

	private List<Castle> getCastlesAsList() {
		Iterator<Integer> itr = castles.keySet().iterator();
		List<Castle> castlesList = Lists.newArrayList();
		while (itr.hasNext()) {
			List<Castle> rankCastles = castles.get(itr.next());
			castlesList.addAll(rankCastles);
		}
		return castlesList;
	}

	/**
	 * method used to put tile
	 * 
	 * @param tile
	 *            - which type of tile player want to put
	 * @param row
	 *            - row number
	 * @param column
	 *            - row column
	 */
	public void putTile(Tile tile, T coordinate) throws GameRuleException {
		// Entry entry = playStrategy.getEntry(null, castles, null);
		this.board.putComponent(tile, coordinate);
	}

	/**
	 * method used to put castle
	 * 
	 * @param castle
	 *            - which type of castle player want to put
	 * @param row
	 *            - row number
	 * @param column
	 *            - row column
	 */
	public void putCastle(Castle castle, T coordinate) throws GameRuleException {
		if (!this.castles.get(castle.getValue()).contains(castle)) {
			throw new GameRuleException("Castle " + castle
					+ " not available with this player");
		}
		this.board.putComponent((Component) castle, coordinate);
		this.castles.get(castle.getValue()).remove(castle);
	}

	/**
	 * add castles to the player
	 * 
	 * @param rank
	 *            all kastles belong to this rank
	 * @param kastles
	 *            list of kastles appended to the existing list
	 */

	public void addCastle(int rank, final List<Castle> kastles) {
		if (kastles == null || kastles.isEmpty()) {
			return;
		}
		final List<Castle> rankCastles = this.castles.get(rank);
		if (rankCastles != null) {
			rankCastles.addAll(kastles);
		} else {
			this.castles.put(rank, kastles);
		}
	}

	/**
	 * removes the first occurence of castle from the players account
	 * 
	 * @param rank
	 *            - castle's rank
	 * @return {@link Castle}
	 * @throws GameRuleException
	 *             - throws when no castle with the given rank is available with
	 *             this player
	 */
	public Castle removeCastle(int rank) throws GameRuleException {

		final List<Castle> castlesList = this.castles.get(rank);
		if (castlesList != null && !castlesList.isEmpty()) {
			return castlesList.remove(0);
		} else {
			throw new GameRuleException("No castle with rank " + rank
					+ "available with this player");
		}
	}

	public static <T extends ICoordinate> Player<T> newPlayer(String name,
			final Color chosenColor) {
		return new Player<T>(name, chosenColor);
	}

	/**
	 * castles associated with tihs player
	 * 
	 * @return unmodifiable map this includes even the list of castles are also
	 *         not modifiable
	 */
	public Map<Integer, List<Castle>> getCastles() {
		Iterator<Integer> rankItr = this.castles.keySet().iterator();
		Map<Integer, List<Castle>> retMap = Maps.newHashMap();
		while (rankItr.hasNext()) {
			Integer rank = rankItr.next();
			retMap.put(rank,
					Collections.unmodifiableList(this.castles.get(rank)));
		}
		return Collections.unmodifiableMap(retMap);
	}

	/**
	 * new entry for scores list
	 * 
	 * @param newScore
	 */
	public void addNewScore(Score newScore) {
		if (newScore != null) {
			this.scores.add(newScore);
		}
	}

	/**
	 * sum of all the scores from each individual level, plus 50 the one
	 * assigned before the game begin
	 * 
	 * @return total score
	 */
	public int getTotalScore() {
		int total = 50;
		for (Score score : scores) {
			total += score.score();
		}
		return total;
	}

	/**
	 * returns a list of score objects
	 * 
	 * @return
	 */
	public List<Score> getScores() {
		return this.scores;
	}

	/**
	 * @return return true, if atleast one castle is available with the player
	 *         under any rank, false otherwise
	 */
	public boolean hasAnyCastleAvailable() {

		Iterator<Integer> itr = this.castles.keySet().iterator();
		while (itr.hasNext()) {
			Integer rank = itr.next();
			if (!this.castles.get(rank).isEmpty()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 
	 * @return true if the player has already took the starting tile, false
	 *         otherwise
	 */
	public boolean isStartingTileUsed() {
		return isStartingTileUsed;
	}

	public void setStartingTileUsed(boolean isStartingTileUsed) {
		this.isStartingTileUsed = isStartingTileUsed;
	}

	/**
	 * 
	 * @param rank
	 *            - castle rank
	 * @return -first occurence of the castle with the given rank from this
	 *         player's account, null if no castle of given rank available
	 */
	public Castle getCastle(int rank) {
		final List<Castle> rankCastles = this.castles.get(rank);
		if (rankCastles != null && !rankCastles.isEmpty()) {
			return rankCastles.get(0);
		}
		return null;
	}

	/**
	 * name associated with the given player
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * set a name for player
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * player's chosen color
	 * 
	 * @return {@link Color}
	 */
	public Color getChosenColor() {
		return this.chosenColor;
	}

	/**
	 * starting tile assigned to this player
	 * 
	 * @return null if starting tile has not yet assigned or have already used
	 *         it
	 */
	public Tile getStartingTile() {
		return startingTile;
	}

	/**
	 * 
	 * @param startingTile
	 * @throws GameRuleException
	 */
	public void setStartingTile(Tile startingTile) throws GameRuleException {
		if (!this.isStartingTileUsed) {
			this.startingTile = startingTile;
			return;
		}
		throw new GameRuleException("Starting Tile has already been taken");

	}

	public Map<CoinType, List<Coin>> getCoins() {
		return coins;
	}

	public void setCoins(Map<CoinType, List<Coin>> coins) {
		this.coins = coins;
	}

	public Board<T> getBoard() {
		return this.board;
	}

	public void setBoard(Board<T> board) {
		this.board = board;
	}

	/**
	 * sorts the players based on their total scores in descending order
	 * 
	 * @author Pavan
	 * 
	 */
	public static class PlayerComparator implements Comparator<Player<?>> {

		public static final PlayerComparator INSTANCE = new PlayerComparator();

		public int compare(Player<?> o1, Player<?> o2) {
			return o2.getTotalScore() - o1.getTotalScore();
		}
	}

	public IStrategy<T> getPlayStrategy() {
		return playStrategy;
	}

	public void setPlayStrategy(IStrategy<T> playStrategy) {
		this.playStrategy = playStrategy;
	}

	public Tile drawTile() {
		return board.drawTile();
	}
}
