package org.concordia.kingdoms;

import java.util.List;

import org.concordia.kingdoms.board.ui.Presentable;
import org.concordia.kingdoms.exceptions.GameException;
/**
 * The user of this interface has precise control over the game. The control like starting the game, 
 * resuming, saving and exit the game
 * @author Team K
 * @since 1.0
 *	
 */
public interface Game extends Presentable{

	void start(List<Player> players) throws GameException;

	void resume();

	void pause();

	void save();

	void exit();

	String getName();

	String getDescription();

}
