package org.concordia.kingdoms.board;

import java.util.Map;

import org.concordia.kingdoms.domain.Color;

public interface ScoreService<T extends ICoordinate> {

	Map<Color, Score> score();
}
