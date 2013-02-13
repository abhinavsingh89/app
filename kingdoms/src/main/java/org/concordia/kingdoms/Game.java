package org.concordia.kingdoms;

import java.util.List;

import org.concordia.kingdoms.board.ui.Presentable;

public interface Game extends Presentable{

	void start(List<Player> players);

	void resume();

	void pause();

	void save();

	void exit();

	String getName();

	String getDescription();

}
