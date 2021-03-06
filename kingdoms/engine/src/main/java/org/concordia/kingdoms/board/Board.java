package org.concordia.kingdoms.board;

/**
 * Game board defining current entries into the game, tile bank, coin bank
 * players, etc. This class is basically keeping a track of board state. 
 * @author Team K
 * @since 1.0
 *
 */

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.concordia.kingdoms.CoinBank;
import org.concordia.kingdoms.GameBox;
import org.concordia.kingdoms.Player;
import org.concordia.kingdoms.board.factory.BoardBuilder;
import org.concordia.kingdoms.domain.Castle;
import org.concordia.kingdoms.domain.Color;
import org.concordia.kingdoms.domain.Component;
import org.concordia.kingdoms.domain.Tile;
import org.concordia.kingdoms.domain.TileType;
import org.concordia.kingdoms.exceptions.GameRuleException;
import org.concordia.kingdoms.spring.SpringContainer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Board<T extends ICoordinate> {

	private IMatrix<T> matrix;

	private TileBank tileBank;

	private CoinBank coinBank;

	private List<Player<T>> players;

	private List<IDisaster<T>> disasters;

	public static final int MAX_ROWS = 5;

	public static final int MAX_COLUMNS = 6;

	/**
	 * 
	 * @param entries
	 *            - It is a 2-D array
	 */
	public Board(final IMatrix<T> matrix) {
		this.matrix = matrix;
		this.tileBank = null;
		this.coinBank = null;
		this.players = Lists.newArrayList();
		this.disasters = Lists.newArrayList();
	}

	/**
	 * method used to put component
	 * 
	 * @param component
	 * 
	 * @param row
	 *            - row number
	 * @param column
	 *            - row column
	 */
	public synchronized void putComponent(Component component, T coordinate)
			throws GameRuleException {
		this.matrix.putComponent(component, coordinate);
		// all ready to fire disasters will be striken next
		for (IDisaster<T> disaster : disasters) {
			disaster.strike(this);
		}
	}

	/**
	 * method used to test the given coordinade are valid or not It returns
	 * boolean value
	 * 
	 * @param row
	 *            - row number
	 * @param column
	 *            - row column
	 */
	public boolean isValidCoordinate(int row, int column) {
		return row >= 0 && row < MAX_ROWS && column >= 0
				&& column < MAX_COLUMNS;
	}

	/**
	 * method used to test the given position is valid or not It returns boolean
	 * value
	 * 
	 * @param row
	 *            - row number
	 * @param column
	 *            - row column
	 */
	public boolean isValidPosition(T coordinate) {
		return this.matrix.isValidPosition(coordinate);
	}

	/**
	 * method used to test the values in 2-D array entries. It returns boolean
	 * value
	 * 
	 * @param row
	 *            - row number
	 * @param column
	 *            - row column
	 */
	public boolean isEntryEmpty(T coordinate) {
		return this.matrix.isEmpty(coordinate);
	}

	/**
	 * method used for returning tile bank
	 * 
	 * @return tileBank
	 */
	public TileBank getTileBank() {
		return this.tileBank;
	}

	/**
	 * method to set the tilebank
	 * 
	 * @param tileBank
	 */
	public void setTileBank(TileBank tileBank) {
		this.tileBank = tileBank;
	}

	/**
	 * method for returning coin bank
	 * 
	 * @return coinBank
	 */
	public CoinBank getCoinBank() {
		return this.coinBank;
	}

	/**
	 * sets the coin bank
	 * 
	 * @param coinBank
	 */
	public void setCoinBank(CoinBank coinBank) {
		this.coinBank = coinBank;
	}

	/**
	 * method used for returning the players list
	 * 
	 * @return - @see {@link Player}
	 */
	public List<Player<T>> getPlayers() {
		return this.players;
	}

	/**
	 * method used to set the players
	 * 
	 * @param players
	 */
	public void setPlayers(final List<Player<T>> players) {
		this.players = players;
	}

	public Iterator<Entry<T>> getEntries() {
		return this.matrix.getEntries();
	}

	public int getComponentsOnBoard() {
		return matrix.getTotalComponents();
	}

	public boolean isEmpty() {
		return this.matrix.isEmpty();
	}

	public boolean isFull() {
		return this.matrix.getAvailableCoordinates().isEmpty();
	}

	public Map<Color, Score> score() {
		return this.matrix.score();
	}

	/**
	 * when an epoch ends, and prepares for the next level, some cleanup on the
	 * board must be done before moving on. which includes returning all the
	 * castles on the board to the respective player(s).
	 * 
	 * @param coordinate
	 * @param boardBuilder
	 */
	public synchronized void levelChange(final BoardBuilder<T> boardBuilder) {

		final Iterator<Entry<T>> entries = getEntries();
		Map<Color, Player<?>> playersColor = resolvePlayersColors();
		final GameBox gameBox = SpringContainer.INSTANCE.getBean("gameBox",
				GameBox.class);
		// tiles going to be returned to tilebank
		List<Tile> retTiles = Lists.newArrayList();
		// scan through all the entries on the board
		while (entries.hasNext()) {
			final Entry<T> entry = entries.next();

			final Component component = entry.getComponent();
			// if component is castle
			if (component instanceof Castle) {
				// castle's color
				final Color color = ((Castle) component).getColor();
				// give it back to the player
				playersColor.get(color).addCastle(component.getValue(),
						Lists.newArrayList(((Castle) component)));
			}
			if (component instanceof Tile) {
				retTiles.add((Tile) component);
			}
		}
		// returning all the tiles from the board to the tilebank
		this.tileBank.addTiles(retTiles);
		// this method is important call without this the whole game will be
		// inconsistent, because before calling the tiles are on the board and
		// also in the tilebank
		this.matrix.clearAllEntries();
	}

	private Map<Color, Player<?>> resolvePlayersColors() {

		final Map<Color, Player<?>> colorMap = Maps.newHashMap();
		for (Player<?> player : players) {
			colorMap.put(player.getChosenColor(), player);
		}
		return colorMap;
	}

	public synchronized Tile drawTile() {
		return tileBank.drawTile();
	}

	public boolean isTileBankEmpty() {
		return this.tileBank.isEmpty();
	}

	public List<T> getAvailableCoordinates() {
		return this.matrix.getAvailableCoordinates();
	}

	public void putTileOnBoard(T coordinate, TileType type)
			throws GameRuleException {
		Tile drawnTile = this.tileBank.drawTile(type);
		synchronized (this) {
			putComponent(drawnTile, coordinate);
		}
	}

	public void setDisasters(List<IDisaster<T>> disasters) {
		this.disasters = disasters;
	}

	// return the component back to the player if the component is a Castle
	// otherwise if it is a tile return to Tilebank, leaving all those tiles
	// which are immune to be returned.
	public synchronized void returnComponent(Set<T> effectedCoordinates,
			List<TileType> tileTypes, DisasterType type) {
		List<Tile> retTiles = Lists.newArrayList();
		for (T effectedCoordinate : effectedCoordinates) {
			Component component = this.matrix.getComponent(effectedCoordinate);

			if (component instanceof Tile) {
				Tile tile = (Tile) component;
				if (!tileTypes.contains(tile.getType())) {
					this.matrix.removeComponent(effectedCoordinate, type);
					retTiles.add(tile);
				} else {
					continue;
				}
			}

			Map<Color, Player<?>> playersColor = resolvePlayersColors();

			// if component is castle
			if (component instanceof Castle) {
				// castle's color
				final Color color = ((Castle) component).getColor();
				// give it back to the player
				playersColor.get(color).addCastle(component.getValue(),
						Lists.newArrayList(((Castle) component)));
				this.matrix.removeComponent(effectedCoordinate, type);
			}
			this.matrix.markDisaster(type, effectedCoordinate);
		}
		this.tileBank.addTiles(retTiles);
		this.tileBank.shuffleTiles();
	}

	// shuffles the component(s) positions from its original position to a
	// random location among themselves
	public synchronized void shuffle(Set<T> effectedCoordinates,
			DisasterType disasterType) throws GameRuleException {
		List<T> coordList = Lists.newArrayList();
		List<Component> compList = Lists.newArrayList();
		for (T coord : effectedCoordinates) {
			coordList.add(coord);
		}

		Collections.shuffle(coordList);

		for (int i = 0; i < coordList.size(); i++) {
			T t = coordList.get(i);
			compList.add(matrix.removeComponent(t, disasterType));
		}

		int i = 0;

		for (T t : effectedCoordinates) {
			this.matrix.putComponent(compList.get(i), t, disasterType);
			i++;
		}

	}
}
