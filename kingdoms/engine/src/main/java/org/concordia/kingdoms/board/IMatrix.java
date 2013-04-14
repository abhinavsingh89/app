package org.concordia.kingdoms.board;

import java.util.Iterator;
import java.util.List;

import org.concordia.kingdoms.board.score.ScoreService;
import org.concordia.kingdoms.domain.Component;
import org.concordia.kingdoms.exceptions.GameRuleException;

/**
 * @author Team K
 * @since 1.1
 */
public interface IMatrix<T extends ICoordinate> extends ScoreService<T> {

	// take the component and place it at the given coordinate
	void putComponent(Component component, T coordinate)
			throws GameRuleException;

	// is the coordinate eligible to take a component
	boolean isValidPosition(T coordinate);

	// is the complete matrix empty
	boolean isEmpty();

	// is the entry empty at given coordinate
	boolean isEmpty(T coordinate);

	// total components already holding
	int getTotalComponents();

	// matrix actual entries
	Iterator<Entry<T>> getEntries();

	// coordinates which are free to place component
	List<T> getAvailableCoordinates();

	List<Component> clearAllEntries();

	Component removeComponent(T coordinate);

	Component getComponent(T coordinate);
}