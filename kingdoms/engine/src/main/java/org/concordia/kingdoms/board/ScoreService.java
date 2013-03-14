package org.concordia.kingdoms.board;

import org.concordia.kingdoms.Player;

public interface ScoreService<T extends ICoordinate> {

	int score(Player<T>... players);
}
