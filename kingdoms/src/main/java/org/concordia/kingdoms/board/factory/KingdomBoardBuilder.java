package org.concordia.kingdoms.board.factory;

import java.util.List;
import java.util.Map;

import org.concordia.kingdoms.GameBox;
import org.concordia.kingdoms.Player;
import org.concordia.kingdoms.board.Board;
import org.concordia.kingdoms.board.Entry;
import org.concordia.kingdoms.exceptions.GameException;
import org.concordia.kingdoms.tokens.Coin;
import org.concordia.kingdoms.tokens.CoinType;
import org.concordia.kingdoms.tokens.Color;

import com.google.common.collect.Maps;

public class KingdomBoardBuilder implements BoardBuilder {

	private KingdomBoardBuilder() {
		//
	}

	public Entry[][] buildEmptyBoard(int rows, int columns) {
		final Entry[][] entries = new Entry[rows][columns];
		this.initEntries(entries, rows, columns);
		return entries;
	}

	private void initEntries(final Entry[][] entries, int rows, int columns) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				entries[i][j] = Entry.newEntry(i, j);
			}
		}
	}

	public TileBank buildTileBank() {
		return TileBank.getTileBank();
	}

	public CoinBank buildCoinBank() {
		return CoinBank.getCoinBank();
	}

	public Board buildBoard(final int rows, final int columns,
			final List<Player> players) throws GameException {
		final Board board = new Board(this.buildEmptyBoard(rows, columns));
		board.setTileBank(this.buildTileBank());
		board.setCoinBank(this.buildCoinBank());
		board.setPlayers(players);
		initPlayers(board, players);
		return board;
	}

	private void initPlayers(Board board, final List<Player> players) throws GameException {
		// each player must have the board to put component
		for (final Player player : players) {
			player.setBoard(board);
			final Map<CoinType, List<Coin>> coinMap = Maps.newHashMap();
			coinMap.put(CoinType.GOLD_50,
					GameBox.getGameBox().takeCoins(CoinType.GOLD_50, 1));
			player.setCoins(coinMap);
			GameBox.getGameBox().assignRankOneCastles(player, players.size());
			GameBox.getGameBox().assignCastles(player, players.size());
		}

	}

	public Player buildPlayer(final String name, final Color[] chosenColors) {
		return Player.newPlayer(name, chosenColors);
	}

	public Player buildPlayer(final String name, final Color chosenColor) {
		return Player.newPlayer(name, new Color[] { chosenColor });
	}

	public static KingdomBoardBuilder newKingdomBoardBuilder() {
		return new KingdomBoardBuilder();
	}

}
