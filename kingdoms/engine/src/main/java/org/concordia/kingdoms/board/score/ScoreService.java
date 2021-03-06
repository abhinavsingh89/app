package org.concordia.kingdoms.board.score;

import java.util.Map;

import org.concordia.kingdoms.board.ICoordinate;
import org.concordia.kingdoms.board.Score;
import org.concordia.kingdoms.domain.Color;
/**
 * @author Team K
 * @since 1.1
 */
public interface ScoreService<T extends ICoordinate> {

	Map<Color, Score> score();
}
