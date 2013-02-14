package org.concordia.kingdoms;

import java.util.List;

import org.concordia.kingdoms.board.ui.Presentable;
import org.concordia.kingdoms.exceptions.GameException;

public interface Game extends Presentable{

	void start(List<Player> players) throws GameException;

	void resume();

	void pause();

	void save();

	void exit();

	String getName();

	String getDescription();

}
