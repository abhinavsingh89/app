package org.concordia.kingdoms;

import java.util.List;

public interface Game {

	void start(List<Player> players);

	void resume();

	void pause();

	void save();

	void exit();

	String getName();

	String getDescription();

}
